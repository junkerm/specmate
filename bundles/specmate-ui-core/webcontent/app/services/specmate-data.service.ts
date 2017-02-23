import { Injectable } from '@angular/core';
import { ISpecmateObject } from '../model/ISpecmateObject';

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

    getContent(url: string): Promise<ISpecmateObject> {
        return Promise.resolve(CONTENTS[url]);
    }

    getChildren(url: string): Promise<ISpecmateObject[]> {
        return Promise.resolve(CHILDREN[url]);
    }
}