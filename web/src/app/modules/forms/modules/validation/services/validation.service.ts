import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { IContainer } from '../../../../../model/IContainer';
import { FieldMetaItem, MetaInfo } from '../../../../../model/meta/field-meta';
import { ElementValidatorBase } from '../../../../../validation/element-validator-base';
import { RequiredFieldsValidator } from '../../../../../validation/required-fields-validator';
import { TextLengthValidator } from '../../../../../validation/text-length-validator';
import { ValidNameValidator } from '../../../../../validation/valid-name-validator';
import { ValidationResult } from '../../../../../validation/validation-result';
import { NavigatorService } from '../../../../navigation/modules/navigator/services/navigator.service';
import { ValidationCache, ValidationPair } from '../util/validation-cache';
import { ValidationErrorSeverity } from '../../../../../validation/validation-error-severity';

@Injectable()
export class ValidationService {

    private validationCache: ValidationCache;
    private validNameValidator: ValidNameValidator = new ValidNameValidator();
    private textLengthValidator: TextLengthValidator = new TextLengthValidator();

    constructor(private navigator: NavigatorService, translate: TranslateService) {
        this.validationCache = new ValidationCache(translate);
        navigator.hasNavigated.subscribe(() => this.validateCurrent());
    }

    public getValidationResults(parentElement: IContainer): ValidationPair[] {
        return parentElement ? this.validationCache.getValidationResults(parentElement.url) : [];
    }

    private static requiredFieldValidatorMap: { [className: string]: RequiredFieldsValidator };

    private static elementValidators: { [className: string]: ElementValidatorBase<IContainer>[] };

    public validate(element: IContainer, contents: IContainer[] = []): ValidationPair[] {
        return this.validateElement(element).concat(this.validateAll(contents));
    }

    public async validateCurrent(): Promise<void> {
        const element = this.navigator.currentElement;
        const contents = await this.navigator.currentContents;
        this.refreshValidation(element, contents);
    }

    private validateElement(element: IContainer): ValidationPair[] {
        if (element === undefined) {
            return [];
        }
        return this.validationCache.getValidationResults(element.url);
    }

    private validateAll(elements: IContainer[]): ValidationPair[] {
        return elements
            .map((element: IContainer) => this.validateElement(element))
            .reduce((a: ValidationPair[], b: ValidationPair[]) => a.concat(b), []);
    }

    public async refreshValidation(element: IContainer, contents: IContainer[] = [], clear = true): Promise<void> {
        if (clear) {
            this.validationCache.clear();
        }
        const requiredFieldsResults: ValidationResult = this.getRequiredFieldsValidator(element).validate(element);
        const validNameResult: ValidationResult = this.validNameValidator.validate(element);
        const textLengthValidationResult: ValidationResult = this.textLengthValidator.validate(element);
        const elementValidators = this.getElementValidators(element) || [];
        let elementResults: ValidationResult[] =
            elementValidators.map((validator: ElementValidatorBase<IContainer>) => validator.validate(element, contents))
                .concat(requiredFieldsResults)
                .concat(validNameResult)
                .concat(textLengthValidationResult);
        this.validationCache.addValidationResultsToCache(elementResults);
    }

    public get currentSeverities(): ValidationErrorSeverity[] {
        return this.validate(this.navigator.currentElement, this.navigator.currentContents)
            .map(validationResult => validationResult.severity);
    }

    public isValid(element: IContainer): boolean {
        const isValid = this.validationCache.isValid(element.url);
        return isValid;
    }

    public get currentValid(): boolean {
        return this.isValid(this.navigator.currentElement);
    }

    public allValid(contents: IContainer[]): boolean {
        if (!contents) {
            return true;
        }
        return contents.every((element: IContainer) => this.isValid(element));
    }

    private getRequiredFieldsValidator(element: IContainer): RequiredFieldsValidator {
        if (!ValidationService.requiredFieldValidatorMap) {
            ValidationService.requiredFieldValidatorMap = {};
        }
        let type: string = element.className;
        if (!ValidationService.requiredFieldValidatorMap[type]) {
            let fieldMetaInfo: FieldMetaItem[] = MetaInfo[type];
            let requiredFields: string[] = [];
            fieldMetaInfo.forEach((metaItem: FieldMetaItem) => {
                if (metaItem.required) {
                    requiredFields.push(metaItem.name);
                }
            });
            ValidationService.requiredFieldValidatorMap[type] = new RequiredFieldsValidator(requiredFields);
        }

        return ValidationService.requiredFieldValidatorMap[type];
    }

    private getElementValidators(element: IContainer): ElementValidatorBase<IContainer>[] {
        return ValidationService.elementValidators[element.className];
    }

    public static registerElementValidator<TV extends Function & (typeof ElementValidatorBase), TE extends { className: string }>(
        elementType: TE, validatorType: TV): void {
        if (ValidationService.elementValidators === undefined) {
            ValidationService.elementValidators = {};
        }
        const className: string = elementType.className;
        if (ValidationService.elementValidators[className] === undefined) {
            ValidationService.elementValidators[className] = [];
        }
        const validatorInstance: ElementValidatorBase<IContainer> = new (validatorType)();
        ValidationService.elementValidators[className].push(validatorInstance);
    }
}
