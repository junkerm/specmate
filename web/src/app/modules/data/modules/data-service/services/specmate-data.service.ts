import { Injectable, EventEmitter } from '@angular/core';
import { DataCache } from './data-cache';
import { ServiceInterface } from './service-interface';
import { Observable } from 'rxjs/Observable';
import { Scheduler } from './scheduler';
import { LoggingService } from '../../../../views/side/modules/log-list/services/logging.service';
import { IContainer } from '../../../../../model/IContainer';
import { Url } from '../../../../../util/url';
import { IPositionable } from '../../../../../model/IPositionable';
import { Id } from '../../../../../util/id';
import { Command } from './command';
import { EOperation } from './e-operation';
import { HttpClient } from '@angular/common/http';
import { TranslateService } from '@ngx-translate/core';
import { AuthenticationService } from '../../../../views/main/authentication/modules/auth/services/authentication.service';
import { ServerConnectionService } from '../../../../common/modules/connection/services/server-connection-service';
import { BatchOperation } from '../../../../../model/BatchOperation';

/**
 * The interface to all data handling things.
 * It handles the cache and the service interface.
 *
 * In commands executed by the user via the gui, always set the virtual argument to true, and use the commit-method in a save button.
 * This makes changes being done only in the cache, not on the server.
 * In rare cases, e.g., creating a new model, the virtual flag can be omitted, since we want to store this directly on the server.
 *
 * Whenever the user discards local changes, clearCommits() needs to be called to prevent commits from other views are done.
 */
@Injectable()
export class SpecmateDataService {

    public currentTaskName = '';

    private _busy = false;

    private set busy(busy: boolean) {
        this._busy = busy;
        this.stateChanged.emit();
    }

    public get isLoading(): boolean {
        return this._busy;
    }

    public stateChanged: EventEmitter<void>;

    private cache: DataCache = new DataCache();
    private serviceInterface: ServiceInterface;
    private scheduler: Scheduler;

    constructor(private http: HttpClient,
        private auth: AuthenticationService,
        private logger: LoggingService,
        private translate: TranslateService,
        private connectionService: ServerConnectionService) {

        this.serviceInterface = new ServiceInterface(http);
        this.scheduler = new Scheduler(this, this.logger, this.translate);
        this.stateChanged = new EventEmitter<void>();

        this.auth.authChanged.subscribe(() => {
            if (!this.auth.isAuthenticated) {
                this.clear();
            }
        });
    }

    private clear(): void {
        this.clearCommits();
        this.cache.clear();
    }

    public createElement(element: IContainer, virtual: boolean, compoundId: string): Promise<void> {
        if (virtual) {
            return Promise.resolve(this.createElementVirtual(element, compoundId));
        }
        return this.createElementServer(element);
    }

    public readContents(url: string, virtual?: boolean): Promise<IContainer[]> {
        this.busy = true;

        if (virtual || this.scheduler.isVirtualElement(url) || this.cache.isCachedContents(url)) {
            let contents: IContainer[] = this.readContentsVirtual(url);
            if (contents) {
                return Promise.resolve(contents).then((loadedContents: IContainer[]) => this.readContentsComplete(loadedContents));
            } else if (this.scheduler.isVirtualElement(url)) {
                this.logger.info(this.translate.instant('triedToReadContensForVirtualElement'), url);
                this.cache.updateContents([], url);
                let virtualContents: IContainer[] = this.readContentsVirtual(url);
                return Promise.resolve(virtualContents).then((loadedContents: IContainer[]) => this.readContentsComplete(loadedContents));
            } else {
                this.logger.warn(this.translate.instant('triedToReadContensVirtuallyButCouldNotFindThemFallingBackToServer'), url);
            }
        }
        return this.readContentsServer(url).then((contents: IContainer[]) => this.readContentsComplete(contents));
    }

    private readContentsComplete(contents: IContainer[]): IContainer[] {
        this.busy = false;
        return contents;
    }

    public readElement(url: string, virtual?: boolean): Promise<IContainer> {
        this.busy = true;
        let readElementTask: Promise<IContainer> = undefined;

        if (virtual || this.scheduler.isVirtualElement(url) || this.cache.isCachedElement(url)) {
            let element: IContainer = this.readElementVirtual(url);
            if (element) {
                if (!((<any>element).live)) {
                    readElementTask = Promise.resolve(element);
                }
            } else {
                this.logger.warn(this.translate.instant('triedToReadElementVirtuallyButCouldNotFindItFallingBackToServer'), url);
            }
        }
        if (!readElementTask) {
            readElementTask = this.readElementServer(url);
        }
        const parentUrl = Url.parent(url);
        if (parentUrl === undefined) {
            return readElementTask.then(element => this.readElementComplete(element));
        }
        return this.readContents(parentUrl)
            .then(() => readElementTask)
            .then((element: IContainer) => this.readElementComplete(element));
    }

