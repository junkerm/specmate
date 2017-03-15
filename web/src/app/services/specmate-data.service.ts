import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { IContainer } from '../model/IContainer';
import 'rxjs/add/operator/toPromise';
import { Url } from '../util/Url';
import { Arrays } from '../util/Arrays';
import { Config } from '../config/config';

@Injectable()
export class SpecmateDataService {

    detailsCache: IContainer[] = [];
    listCache: IContainer[][] = [];

    private _ready: boolean = true;
    public get ready(): boolean {
        return this._ready;
    }

    constructor(private http: Http) { }

    public reGetList(url: string): Promise<IContainer[]> {
        this._ready = false;
        let fullUrl: string = Url.clean(Config.BASE_URL + url + '/list');
        return this.http.get(fullUrl).toPromise().then(response => {
            let list: IContainer[] = response.json() as IContainer[];
            this.listCache[url] = list;
            for (let i = 0; i < list.length; i++) {
                let details: IContainer = list[i];
                this.detailsCache[details.url] = details;
            }
            setTimeout(() => { this._ready = true; }, 1000);
            return list;
        }).catch(this.handleError);
    }

    public getList(url: string): Promise<IContainer[]> {
        if (this.listCache[url]) {
            return Promise.resolve(this.listCache[url]);
        }
        return this.reGetList(url);
    }

    public addList(element: IContainer, contents: IContainer[]): void {
        if (this.listCache[element.url]) {
            console.error('Element with URL ' + element.url + ' already existed.');
        }
        this.listCache[element.url] = contents;
    }

    public reGetDetails(url: string): Promise<IContainer> {
        this._ready = false;
        let fullUrl: string = Url.clean(Config.BASE_URL + url + '/details');
        return this.http.get(fullUrl).toPromise().then(response => {
            let details: IContainer = response.json() as IContainer;
            this.detailsCache[url] = details;
            setTimeout(() => { this._ready = true; }, 1000);
            return details;
        }).catch(this.handleError);
    }

    public getDetails(url: string): Promise<IContainer> {
        if (this.detailsCache[url]) {
            return Promise.resolve(this.detailsCache[url]);
        }
        return this.reGetDetails(url);
    }

    public addDetails(element: IContainer): void {
        if (this.detailsCache[element.url]) {
            console.error('Element with URL ' + element.url + ' already existed.');
        }
        this.detailsCache[element.url] = element;
        this.getList(Url.parent(element.url)).then(
            (contents: IContainer[]) => {
                contents.push(element);
            });
    }

    public removeDetails(element: IContainer): void {
        console.log('CANNOT DELETE ELEMENTS YET');

        for (let url in this.detailsCache) {
            if (url.startsWith(element.url + Url.SEP) || url === element.url) {
                let elementToDelete: IContainer = this.detailsCache[url];
                if (!elementToDelete) {
                    continue;
                }
                this.removeFromCache(elementToDelete);
            }
        }
    }

    public emptyCache(): void {
        this.detailsCache = [];
        this.listCache = [];
    }

    private removeFromCache(element: IContainer) {
        this.detailsCache[element.url] = undefined;
        this.listCache[element.url] = undefined;
        Arrays.remove(this.listCache[Url.parent(element.url)], element);
    }

    private handleError(error: any): Promise<any> {
        return Promise.reject(error.message || error);
    }
}
