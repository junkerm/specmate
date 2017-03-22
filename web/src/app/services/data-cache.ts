import { Arrays } from '../util/Arrays';
import { IContainer } from "../model/IContainer";
import { Url } from "../util/Url";
import { Objects } from "../util/Objects";

export class DataCache {

    private elementStore: { [key: string]: IContainer } = {};
    private contentsStore: { [key: string]: IContainer[] } = {};

    public isCachedElement(url: string): boolean {
        return this.elementStore[url] !== undefined;
    }

    public isCachedContents(url: string): boolean {
        return this.contentsStore[url] !== undefined;
    }

    public addElement(element: IContainer): void {
        if (this.isCachedElement(element.url)) {
            this.updateElement(element);
            return;
        }
        this.createElement(element);
    }

    public readElement(url: string): IContainer {
        return this.elementStore[url];
    }

    public readContents(url: string): IContainer[] {
        if(!this.contentsStore[url]) {
            this.contentsStore[url] = [];
        }
        return this.contentsStore[url];
    }

    public deleteElement(url: string): void {
        // always remove from parent and then remove the element itself. Otherwise, removal from parent does not work, since this relies on the element being in the element cache.
        this.removeFromParentContents(url);
        this.elementStore[url] = undefined;
        let childrenUrls: string[] = this.getChildrenUrls(url);
        for (let i = 0; i < childrenUrls.length; i++) {
            this.removeFromParentContents(childrenUrls[i]);
            this.elementStore[childrenUrls[i]] = undefined;
        }
    }

    private updateElement(element: IContainer): void {
        Objects.clone(element, this.elementStore[element.url]);
    }

    private createElement(element: IContainer): Promise<void> {
        this.elementStore[element.url] = element;
        this.addToParentContents(element);
        return Promise.resolve();
    }

    private getParentContents(url: string): IContainer[] {
        let parentUrl: string = Url.parent(url);
        return this.contentsStore[parentUrl];
    }

    private getChildrenUrls(url: string): string[] {
        let childrenUrls: string[] = [];
        for (let storedUrl in this.contentsStore) {
            if (storedUrl.startsWith(url + Url.SEP)) {
                childrenUrls.push(storedUrl);
            }
        }
        return childrenUrls;
    }

    private addToParentContents(element: IContainer): void {
        var parentUrl: string = Url.parent(element.url);
        if (!this.isCachedContents(parentUrl)) {
            this.contentsStore[parentUrl] = [];
        }
        let parentContents = this.getParentContents(element.url);
        var index: number = parentContents.indexOf(element);
        if (parentContents.indexOf(element) < 0) {
            parentContents.push(element);
        } else {
            console.error('Tried to add an existing element to parent! ' + element.url);
        }
    }

    private removeFromParentContents(url: string): void {
        let parentContents = this.getParentContents(url);
        if (parentContents) {
            let element: IContainer = this.elementStore[url];
            Arrays.remove(parentContents, element);
        }
    }
}