    private readElementComplete(element: IContainer): IContainer {
        this.busy = false;
        this.scheduler.initElement(element);
        return element;
    }

    public updateElement(element: IContainer, virtual: boolean, compoundId: string): Promise<void> {
        if (virtual) {
            return Promise.resolve(this.updateElementVirtual(element, compoundId));
        }
        return this.updateElementServer(element);
    }

    public duplicateElement(url: string, virtual: boolean, compoundId: string): Promise<void> {
        if (virtual || this.scheduler.isVirtualElement(url)) {
            return Promise.resolve(this.duplicateElementVirtual(url, compoundId));
        }
        return this.duplicateElementServer(url);
    }

    public deleteElement(url: string, virtual: boolean, compoundId: string): Promise<void> {
        if (virtual || this.scheduler.isVirtualElement(url)) {
            return Promise.resolve(this.deleteElementVirtual(url, compoundId));
        }
        return this.deleteElementServer(url);
    }

    public sanitizeContentPositions(elements: IPositionable[], update: boolean, compoundId?: string): void {
        if (!compoundId) {
            compoundId = Id.uuid;
        }
        elements.forEach((element: IContainer & IPositionable, index: number) => {
            element.position = index;
            if (update) {
                this.updateElement(<IContainer>element, true, compoundId);
            }
        });
    }

    public clearCommits(): void {
        this.scheduler.clearCommits();
    }

    public get hasCommits(): boolean {
        return this.scheduler.hasCommits;
    }

    public get countCommits(): number {
        return this.scheduler.countOpenCommits;
    }

    public get unresolvedCommands(): Command[] {
        return this.scheduler.unresolvedCommands;
    }

    public async commit(taskName: string): Promise<void> {
        this.busy = true;
        this.currentTaskName = taskName;
        const batchOperation = this.scheduler.toBatchOperation();
        await this.serviceInterface.performBatchOperation(batchOperation, this.auth.token);
        this.scheduler.resolveBatchOperation(batchOperation);
        this.scheduler.clearCommits();
        this.busy = false;
    }

    public undo(): void {
        this.scheduler.undo();
    }

    public discardChanges(): void {
        this.scheduler.undoAll();
    }

    public undoCreate(url: string) {
        this.cache.deleteElement(url);
    }

    public undoUpdate(originalValue: IContainer) {
        this.cache.addElement(originalValue);
    }

    public undoDelete(originalValue: IContainer) {
        this.cache.addElement(originalValue);
    }

    private createElementVirtual(element: IContainer, compoundId: string): void {
        this.scheduler.schedule(element.url, EOperation.CREATE, element, undefined, compoundId);
        return this.cache.addElement(element);
    }

    private readContentsVirtual(url: string): IContainer[] {
        return this.cache.readContents(url);
    }

    private readElementVirtual(url: string, forceServer?: boolean): IContainer {
        return this.cache.readElement(url);
    }

    private updateElementVirtual(element: IContainer, compoundId: string): void {
        this.scheduler.schedule(element.url, EOperation.UPDATE, element, undefined, compoundId);
        this.cache.addElement(element);
    }

    private duplicateElementVirtual(url: string, compoundId: string): void {
        this.scheduler.schedule(url, EOperation.DUPLICATE, this.readElementVirtual(url), this.readElementVirtual(url), compoundId);
    }

    private deleteElementVirtual(url: string, compoundId: string): void {
        this.scheduler.schedule(url, EOperation.DELETE, undefined, this.readElementVirtual(url), compoundId);
        this.cache.deleteElement(url);
    }

    private createElementServer(element: IContainer): Promise<void> {
        if (!this.auth.isAuthenticatedForUrl(element.url)) {
            return Promise.resolve();
        }
        this.logStart(this.translate.instant('create'), element.url);
        return this.serviceInterface.createElement(element, this.auth.token).then(() => {
            this.scheduler.resolve(element.url);
            this.logFinished(this.translate.instant('create'), element.url);
        }).catch((error) => this.handleError(this.translate.instant('elementCouldNotBeSaved'), element.url, error));
    }

    private readContentsServer(url: string): Promise<IContainer[]> {
        if (!this.auth.isAuthenticatedForUrl(url)) {
            return Promise.resolve(undefined);
        }
        this.logStart(this.translate.instant('log.readContents'), url);
        return this.serviceInterface.readContents(url, this.auth.token).then((contents: IContainer[]) => {
            this.cache.updateContents(contents, url);
            contents.forEach((element: IContainer) => this.scheduler.initElement(element));
            this.logFinished(this.translate.instant('log.readContents'), url);
            return this.cache.readContents(url);
        }).catch((error) => this.handleError(this.translate.instant('contentsCouldNotBeRead'), url, error));
    }

