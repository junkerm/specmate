import { ValidationResult } from '../../../../../validation/validation-result';

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

    public addValidationResultsToCache(url: string, entries: ValidationResult[]): void {
        if (!(url in this.dataCache)) {
            this.dataCache[url] = [];
        }
        this.dataCache[url] = entries.filter(entry => !entry.isValid);
        // One Result might affect multiple elements
        // These Results are stored in the cache of every individual element
        for (const entry of entries.filter(entry => !entry.isValid)) {
            let urls = entry.elements.map(el => el.url);
            for (const elUrl of urls) {
                if (elUrl !== url) {
                    if (!(elUrl in this.dataCache)) {
                        this.dataCache[elUrl] = [];
                    }
                    this.dataCache[url].push(entry);
                    this.dataCache[elUrl].push(entry);
                }
            }
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
        this.dataCache = {};
    }
}
