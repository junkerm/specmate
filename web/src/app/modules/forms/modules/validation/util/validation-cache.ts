import { ValidationResult } from '../../../../../validation/validation-result';
import { Url } from '../../../../../util/url';
import { TranslateService } from '@ngx-translate/core';
import { IContainer } from '../../../../../model/IContainer';
import { ValidationErrorSeverity } from '../../../../../validation/validation-error-severity';


export type ValidationPair = { element: IContainer, message: string, severity: ValidationErrorSeverity };

export class ValidationCache {

    private dataCache: { [url: string]: ValidationPair[] } = {};

    constructor(private translate: TranslateService) { }

    public isValid(url: string): boolean {
        if (this.dataCache[url] !== undefined && this.dataCache[url].length > 0) {
            return false;
        }
        return true;
    }

    private emptyValidatatonResults: ValidationPair[] = [];
    public getValidationResults(url: string): ValidationPair[] {
        return this.dataCache[url] || this.emptyValidatatonResults;
    }

    public addValidationResultsToCache(validationResults: ValidationResult[]): void {
        for (const validationResult of validationResults.filter(validationResult => !validationResult.isValid)) {
            for (const element of validationResult.elements) {
                this.addToCache(element.url, {
                    element: element,
                    message: validationResult.message.getMessageTranslated(this.translate),
                    severity: validationResult.severity
                });
            }
        }
    }

    private addToCache(url: string, entry: ValidationPair): void {
        while (url != undefined) {
            if (!(url in this.dataCache)) {
                this.dataCache[url] = [];
            }
            if (this.dataCache[url].find(pair => pair.message === entry.message && pair.element === entry.element) === undefined) {
                this.dataCache[url].push(entry);
            }
            url = Url.parent(url);
        }
    }

    public clear(): void {
        const urls = [];
        for (let url in this.dataCache) {
            urls.push(url);
        }
        for (let url of urls) {
            delete this.dataCache[url];
        }
    }
}
