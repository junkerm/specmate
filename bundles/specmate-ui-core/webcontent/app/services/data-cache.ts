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
        return this.elementStore[url] !== undefined;
    }

    public addElement(element: IContainer): void {
        if(this.isCachedElement(element.url)) {
            this.updateElement(element);
        }
        this.createElement(element);
    }

    public readElement(url: string): IContainer {
        return this.elementStore[url];
    }

    public readContents(url: string): IContainer[] {
        return this.contentsStore[url];
    }

    public deleteElement(url: string): void {
        this.elementStore[url] = undefined;
        this.removeFromParentContents(url);
        let childrenUrls: string[] = this.getChildrenUrls(url);
        for(let i = 0; i < childrenUrls.length; i++) {
            this.elementStore[childrenUrls[i]] = undefined;
            this.removeFromParentContents(childrenUrls[i]);
        }
    }

    private updateElement(element: IContainer): void {
        Objects.clone(element, this.elementStore[element.url]);
    }

    private createElement(element: IContainer): Promise<void> {
        this.elementStore[element.url] = element;
        return Promise.resolve();
    }

    private getParentContents(url: string): IContainer[] {
        let parentUrl: string = Url.parent(url);
        return this.contentsStore[parentUrl];
    }

    private getChildrenUrls(url: string): string[] {
        let childrenUrls: string[] = [];
        for(let storedUrl in this.contentsStore) {
            if(storedUrl.startsWith(url + Url.SEP)) {
                childrenUrls.push(storedUrl);
            }
        }
        return childrenUrls;
    }

    private addToParentContents(element: IContainer): void {
        let parentContents = this.getParentContents(element.url);
        var index: number = parentContents.indexOf(element);
        if(parentContents.indexOf(element) < 0) {
            parentContents.push(element);
        } else {
            // Should be not necessary, since we have the same elements here.
        }
    }

    private removeFromParentContents(url: string): void {
        let parentContents = this.getParentContents(url);
        if(parentContents) {
            let element: IContainer = this.elementStore[url];
            Arrays.remove(parentContents, element);
        }
    }
}
