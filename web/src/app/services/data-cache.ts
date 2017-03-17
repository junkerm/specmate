import { IContainer } from "../model/IContainer";
import { Url } from "../util/Url";

export class DataCache {

    private _operationStore: { [key: string]: EOperation } = {};
    private _elementCache: { [key: string]: IContainer } = {};
    private _contentsCache: { [key: string]: IContainer[] } = {};

    public clearOperationStore(): void {
        this._operationStore = {};
    }

    public get operationStore(): { [key: string]: EOperation } {
        return this._operationStore;
    }

    public storeElement(element: IContainer): IContainer {
        if (this.isCached(element.url)) {
            return this.updateElement(element);
        }
        return this.createElement(element);
    }

    private createElement(element: IContainer): IContainer {
        this._operationStore[element.url] = EOperation.CREATE;
        if(!this._elementCache[element.url]) {
            this._elementCache[element.url] = element;
        } else {
            this.clone(element, this._elementCache[element.url]);
        }

        if(!this._contentsCache[Url.parent(element.url)]) {
            this._contentsCache[Url.parent(element.url)] = new Array<IContainer>();
        }
        let parentContents: IContainer[] = this._contentsCache[Url.parent(element.url)];

        let index: number = parentContents.indexOf(element);
        if (index < 0) {
            parentContents.push(element);
        } else {
            this.clone(element, parentContents[index]);
        }
        return this._elementCache[element.url];
    }

    private updateElement(element: IContainer): IContainer {
        this._operationStore[element.url] = EOperation.UPDATE;

        return this._elementCache[element.url];
    }

    public deleteElement(element: IContainer): void {
        this._operationStore[element.url] = EOperation.DELETE;
        this._elementCache[element.url] = undefined;

        let parentContents: IContainer[] = this._contentsCache[Url.parent(element.url)];
        let index: number = parentContents.indexOf(element);
        if (index >= 0) {
            parentContents.splice(index, 1);
        }
    }

    public getElement(url: string): IContainer {
        return this._elementCache[Url.clean(url)];
    }

    public getContents(url: string): IContainer[] {
        return this._contentsCache[Url.clean(url)];
    }

    private clone(source: any, target: any): void {
        for (let name in source) {
            if (typeof (source[name]) !== 'object' && typeof (source[name]) !== 'function') {
                target[name] = source[name];
            } else {
                this.clone(source[name], target[name]);
            }
        }
    }

    private isCached(url: string): boolean {
        return this._elementCache[url] != undefined && this._elementCache[url] != null;
    }

}

export enum EOperation {
    CREATE, DELETE, UPDATE, NONE
}
