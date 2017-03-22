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

@Injectable()
export class SpecmateDataService {

    private cache: DataCache = new DataCache();
    private serviceInterface: ServiceInterface;

    private operations: { [key: string]: EOperation } = {};

    constructor(private http: Http) {
        this.serviceInterface = new ServiceInterface(http);
    }

    public commit(): Promise<void> {
        return this.performCommit().then(() => {
            let clean: { [key: string]: EOperation } = {};
            for(let url in this.operations) {
                if(this.operations[url] !== EOperation.RESOLVED) {
                    clean[url] = this.operations[url];
                }
            }
            this.operations = clean;
        });
    }

    private performCommit(): Promise<void> {
        return Promise.all(this.getAllCommitActions()).then(() => { }, (message: any) => console.log("Promise rejected: " + message));
    }

    private getAllCommitActions(): Promise<void>[] {
        let actions: Promise<void>[] = [];
        for (let url in this.operations) {
            let operation: EOperation = this.operations[url];
            let element: IContainer = this.readElementVirtual(url);
            console.log("COMMIT: " + EOperation[operation] + " " + url);
            switch (operation) {
                case EOperation.CREATE:
                    actions.push(this.createElementServer(element));
                    break;
                case EOperation.UPDATE:
                    actions.push(this.updateElementServer(element));
                    break;
                case EOperation.DELETE:
                    actions.push(this.deleteElementServer(url));
                    break;
            }
        }
        return actions;
    }

    private task(url: string, operation: EOperation): void {
        this.operations[url] = operation;
    }

    private resolve(url: string): void {
        this.operations[url] = EOperation.RESOLVED;
    }

    public createElement(element: IContainer, virtual?: boolean): Promise<void> {
        if (virtual) {
            return Promise.resolve(this.createElementVirtual(element));
        }
        this.createElementServer(element);
    }

    public readContents(url: string, virtual?: boolean): Promise<IContainer[]> {
        if (virtual) {
            return Promise.resolve(this.readContentsVirtual(url));
        }
        this.readContentsServer(url);
    }

    public readElement(url: string, virtual?: boolean): Promise<IContainer> {
        if (virtual) {
            return Promise.resolve(this.readElementVirtual(url));
        }
        this.readElementServer(url);
    }

    public updateElement(element: IContainer, virtual?: boolean): Promise<void> {
        if (virtual) {
            return Promise.resolve(this.updateElementVirtual(element));
        }
        this.updateElementServer(element);
    }

    public deleteElement(url: string, virtual?: boolean): Promise<void> {
        if (virtual) {
            return Promise.resolve(this.deleteElementVirtual(url));
        }
        this.deleteElementServer(url);
    }

    private createElementVirtual(element: IContainer): void {
        this.task(element.url, EOperation.CREATE);
        return this.cache.addElement(element);
    }

    private readContentsVirtual(url: string): IContainer[] {
        return this.cache.readContents(url);
    }

    private readElementVirtual(url: string, forceServer?: boolean): IContainer {
        return this.cache.readElement(url);
    }

    private updateElementVirtual(element: IContainer): void {
        this.task(element.url, EOperation.UPDATE);
        this.operations[element.url] = EOperation.UPDATE;
        return this.cache.addElement(element);
    }

    private deleteElementVirtual(url: string): void {
        this.task(url, EOperation.DELETE);
        return this.cache.deleteElement(url);
    }

    private createElementServer(element: IContainer): Promise<void> {
        return this.serviceInterface.createElement(element).then(() => {
            this.cache.addElement(element);
            this.resolve(element.url);
        });
    }

    private readContentsServer(url: string): Promise<IContainer[]> {
        if (!this.cache.isCachedContents(url)) {
            return this.serviceInterface.readContents(url).then((contents: IContainer[]) => {
                contents.forEach((element: IContainer) => this.cache.addElement(element));
                return this.cache.readContents(url);
            });
        }
        return Promise.resolve(this.cache.readContents(url));
    }

    private readElementServer(url: string): Promise<IContainer> {
        if (!this.cache.isCachedContents(url)) {
            return this.serviceInterface.readElement(url).then((element: IContainer) => {
                this.cache.addElement(element);
                return this.cache.readElement(url);
            });
        }
        return Promise.resolve(this.cache.readElement(url));
    }

    private updateElementServer(element: IContainer): Promise<void> {
        return this.serviceInterface.updateElement(element).then(() => {
            this.cache.addElement(element);
            this.resolve(element.url);
        });
    }

    private deleteElementServer(url: string): Promise<void> {
        return this.serviceInterface.deleteElement(url).then(() => {
            this.cache.deleteElement(url);
            this.resolve(url);
        });
    }
}


export enum EOperation {
    CREATE, DELETE, UPDATE, RESOLVED
}