    private readElementServer(url: string): Promise<IContainer> {
        if (!this.auth.isAuthenticatedForUrl(url)) {
            return Promise.resolve(undefined);
        }
        this.logStart(this.translate.instant('log.readElement'), url);
        return this.serviceInterface.readElement(url, this.auth.token).then((element: IContainer) => {
            this.cache.addElement(element);
            this.logFinished(this.translate.instant('log.readElement'), url);
            return this.cache.readElement(url);
        }).catch((error) => this.handleError(this.translate.instant('elementCouldNotBeRead'), url, error));
    }

    private updateElementServer(element: IContainer): Promise<void> {
        if (!this.auth.isAuthenticatedForUrl(element.url)) {
            return Promise.resolve();
        }
        this.logStart(this.translate.instant('log.update'), element.url);
        return this.serviceInterface.updateElement(element, this.auth.token).then(() => {
            this.scheduler.resolve(element.url);
            this.logFinished(this.translate.instant('log.update'), element.url);
        }).catch((error) => this.handleError(this.translate.instant('elementCouldNotBeUpdated'), element.url, error));
    }

    private duplicateElementServer(url: string): Promise<void> {
        if (!this.auth.isAuthenticatedForUrl(url)) {
            return Promise.resolve();
        }
        this.logStart(this.translate.instant('log.duplicate'), url);
        return this.serviceInterface.duplicateElement(url, this.auth.token).then(() => {
            this.scheduler.resolve(url);
            this.logFinished(this.translate.instant('log.duplicate'), url);
        }).catch((error) => this.handleError(this.translate.instant('elementCouldNotBeDuplicated'), url, error));
    }

    private deleteElementServer(url: string): Promise<void> {
        if (!this.auth.isAuthenticatedForUrl(url)) {
            return Promise.resolve();
        }
        this.logStart(this.translate.instant('log.delete'), url);
        return this.serviceInterface.deleteElement(url, this.auth.token).then(() => {
            this.scheduler.resolve(url);
            this.logFinished(this.translate.instant('log.delete'), url);
        }).catch((error) => this.handleError(this.translate.instant('elementCouldNotBeDeleted'), url, error));
    }

    public performOperations(url: string, operation: string, payload?: any): Promise<void> {
        if (!this.auth.isAuthenticatedForUrl(url)) {
            return Promise.resolve();
        }
        this.busy = true;
        return this.serviceInterface.performOperation(url, operation, payload, this.auth.token).then((result) => {
            this.busy = false;
            return result;
        })
            .catch((error) =>
                this.handleError(this.translate.instant('operationCouldNotBePerformed') +
                    ' ' + this.translate.instant('operation') + ': ' + operation + ' ' +
                    this.translate.instant('payload') + ': ' + JSON.stringify(payload), url, error));
    }

    public performQuery(url: string, operation: string, parameters: { [key: string]: string; }): Promise<any> {
        if (!this.auth.isAuthenticatedForUrl(url)) {
            return Promise.resolve();
        }
        this.busy = true;
        this.logStart(this.translate.instant('log.queryOperation') + ': ' + operation, url);
        return this.serviceInterface.performQuery(url, operation, parameters, this.auth.token).then(
            (result: any) => {
                this.busy = false;
                this.logFinished(this.translate.instant('log.queryOperation') + ': ' + operation, url);
                return result;
            })
            .catch((error) =>
                this.handleError(this.translate.instant('queryCouldNotBePerformed') + ' ' + this.translate.instant('operation') + ': ' +
                    operation + ' ' + this.translate.instant('parameters') + ': ' + JSON.stringify(parameters), url, error));
    }

    public search(query: string, filter?: { [key: string]: string }): Promise<IContainer[]> {
        if (!this.auth.isAuthenticated) {
            return Promise.resolve([]);
        }
        this.busy = true;
        this.logStart(this.translate.instant('log.search') + ': ' + query, '');
        return this.serviceInterface.search(query, this.auth.token, filter).then(
            (result: IContainer[]) => {
                this.busy = false;
                this.logFinished(this.translate.instant('log.search') + ': ' + query, '');
                return result;
            }).catch((error) => this.handleError(this.translate.instant('queryCouldNotBePerformed') + ' ' +
                this.translate.instant('operation') + ' : search ' + query, '', error));
    }

    private logStart(message: string, url: string): Promise<any> {
        this.logger.debug(this.translate.instant('log.trying') + ': ' + message, url);
        return Promise.resolve(undefined);
    }

    private logFinished(message: string, url: string): Promise<any> {
        this.logger.info(this.translate.instant('log.success') + ': ' + message, url);
        return Promise.resolve(undefined);
    }

    private handleError(message: string, url: string, error: any): Promise<any> {
        console.error(message);
        this.connectionService.handleErrorResponse(error, url);
        return Promise.resolve(undefined);
    }
}
