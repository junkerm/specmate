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

    constructor(private http: Http) {}

    getContent(url: string): Promise<IContainer> {
        return this.http.get(this.baseUrl + 'list').toPromise().then(response => {
            console.log(response);
            return response.json().data as IContainer;
        });
    }

    getChildren(url: string): Promise<IContainer[]> {
        return Promise.resolve(CHILDREN[url]);
    }
}