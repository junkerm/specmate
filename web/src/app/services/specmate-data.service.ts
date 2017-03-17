import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { IContainer } from '../model/IContainer';
import 'rxjs/add/operator/toPromise';
import { Url } from '../util/Url';
import { Arrays } from '../util/Arrays';
import { Config } from '../config/config';
import { DataCache, EOperation } from "./data-cache";

@Injectable()
export class SpecmateDataService {

    private cache: DataCache = new DataCache();
    private _ready: boolean = true;

    public get ready(): boolean {
        return this._ready;
    }

    constructor(private http: Http) { }

    public commit(): Promise<void> {
        let operations = this.cache.operationStore;
        for (let url in operations) {
            let operation: EOperation = operations[url];
            let element: IContainer = this.cache.getElement(url);
            switch (operation) {
                case EOperation.CREATE:
                    this.serverCreateElement(element);
                    break;
                case EOperation.UPDATE:
                    this.serverUpdateElement(element);
                    break;
                case EOperation.DELETE:
                    this.serverDeleteElement(element);
                    break;
            }
        }
        return Promise.resolve();
    }

    public getContents(url: string): Promise<IContainer[]> {
        return this.getContentsFresh(url);
    }

    public getElement(url: string): Promise<IContainer> {
        return this.getElementFresh(url);
    }

    public createElement(element: IContainer): Promise<IContainer[]> {
        this.cache.storeElement(element);
        return Promise.resolve(this.cache.getContents(Url.parent(element.url)));
    }

    public deleteElement(element: IContainer): Promise<IContainer[]> {
        this.cache.deleteElement(element);
        return Promise.resolve(this.cache.getContents(Url.parent(element.url)));
    }

    private serverCreateElement(element: IContainer): Promise<IContainer[]> {
        let url = element.url;
        element.url = undefined;
        return this.http.post(Url.urlCreate(url), element).toPromise()
            .then(response => {
                element.url = url;
                return this.getContents(Url.parent(url));
            });
    }

    private serverUpdateElement(element: IContainer): Promise<IContainer> {
        return this.http.put(Url.urlUpdate(element.url), element).toPromise()
            .then(response => {
                return this.getElement(element.url);
            });
    }

    private serverDeleteElement(element: IContainer): Promise<IContainer[]> {
        return this.http.delete(Url.urlDelete(element.url)).toPromise()
            .then(() => this.getContents(Url.parent(element.url)));
    }

    private getElementFresh(url: string): Promise<IContainer> {
        let fullUrl: string = Url.urlElement(url);
        return this.http.get(fullUrl).toPromise()
            .then(response => {
                let element: IContainer = response.json() as IContainer;
                return this.cache.storeElement(element);
            }).catch(this.handleError);
    }

    private getContentsFresh(url: string): Promise<IContainer[]> {
        let fullUrl: string = Url.urlContents(url);
        return this.http.get(fullUrl).toPromise()
            .then(response => {
                let contents: IContainer[] = response.json() as IContainer[];
                for (let i = 0; i < contents.length; i++) {
                    this.cache.storeElement(contents[i]);
                }
                return this.cache.getContents(url);
            }).catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        return Promise.reject(error.message || error);
    }
}