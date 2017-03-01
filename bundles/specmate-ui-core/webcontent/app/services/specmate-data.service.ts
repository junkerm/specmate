import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { IContainer } from '../model/IContainer';
import 'rxjs/add/operator/toPromise';

const CONTENTS = {
    '/': {
        name: 'Root',
        url: '/'
    },
    '/folder1': {
        name: 'Folder 1',
        url: '/folder1'
    },
    '/folder2': {
        name: 'Folder 2',
        url: '/folder2'
    },
    '/folder1/object1': {
        name: 'Object 1',
        url: '/folder1/object1'
    },
    '/folder2/object2': {
        name: 'Object 2',
        url: '/folder2/object2'
    }
}

const CHILDREN = {
    '/': [CONTENTS['/folder1'], CONTENTS['/folder2']],
    '/folder1': [CONTENTS['/folder1/object1']],
    '/folder2': [CONTENTS['/folder2/object2']]
}

@Injectable()
export class SpecmateDataService {

    // localhost:8080/services/rest/:url/list|details|delete

    baseUrl: string = 'services/rest/';

    detailsCache: IContainer[] = [];

    constructor(private http: Http) { }

    private cleanUrl(url: string) {
        while (url.indexOf("//") >= 0) {
            url = url.replace("//", "/");
        }
        return url;
    }

    getList(url: string): Promise<IContainer[]> {
        var fullUrl: string = this.cleanUrl(this.baseUrl + url + '/list');
        return this.http.get(fullUrl).toPromise().then(response => {
            return response.json() as IContainer[];
        });
    }

    getDetails(url: string): Promise<IContainer> {
        if (this.detailsCache[url]) {
            return Promise.resolve(this.detailsCache[url]);
        }
        var fullUrl: string = this.cleanUrl(this.baseUrl + url + '/details');
        return this.http.get(fullUrl).toPromise().then(response => {
            var details: IContainer = response.json() as IContainer;
            this.detailsCache[url] = details;
            return details;
        });
    }
}