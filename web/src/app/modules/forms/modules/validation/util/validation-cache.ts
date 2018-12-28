import { ValidationResult } from '../../../../../validation/validation-result';
import { IContainer } from '../../../../../model/IContainer';

export class ValidationCache {
    constructor() {
        this.dataCache = {};
        this.contentCache = {};
    }

    private dataCache: {[url: string]: ValidationResult[]};
    private contentCache: {[url: string]: string};

    public removeEntry(url: string) {
        if (!(url in this.dataCache)) {
            return;
        }
        let results = this.dataCache[url];
        delete this.dataCache[url];
        delete this.contentCache[url];
        /* Changing one element might make the results of other elements to become invalid as well:
        * A => Result[A,B], Result[A]
        * B => Result[A,B]
        *
        * delete A
        * A => []
        * B => Result[A,B]   <-- Now invalid and needs to be removed
        */
        for (const valRes of results) {
            for (const element of valRes.elements) {
                delete this.dataCache[element.url];
                delete this.contentCache[element.url];
            }
        }
    }

    private static getContentString(content: string[]) {
        return content.sort().join(' ');
    }

    public isCached(url: string, content: string[]) {
        if (!(url in this.dataCache)) {
            return false;
        }
        let cStr = ValidationCache.getContentString(content);
        return this.contentCache[url] === cStr;
    }

    public getEntry(url: string) {
        return this.dataCache[url];
    }

    public addEntriesToCache(url: string, content: string[], entries: ValidationResult[]) {
        if (!(url in this.dataCache)) {
            this.dataCache[url] = [];
        }
        this.dataCache[url] = entries;
        // One Result might affect multiple elements
        // These Results are stored in the cache of every individual element
        for (const entry of entries) {
            let urls = entry.elements.map(el => el.url);
            for (const elUrl of urls) {
                if (elUrl !== url) {
                    if (!(elUrl in this.dataCache)) {
                        this.dataCache[elUrl] = [];
                    }
                    this.dataCache[elUrl].push(entry);
                }
            }
        }
    }

    public findValidationResults(parentElement: IContainer, resultFilter?: (result: ValidationResult) => boolean):
                                {[url: string]: ValidationResult[]} {
        let out: {[url: string]: ValidationResult[]} = {};
        for (const url in this.dataCache) {
            if (this.dataCache.hasOwnProperty(url) && url.startsWith(parentElement.url)) {
                if (resultFilter !== undefined) {
                    let filteredResult = this.dataCache[url].filter(resultFilter);
                    if (filteredResult.length > 0) {
                        out[url] = filteredResult;
                    }
                } else {
                    out[url] = this.dataCache[url];
                }
            }
        }
        return out;
    }
}
