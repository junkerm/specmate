import { Injectable, EventEmitter } from '@angular/core';
import { Http, Response } from '@angular/http';
import { DataCache } from './data-cache';
import { ServiceInterface } from './service-interface';
import { Observable }        from 'rxjs/Observable';
import { Scheduler } from './scheduler';
import { LoggingService } from '../../../../views/side/modules/log-list/services/logging.service';
import { IContainer } from '../../../../../model/IContainer';
import { Url } from '../../../../../util/url';
import { IPositionable } from '../../../../../model/IPositionable';
import { Id } from '../../../../../util/id';
import { Command } from './command';
import { EOperation } from './e-operation';

/**
 * The interface to all data handling things.
 * It handles the cache and the service interface.
 * 
 * In commands executed by the user via the gui, always set the virtual argument to true, and use the commit-method in a save button.
 * This makes changes being done only in the cache, not on the server. In rare cases, e.g., creating a new model, the virtual flag can be omitted, since we want to store this directly on the server.
 * 
 * Whenever the user discards local changes, clearCommits() needs to be called to prevent commits from other views are done.
 */
@Injectable()
export class SpecmateDataService {

    public currentTaskName: string = '';

    private _busy: boolean = false;

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

    constructor(private http: Http, private logger: LoggingService) {
        this.serviceInterface = new ServiceInterface(http);
        this.scheduler = new Scheduler(this, this.logger);
        this.stateChanged = new EventEmitter<void>();
    }

    public checkConnection() : Promise<boolean> {
        return this.serviceInterface.checkConnection().then((connected: boolean) => {
            if (!connected) {
                this.logger.error('Connection to Specmate server lost.', undefined);
            }
            return connected;
        });
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
                return Promise.resolve(contents).then((contents: IContainer[]) => this.readContentsComplete(contents));
            }
            else if (this.scheduler.isVirtualElement(url)) {
                this.logger.info('Tried to read contents for virtual element.', url);
                this.cache.updateContents([], url);
                let contents: IContainer[] = this.readContentsVirtual(url);
                return Promise.resolve(contents).then((contents: IContainer[]) => this.readContentsComplete(contents));
            }
            else {
                this.logger.warn('Tried to read contents virtually, but could not find them. Falling back to server.', url);
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
                if (!((<any>element).live === 'true')) {
                    readElementTask = Promise.resolve(element);
                }
            }
            else {
                this.logger.warn('Tried to read element virtually, but could not find it. Falling back to server.', url);
            }
        }
        if (!readElementTask) {
            readElementTask = this.readElementServer(url);
        }
        return this.readContents(Url.parent(url)).then(() => readElementTask).then((element: IContainer) => this.readElementComplete(element));
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

    public getPromiseForCommand(command: Command): Promise<void> {
        let element: IContainer = command.newValue;
        switch(command.operation) {
            case EOperation.CREATE:
                return this.createElementServer(command.newValue);
            case EOperation.UPDATE:
                return this.updateElementServer(command.newValue);
            case EOperation.DELETE:
                return this.deleteElementServer(command.originalValue.url);
        }

        throw new Error('No suitable command found! Probably, we tried to re-execute an already resolved command.');
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

    public commit(taskName: string): Promise<void> {
        this.busy = true;
        this.currentTaskName = taskName;
        return this.scheduler.commit().then(() => { this.busy = false; });
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

    private deleteElementVirtual(url: string, compoundId: string): void {
        this.scheduler.schedule(url, EOperation.DELETE, undefined, this.readElementVirtual(url), compoundId);
        this.cache.deleteElement(url);
    }

    private createElementServer(element: IContainer): Promise<void> {
        this.logStart('Create', element.url);
        return this.serviceInterface.createElement(element).then(() => {
            this.scheduler.resolve(element.url);
            this.logFinished('Create', element.url);
        }).catch(() => this.handleError('Element could not be saved.', element.url));
    }

    private readContentsServer(url: string): Promise<IContainer[]> {
        this.logStart('Read Contents', url);
        return this.serviceInterface.readContents(url).then((contents: IContainer[]) => {
            this.cache.updateContents(contents, url);
            contents.forEach((element: IContainer) => this.scheduler.initElement(element));
            this.logFinished('Read Contents', url);
            return this.cache.readContents(url);
        }).catch(() => this.handleError('Contents could not be read. ', url));
    }

    private readElementServer(url: string): Promise<IContainer> {
        this.logStart('Read Element', url);
        return this.serviceInterface.readElement(url).then((element: IContainer) => {
            this.cache.addElement(element);
            this.logFinished('Read Element', url);
            return this.cache.readElement(url);
        }).catch(() => this.handleError('Element could not be read. ', url));
    }

    private updateElementServer(element: IContainer): Promise<void> {
        this.logStart('Update', element.url);
        return this.serviceInterface.updateElement(element).then(() => {
            this.scheduler.resolve(element.url);
            this.logFinished('Update', element.url);
        }).catch(() => this.handleError('Element could not be updated. ', element.url));
    }

    private deleteElementServer(url: string): Promise<void> {
        this.logStart('Delete ', url);
        return this.serviceInterface.deleteElement(url).then(() => {
            this.scheduler.resolve(url);
            this.logFinished('Delete', url);
        }).catch(() => this.handleError('Element could not be deleted. ', url));
    }

    public performOperations(url: string, operation: string, payload?: any): Promise<void> {
        this.busy = true;
        return this.serviceInterface.performOperation(url, operation, payload).then((result) => {
            this.busy = false;
            return result;
        }).catch(() => this.handleError('Operation could not be performed. Operation: ' + operation + ' Payload: ' + JSON.stringify(payload), url));
    }

    public performQuery(url: string, operation: string, parameters: { [key: string]: string; } ): Promise<any> {
        this.busy = true;
        this.logStart('Query operation: ' + operation, url);
        return this.serviceInterface.performQuery(url, operation, parameters).then(
            (result: any) => {
                this.busy = false;
                this.logFinished('Query operation: ' + operation, url);
                return result;
            }).catch(() => this.handleError('Query could not be performed. Operation: ' + operation + ' Parameters: ' + JSON.stringify(parameters), url));
    }

    public search(query: string): Promise<IContainer[]>{
        this.busy=true;
        this.logStart('Search: ' + query, '');
        return this.serviceInterface.search(query).then(
            (result: IContainer[]) => {
                this.busy = false;
                this.logFinished('Search: ' + query, '');
                return result;
            }).catch(() => this.handleError('Query could not be performed. Operation: search ' + query, ''));
    }

    private logStart(message: string, url: string): Promise<any> {
        this.logger.debug('Trying: ' + message, url);
        return Promise.resolve(undefined);
    }

    private logFinished(message: string, url: string): Promise<any> {
        this.logger.info('Success: ' + message, url);
        return Promise.resolve(undefined);
    }

    private handleError(message: string, url: string): Promise<any> {
        this.logger.error('Data Error: ' + message, url);
        return Promise.resolve(undefined);
    }
}
