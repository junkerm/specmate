import { IContainer } from "../../../../../model/IContainer";
import { Sort } from "../../../../../util/sort";
import { Objects } from "../../../../../util/objects";
import { Url } from "../../../../../util/url";
import { Arrays } from "../../../../../util/arrays";

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

    public updateContents(contents: IContainer[], url: string): void {
        if(!contents) {
            return;
        }
        Sort.sortArray(contents).forEach((element: IContainer) => this.addElement(element));
        if(!this.isCachedContents(url)) {
            this.contentsStore[url] = [];
        }
        let storedContents: IContainer[] = this.readContents(url);
        let elementsToDelete: IContainer[] = storedContents.filter((storedElement: IContainer) => {
                return contents.find((element: IContainer) => element.url === storedElement.url) === undefined;
        });
        elementsToDelete.forEach((element: IContainer) => this.deleteElement(element.url));
    }

    public readElement(url: string): IContainer {
        return this.elementStore[url];
    }

    public readContents(url: string): IContainer[] {
        if (!this.contentsStore[url]) {
            return undefined;
        }
        return this.contentsStore[url];
    }

    public deleteElement(url: string): void {
        // always remove from parent and then remove the element itself. Otherwise, removal from parent does not work, since this relies on the element being in the element cache.
        this.removeFromParentContents(url);
        delete this.elementStore[url];
        let childrenUrls: string[] = this.getChildrenUrls(url);
        for (let i = 0; i < childrenUrls.length; i++) {
            this.removeFromParentContents(childrenUrls[i]);
            delete this.elementStore[childrenUrls[i]];
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
        Sort.sortArrayInPlace(this.contentsStore[parentUrl]);
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
            Sort.insert(element, parentContents);
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
