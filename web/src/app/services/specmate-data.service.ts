import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { IContainer } from '../model/IContainer';
import 'rxjs/add/operator/toPromise';
import { Url } from '../util/Url';
import { Arrays } from '../util/Arrays';
import { Config } from '../config/config';
import { DataCache } from "./data-cache";
import { Objects } from "../util/Objects";
import { ServiceInterface } from "./service-interface";

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

    private cache: DataCache = new DataCache();
    private serviceInterface: ServiceInterface;

    private operations: { [key: string]: EOperation } = {};

    constructor(private http: Http) {
        this.serviceInterface = new ServiceInterface(http);
    }

    public commit(): Promise<void> {
        return this.chainCommits().then(() => {
            this.cleanOperations();
        });
    }

    public clearCommits(): void {
        this.operations = {};
    }

    private cleanOperations(): void {
        let clean: { [key: string]: EOperation } = {};
        for (let url in this.operations) {
            if (this.operations[url] && this.operations[url] !== EOperation.RESOLVED) {
                clean[url] = this.operations[url];
            }
        }
        this.operations = clean;
    }

    private chainCommits(): Promise<void> {
        let chain: Promise<void> = Promise.resolve();
        for (let url in this.operations) {
            chain = chain.then(() => {
                return this.determineAction(url);
            });
        }
        return chain;
    }

    private determineAction(url: string): Promise<void> {
        let element: IContainer = this.readElementVirtual(url);
        let operation: EOperation = this.operations[url];
        switch (operation) {
            case EOperation.CREATE:
                return this.createElementServer(element);
            case EOperation.UPDATE:
                return this.updateElementServer(element);
            case EOperation.DELETE:
                return this.deleteElementServer(url);
        }
    }

    private schedule(url: string, operation: EOperation): void {
        let previousOperation: EOperation = this.operations[url];
        if (previousOperation === EOperation.CREATE) {
            if (operation === EOperation.CREATE) {
                console.error('Trying to overwrite element ' + url);
            } else if (operation === EOperation.UPDATE) {
                operation = EOperation.CREATE;
            } else if (operation === EOperation.DELETE) {
                operation = EOperation.RESOLVED;
            }
        } else if (previousOperation === EOperation.UPDATE) {
            if (operation === EOperation.CREATE) {
                console.error('Trying to overwrite element ' + url);
            }
        } else if (previousOperation === EOperation.DELETE) {
            if (operation === EOperation.CREATE) {
                operation = EOperation.UPDATE;
            } else if (operation === EOperation.UPDATE) {
                console.error('Trying to update deleted element ' + url);
            } else if (operation === EOperation.DELETE) {
                console.error('Trying to delete deleted element ' + url);
            }
        }
        console.log("SCHEDULING " + EOperation[operation] + " FOR " + url);
        this.operations[url] = operation;
    }

    private resolve(url: string): void {
        this.operations[url] = EOperation.RESOLVED;
    }

    public createElement(element: IContainer, virtual?: boolean): Promise<void> {
        if (virtual) {
            return Promise.resolve(this.createElementVirtual(element));
        }
        return this.createElementServer(element);
    }

    public readContents(url: string, virtual?: boolean): Promise<IContainer[]> {
        if (virtual) {
            return Promise.resolve(this.readContentsVirtual(url));
        }
        return this.readContentsServer(url);
    }

    public readElement(url: string, virtual?: boolean): Promise<IContainer> {
        if (virtual || this.operations[url] === EOperation.CREATE) {
            return Promise.resolve(this.readElementVirtual(url));
        }
        return this.readElementServer(url);
    }

    public updateElement(element: IContainer, virtual?: boolean): Promise<void> {
        if (virtual) {
            return Promise.resolve(this.updateElementVirtual(element));
        }
        return this.updateElementServer(element);
    }

    public deleteElement(url: string, virtual?: boolean): Promise<void> {
        if (virtual || this.operations[url] === EOperation.CREATE) {
            return Promise.resolve(this.deleteElementVirtual(url));
        }
        return this.deleteElementServer(url);
    }

    private createElementVirtual(element: IContainer): void {
        this.schedule(element.url, EOperation.CREATE);
        return this.cache.addElement(element);
    }

    private readContentsVirtual(url: string): IContainer[] {
        return this.cache.readContents(url);
    }

    private readElementVirtual(url: string, forceServer?: boolean): IContainer {
        return this.cache.readElement(url);
    }

    private updateElementVirtual(element: IContainer): void {
        this.schedule(element.url, EOperation.UPDATE);
        return this.cache.addElement(element);
    }

    private deleteElementVirtual(url: string): void {
        this.schedule(url, EOperation.DELETE);
        return this.cache.deleteElement(url);
    }

    private createElementServer(element: IContainer): Promise<void> {
        console.log("CREATE " + element.url);
        return this.serviceInterface.createElement(element).then(() => {
            this.cache.addElement(element);
            this.resolve(element.url);
            console.log("CREATE " + element.url + " DONE");
        });
    }

    private readContentsServer(url: string): Promise<IContainer[]> {
        return this.serviceInterface.readContents(url).then((contents: IContainer[]) => {
            contents.forEach((element: IContainer) => this.cache.addElement(element));
            return this.cache.readContents(url);
        });
    }

    private readElementServer(url: string): Promise<IContainer> {
        return this.serviceInterface.readElement(url).then((element: IContainer) => {
            this.cache.addElement(element);
            return this.cache.readElement(url);
        });
    }

    private updateElementServer(element: IContainer): Promise<void> {
        console.log("UPDATE " + element.url);
        return this.serviceInterface.updateElement(element).then(() => {
            this.cache.addElement(element);
            this.resolve(element.url);
            console.log("UPDATE " + element.url + " DONE");
        });
    }

    private deleteElementServer(url: string): Promise<void> {
        console.log("DELETE " + url);
        return this.serviceInterface.deleteElement(url).then(() => {
            this.cache.deleteElement(url);
            this.resolve(url);
            console.log("DELETE " + url + " DONE");
        });
    }
}


export enum EOperation {
    CREATE, DELETE, UPDATE, RESOLVED
}
