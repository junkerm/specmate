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

    public commit(): Promise<IContainer | IContainer[]> {
        return this.performCommit().then((result: IContainer | IContainer[]) => {
            this.cache.removeResolvedOperations();
            return result;
        });
    }

    private performCommit(): Promise<IContainer | IContainer[]> {
        let operations = this.cache.operationStore;
        for (let url in operations) {
            let operation: EOperation = operations[url];
            let element: IContainer = this.cache.getElement(url);
            console.log("COMMIT: " + EOperation[operation] + " " + url);
            this.cache.resolveOperation(url, operation);
            switch (operation) {
                case EOperation.CREATE:
                    return this.serverCreateElement(element)
                        .then((contents: IContainer[]) => {
                            this.cache.resolveOperation(url, operation);
                            return contents;
                        });
                case EOperation.UPDATE:
                    return this.serverUpdateElement(element)
                        .then((element: IContainer) => {
                            this.cache.resolveOperation(url, operation);
                            return element;
                        });
                case EOperation.DELETE:
                    return this.serverDeleteElement(url)
                        .then((contents: IContainer[]) => {
                            this.cache.resolveOperation(url, operation);
                            return contents;
                        });
                case EOperation.RESOLVED:

                break;
            }
        }
        return Promise.reject("Did not find anything to commit");
    }

    public getContents(url: string, forceServer?: boolean): Promise<IContainer[]> {
        if(!forceServer && this.cache.isNewElement(url)) {
            return Promise.resolve(this.cache.getContents(url));
        }
        return this.getContentsFresh(url);
    }

    public getElement(url: string, forceServer?: boolean): Promise<IContainer> {
        if(!forceServer && this.cache.isNewElement(url)) {
            return Promise.resolve(this.cache.getElement(url));
        }
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

    private serverDeleteElement(url: string): Promise<IContainer[]> {
        return this.http.delete(Url.urlDelete(url)).toPromise()
            .then(() => this.getContents(Url.parent(url)));
    }

    private getElementFresh(url: string): Promise<IContainer> {
        let fullUrl: string = Url.urlElement(url);
        return this.http.get(fullUrl).toPromise()
            .then(response => {
                let element: IContainer = response.json() as IContainer;
                return this.cache.storeElement(element, true);
            }).catch(this.handleError);
    }

    private getContentsFresh(url: string): Promise<IContainer[]> {
        let fullUrl: string = Url.urlContents(url);
        return this.http.get(fullUrl).toPromise()
            .then(response => {
                let contents: IContainer[] = response.json() as IContainer[];
                for (let i = 0; i < contents.length; i++) {
                    this.cache.storeElement(contents[i], true);
                }
                return this.cache.getContents(url);
            }).catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        return Promise.reject(error.message || error);
    }
}