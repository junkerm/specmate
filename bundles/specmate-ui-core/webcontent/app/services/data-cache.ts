import {Arrays} from '../util/Arrays';
import { IContainer } from "../model/IContainer";
import { Url } from "../util/Url";
import { Objects } from "../util/Objects";

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

    public storeElement(element: IContainer, cold?: boolean): IContainer {
        if (this.isElementCached(element.url)) {
            return this.updateElement(element, cold);
        }
        return this.createElement(element, cold);
    }

    private createElement(element: IContainer, cold: boolean): IContainer {
        if (!cold) {
            this.addOperation(element.url, EOperation.CREATE);
        }

        this._elementCache[element.url] = element;

        if (!this.isContentsCached(Url.parent(element.url))) {
            this._contentsCache[Url.parent(element.url)] = [];
        }
        if (!this.isContentsCached(element.url)) {
            this._contentsCache[element.url] = [];
        }
        let parentContents: IContainer[] = this._contentsCache[Url.parent(element.url)];

        let index: number = parentContents.indexOf(element);
        if (index < 0) {
            parentContents.push(element);
        } else {
            Objects.clone(element, parentContents[index]);
        }
        return this._elementCache[element.url];
    }

    private updateElement(element: IContainer, cold: boolean): IContainer {
        let cachedElement: IContainer = this._elementCache[element.url];
        if (!Objects.equals(element, cachedElement)) {
            if (!cold) {
                this.addOperation(element.url, EOperation.UPDATE);
            }
            Objects.clone(element, cachedElement);
        }
        return this._elementCache[element.url];
    }

    public deleteElement(element: IContainer): void {
        if(this.isNewElement(element.url)) {
            this.clearElement(element);
        }
        this.addOperation(element.url, EOperation.DELETE);
        this._elementCache[element.url] = undefined;

        Arrays.remove(this._contentsCache[Url.parent(element.url)], element);
    }

    public getElement(url: string): IContainer {
        return this._elementCache[Url.clean(url)];
    }

    public getContents(url: string): IContainer[] {
        return this._contentsCache[Url.clean(url)];
    }

    private isElementCached(url: string): boolean {
        return this._elementCache[url] != undefined && this._elementCache[url] != null;
    }

    private isContentsCached(url: string): boolean {
        return this._contentsCache[url] != undefined && this._contentsCache[url] != null;
    }

    private addOperation(url: string, operation: EOperation): void {
        let currentOperation: EOperation = this.operationStore[url];
        if(currentOperation === EOperation.CREATE && operation === EOperation.UPDATE) {
            return;
        }
        this._operationStore[url] = operation;
    }

    public resolveOperation(url: string, operation: EOperation): void {
        this.operationStore[url] = EOperation.RESOLVED;
    }

    public removeResolvedOperations(): void {
        let cleaned: { [key: string]: EOperation } = {};
        for (let url in this._operationStore) {
            if(this._operationStore[url] !== EOperation.RESOLVED) {
                cleaned[url] = this._operationStore[url];
            }
        }
        this._operationStore = cleaned;
    }

    public isNewElement(url: string): boolean {
        return this._operationStore[url] === EOperation.CREATE;
    }

    private clearElement(element: IContainer): void {
        this._operationStore[element.url] = EOperation.RESOLVED;
        this._elementCache[element.url] = undefined;
        this._contentsCache[element.url] = undefined;
        Arrays.remove(this._contentsCache[Url.parent(element.url)], element);
    }
}

export enum EOperation {
    CREATE, DELETE, UPDATE, RESOLVED
}
