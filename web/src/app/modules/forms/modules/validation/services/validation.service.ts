import { IContainer } from '../../../../../model/IContainer';
import { FieldMetaItem, MetaInfo } from '../../../../../model/meta/field-meta';
import { ElementValidatorBase } from '../../../../../validation/element-validator-base';
import { ValidationResult } from '../../../../../validation/validation-result';
import { Injectable } from '@angular/core';
import { RequiredFieldsValidator } from '../../../../../validation/required-fields-validator';
import { NavigatorService } from '../../../../navigation/modules/navigator/services/navigator.service';

@Injectable()
export class ValidationService {

    constructor(private navigator: NavigatorService) { }

    private static requiredFieldValidatorMap: {[className: string]: RequiredFieldsValidator};

    private static elementValidators: {[className: string]: ElementValidatorBase<IContainer>[]};

    public getValidatonResults(element: IContainer, contents: IContainer[] = []): ValidationResult[] {
        const requiredFieldsResults: ValidationResult = this.getRequiredFieldsValidator(element).validate(element);
        const elementValidators = this.getElementValidators(element) || [];
        let elementResults: ValidationResult[] =
            elementValidators.map((validator: ElementValidatorBase<IContainer>) => validator.validate(element, contents));
        return elementResults.concat(requiredFieldsResults);
    }

    public isValid(element: IContainer, contents: IContainer[] = []): boolean {
        return this.getValidatonResults(element, contents).some((validationResult: ValidationResult) => !validationResult.isValid);
    }

    public get currentValid(): boolean {
        return this.isValid(this.navigator.currentElement);
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
