import {Id} from '../util/Id';
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
import { Command } from "./command";
import { TestSpecification } from '../model/TestSpecification';

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
    public currentTaskName: string = '';

    private cache: DataCache = new DataCache();
    private serviceInterface: ServiceInterface;
    private scheduler: Scheduler;

    constructor(private http: Http) {
        this.serviceInterface = new ServiceInterface(http);
        this.scheduler = new Scheduler(this);
    }

    public checkConnection() : Promise<boolean> {
        return this.serviceInterface.checkConnection();
    }

    public createElement(element: IContainer, virtual: boolean, compoundId: string): Promise<void> {
        if (virtual) {
            return Promise.resolve(this.createElementVirtual(element, compoundId));
        }
        return this.createElementServer(element);
    }

    public readContents(url: string, virtual?: boolean): Promise<IContainer[]> {
        this.busy = true;
        if (virtual || this.scheduler.isVirtualElement(url)) {
            return Promise.resolve(this.readContentsVirtual(url)).then((contents: IContainer[]) => this.readContentsComplete(contents));
        }
        return this.readContentsServer(url).then((contents: IContainer[]) => this.readContentsComplete(contents));
    }

    private readContentsComplete(contents: IContainer[]): IContainer[] {
        this.busy = false;
        return contents;
    }

    public readElement(url: string, virtual?: boolean): Promise<IContainer> {
        if (virtual || this.scheduler.isVirtualElement(url)) {
            return Promise.resolve(this.readElementVirtual(url)).then((element: IContainer) => this.readElementComplete(element));
        }
        return this.readElementServer(url).then((element: IContainer) => this.readElementComplete(element));
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

    public undoCreate(url: string) {
        console.log("UNDO CREATE " + url);
        this.cache.deleteElement(url);
    }

    public undoUpdate(originalValue: IContainer) {
        console.log("UNDO UPDATE " + originalValue.url);
        this.cache.addElement(originalValue);
    }

    public undoDelete(originalValue: IContainer) {
        console.log("UNDO DELETE" + originalValue.url);
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
        console.log("CREATE " + element.url);
        return this.serviceInterface.createElement(element).then(() => {
            //this.cache.addElement(element);
            this.scheduler.resolve(element.url);
            console.log("CREATE " + element.url + " DONE");
        });
    }

    private readContentsServer(url: string): Promise<IContainer[]> {
        return this.serviceInterface.readContents(url).then((contents: IContainer[]) => {
            this.cache.updateContents(contents, url);
            contents.forEach((element: IContainer) => this.scheduler.initElement(element));
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
            //this.cache.addElement(element);
            this.scheduler.resolve(element.url);
            console.log("UPDATE " + element.url + " DONE");
        });
    }

    private deleteElementServer(url: string): Promise<void> {
        console.log("DELETE " + url);
        return this.serviceInterface.deleteElement(url).then(() => {
            //this.cache.deleteElement(url);
            this.scheduler.resolve(url);
            console.log("DELETE " + url + " DONE");
        });
    }

    public performOperations(url: string, operation: string, payload?:any): Promise<void>{
        this.busy=true;
        return this.serviceInterface.performOperation(url,operation,payload).then(
            () => {this.busy=false});
    }

    public performQuery(url: string, operation: string, parameters: { [key:string]:string; } ): Promise<any>{
        this.busy=true;
        return this.serviceInterface.performQuery(url,operation,parameters).then(
            (result) => {this.busy=false; return result;});
    }
}
