import { IContainer } from '../../../../../model/IContainer';
import { FieldMetaItem, MetaInfo } from '../../../../../model/meta/field-meta';
import { ElementValidatorBase } from '../../../../../validation/element-validator-base';
import { ValidationResult } from '../../../../../validation/validation-result';
import { Injectable } from '@angular/core';
import { RequiredFieldsValidator } from '../../../../../validation/required-fields-validator';
import { NavigatorService } from '../../../../navigation/modules/navigator/services/navigator.service';
import { Folder } from '../../../../../model/Folder';
import { Type } from '../../../../../util/type';

@Injectable()
export class ValidationService {

    private static DISABLED_CHILD_VALIDATION_TYPES: { className: string }[] = [Folder];

    constructor(private navigator: NavigatorService) { }

    private static requiredFieldValidatorMap: {[className: string]: RequiredFieldsValidator};

    private static elementValidators: {[className: string]: ElementValidatorBase<IContainer>[]};

    public validate(element: IContainer, contents: IContainer[] = []): ValidationResult[] {
        return this.validateElement(element, contents).concat(this.validateAll(contents));
    }

    private validateElement(element: IContainer, contents: IContainer[] = []): ValidationResult[] {
        const requiredFieldsResults: ValidationResult = this.getRequiredFieldsValidator(element).validate(element);
        const elementValidators = this.getElementValidators(element) || [];
        let elementResults: ValidationResult[] =
            elementValidators.map((validator: ElementValidatorBase<IContainer>) => validator.validate(element, contents));
        return elementResults.concat(requiredFieldsResults);
    }

    private validateAll(elements: IContainer[]): ValidationResult[] {
        return elements
            .map((element: IContainer) => this.validateElement(element))
            .reduce((a: ValidationResult[], b: ValidationResult[]) => a.concat(b), []);
    }

    public isValid(element: IContainer, contents: IContainer[] = []): boolean {
        const validationResults: ValidationResult[] = this.validate(element, contents);
        const isValid: boolean = !validationResults.some((validationResult: ValidationResult) => !validationResult.isValid);
        const currentInvalidContainsElement: boolean =
            this.currentInvalidElements.find((otherElement: IContainer) => otherElement === element) !== undefined;
        return isValid && !currentInvalidContainsElement;
    }

    public get currentValid(): boolean {
        const currentElement = this.navigator.currentElement;
        const ignoreContents = ValidationService.DISABLED_CHILD_VALIDATION_TYPES
            .find(disabledType => Type.is(currentElement, disabledType)) !== undefined;
        const contents = ignoreContents ? [] : this.navigator.currentContents;
        return this.isValid(this.navigator.currentElement, contents);
    }

    public get currentInvalidElements(): IContainer[] {
        if (this.navigator.currentElement === undefined) {
            return [];
        }
        return this.validate(this.navigator.currentElement, this.navigator.currentContents)
            .filter((result: ValidationResult) => !result.isValid)
            .map((result: ValidationResult) => result.elements)
            .reduce((prev: IContainer[], cur: IContainer[]) => prev.concat(cur), []);
    }

    public allValid(contents: IContainer[]): boolean {
        if (!contents) {
            return true;
        }
        return !contents.some((element: IContainer) => !this.isValid(element));
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

    public static registerElementValidator<TV extends Function & (typeof ElementValidatorBase), TE extends {className: string}>(
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
