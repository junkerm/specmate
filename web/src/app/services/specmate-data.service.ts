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
import { EOperation } from "./operations";
import { Scheduler } from "./scheduler";

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

    public busy: boolean = false;

    private cache: DataCache = new DataCache();
    private serviceInterface: ServiceInterface;
    private scheduler: Scheduler;

    constructor(private http: Http) {
        this.serviceInterface = new ServiceInterface(http);
        this.scheduler = new Scheduler(this);
    }

    public createElement(element: IContainer, virtual?: boolean): Promise<void> {
        if (virtual) {
            return Promise.resolve(this.createElementVirtual(element));
        }
        return this.createElementServer(element);
    }

    public readContents(url: string, virtual?: boolean): Promise<IContainer[]> {
        this.busy = true;
        if (virtual) {
            return Promise.resolve(this.readContentsVirtual(url))
                .then((contents: IContainer[]) => {
                    this.busy = false;
                    return contents;
                });
        }
        return this.readContentsServer(url);
    }

    public readElement(url: string, virtual?: boolean): Promise<IContainer> {
        if (virtual || this.scheduler.isOperation(url, EOperation.CREATE)) {
            return Promise.resolve(this.readElementVirtual(url))
                .then((element: IContainer) => {
                    this.busy = false;
                    return element;
                });
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
        if (virtual || this.scheduler.isOperation(url, EOperation.CREATE)) {
            return Promise.resolve(this.deleteElementVirtual(url));
        }
        return this.deleteElementServer(url);
    }

    public getPromiseForUrl(url: string): Promise<void> {
        let element: IContainer = this.readElementVirtual(url);

        if (this.scheduler.isOperation(url, EOperation.CREATE)) {
            return this.createElementServer(element);
        }
        if (this.scheduler.isOperation(url, EOperation.UPDATE)) {
            return this.updateElementServer(element);
        }
        if (this.scheduler.isOperation(url, EOperation.DELETE)) {
            return this.deleteElementServer(url);
        }
    }

    public clearCommits(): void {
        this.scheduler.clearCommits();
    }

    public commit(): Promise<void> {
        this.busy = true;
        return this.scheduler.commit().then(() => { this.busy = false; });
    }

    private createElementVirtual(element: IContainer): void {
        this.scheduler.schedule(element.url, EOperation.CREATE);
        return this.cache.addElement(element);
    }

    private readContentsVirtual(url: string): IContainer[] {
        return this.cache.readContents(url);
    }

    private readElementVirtual(url: string, forceServer?: boolean): IContainer {
        return this.cache.readElement(url);
    }

    private updateElementVirtual(element: IContainer): void {
        this.scheduler.schedule(element.url, EOperation.UPDATE);
        return this.cache.addElement(element);
    }

    private deleteElementVirtual(url: string): void {
        this.scheduler.schedule(url, EOperation.DELETE);
        return this.cache.deleteElement(url);
    }

    private createElementServer(element: IContainer): Promise<void> {
        console.log("CREATE " + element.url);
        return this.serviceInterface.createElement(element).then(() => {
            this.cache.addElement(element);
            this.scheduler.resolve(element.url);
            console.log("CREATE " + element.url + " DONE");
        });
    }

    private readContentsServer(url: string): Promise<IContainer[]> {
        return this.serviceInterface.readContents(url).then((contents: IContainer[]) => {
            this.cache.updateContents(contents, url);
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
            this.scheduler.resolve(element.url);
            console.log("UPDATE " + element.url + " DONE");
        });
    }

    private deleteElementServer(url: string): Promise<void> {
        console.log("DELETE " + url);
        return this.serviceInterface.deleteElement(url).then(() => {
            this.cache.deleteElement(url);
            this.scheduler.resolve(url);
            console.log("DELETE " + url + " DONE");
        });
    }
}
