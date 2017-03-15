import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { IContainer } from '../model/IContainer';
import 'rxjs/add/operator/toPromise';
import { Url } from '../util/Url';
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
        var fullUrl: string = Url.clean(Config.BASE_URL + url + '/list');
        return this.http.get(fullUrl).toPromise().then(response => {
            var list: IContainer[] = response.json() as IContainer[];
            this.listCache[url] = list;
            setTimeout(() => { this._ready = true }, 1000);
            return list;
        });
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
        var fullUrl: string = Url.clean(Config.BASE_URL + url + '/details');
        return this.http.get(fullUrl).toPromise().then(response => {
            var details: IContainer = response.json() as IContainer;
            this.detailsCache[url] = details;
            this.updateDetailsCacheDeep(details);
            setTimeout(() => { this._ready = true }, 1000);
            return details;
        });
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
        console.log("CANNOT DELETE ELEMENTS YET");

        var toDelete: string[] = [];

        for (var url in this.detailsCache) {
            if (url.startsWith(element.url + Url.SEP) || url === element.url) {
                toDelete.push(url);
            }
        }

        for (var i = 0; i < toDelete.length; i++) {
            var url = toDelete[i];
            this.getList(Url.parent(url)).then((contents: IContainer[]) => {
                var index = contents.indexOf(element);
                contents.splice(index, 1);
            });
            this.detailsCache[url] = undefined;
            this.listCache[url] = undefined;
        }
    }

    private updateDetailsCacheDeep(container: IContainer): void {
        if (!container['contents']) {
            return;
        }
        this.detailsCache[container.url] = container;
        for (var i = 0; i < container['contents'].length; i++) {
            this.updateDetailsCacheDeep(container['contents'][i]);
        }
    }

    public emptyCache(): void {
        this.detailsCache = [];
        this.listCache = [];
    }
}