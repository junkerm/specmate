import { ValidationResult } from '../../../../../validation/validation-result';
import { Url } from '../../../../../util/url';

export class ValidationCache {

    private dataCache: {[url: string]: ValidationResult[]} = {};

    public isValid(url: string): boolean {
        if(this.dataCache[url] !== undefined && this.dataCache[url].length > 0) {
            return false;
        }
        return true;
    }

    private emptyValidatatonResults: ValidationResult[] = [];
    public getValidationResults(url: string): ValidationResult[] {
        return this.dataCache[url] || this.emptyValidatatonResults;
    }

    public addValidationResultsToCache(entries: ValidationResult[]): void {
        for (const entry of entries.filter(entry => !entry.isValid)) {
            let urls = entry.elements.map(el => el.url);
            for (const elUrl of urls) {
                this.addToCache(elUrl, entry);
            }
        }
    }

    private addToCache(url: string, entry: ValidationResult): void {
        while (url != undefined) {
            if (!(url in this.dataCache)) {
                this.dataCache[url] = [];
            }
            this.dataCache[url].push(entry);
            url = Url.parent(url);
        }
    }

    public removeEntry(url: string) {
        if (!(url in this.dataCache)) {
            return;
        }
        let results = this.dataCache[url];
        delete this.dataCache[url];
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
            }
        }
    }

    public clear(): void {
        console.log('clearing...');
        const urls = [];
        for (let url in this.dataCache) {
            urls.push(url);
        }
        for (let url of urls) {
            delete this.dataCache[url];
        }
        console.log('clearing done');
    }
}
