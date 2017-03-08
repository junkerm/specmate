import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { IContainer } from '../model/IContainer';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class SpecmateDataService {

    // localhost:8080/services/rest/:url/list|details|delete

    baseUrl: string = 'services/rest/';

    detailsCache: IContainer[] = [];
    listCache: IContainer[][] = [];

    constructor(private http: Http) { }

    private cleanUrl(url: string) {
        while (url.indexOf("//") >= 0) {
            url = url.replace("//", "/");
        }
        return url;
    }

    public getList(url: string): Promise<IContainer[]> {
        if (this.listCache[url]) {
            return Promise.resolve(this.listCache[url]);
        }
        var fullUrl: string = this.cleanUrl(this.baseUrl + url + '/list');
        return this.http.get(fullUrl).toPromise().then(response => {
            var list = response.json() as IContainer[];
            this.listCache[url] = list;
            return list;
        });
    }

    public getDetails(url: string): Promise<IContainer> {
        if (this.detailsCache[url]) {
            return Promise.resolve(this.detailsCache[url]);
        }
        var fullUrl: string = this.cleanUrl(this.baseUrl + url + '/details');
        return this.http.get(fullUrl).toPromise().then(response => {
            var details: IContainer = response.json() as IContainer;
            this.detailsCache[url] = details;
            this.updateDetailsCacheDeep(details);
            return details;
        });
    }

    public updateDetailsCacheDeep(container: IContainer): void {
        if(!container['contents']) {
            return;
        }
        this.detailsCache[container.url] = container;
        for(var i = 0; i < container['contents'].length; i++) {
            this.updateDetailsCacheDeep(container['contents'][i]);
        }
    }

    public emptyCache(): void {
        this.detailsCache = [];
        this.listCache = [];
    }
}