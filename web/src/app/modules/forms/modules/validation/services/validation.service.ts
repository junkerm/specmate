import { IContainer } from '../../../../../model/IContainer';
import { FieldMetaItem, MetaInfo } from '../../../../../model/meta/field-meta';
import { ValidatorBase } from '../base/validator-base';
import { RequiredFieldsValidator } from '../base/required-fields-validator';
import { ElementValidatorBase } from '../../../../../validation/element-validator-base';
import { ValidationResult } from '../../../../../validation/validation-result';
import { Injectable } from '@angular/core';

@Injectable()
export class ValidationService {

    private requiredFieldValidatorMap: {[className: string]: ValidatorBase<IContainer>};

    private elementValidators: {[className: string]: ElementValidatorBase<IContainer>[]};

    public isValid(element: IContainer, contents: IContainer[] = []): boolean {
        const requiredFieldsValid: boolean = this.getRequiredFieldsValidator(element).isValid(element);
        let results: ValidationResult[] =
            this.getElementValidators(element).map((validator: ElementValidatorBase<IContainer>) => validator.validate(element, contents));
        let elementValid: boolean = !results.some((result: ValidationResult) => !result.isValid);
        return requiredFieldsValid && elementValid;
    }

    public allValid(contents: IContainer[]): boolean {
        if (!contents) {
            return true;
        }
        return !contents.some((element: IContainer) => !this.isValid(element));
    }

    private getRequiredFieldsValidator(element: IContainer): ValidatorBase<IContainer> {
        if (!this.requiredFieldValidatorMap) {
            this.requiredFieldValidatorMap = {};
        }
        let type: string = element.className;
        if (!this.requiredFieldValidatorMap[type]) {
            let fieldMetaInfo: FieldMetaItem[] = MetaInfo[type];
            let requiredFields: string[] = [];
            fieldMetaInfo.forEach((metaItem: FieldMetaItem) => {
                if (metaItem.required) {
                    requiredFields.push(metaItem.name);
                }
            });
            this.requiredFieldValidatorMap[type] = new RequiredFieldsValidator(requiredFields);
        }

        return this.requiredFieldValidatorMap[type];
    }

    private getElementValidators(element: IContainer): ElementValidatorBase<IContainer>[] {
        return this.elementValidators[element.className];
    }

    public registerElementValidator<TV extends Function & (typeof ElementValidatorBase), TE extends {className: string}>(
        elementType: TE, validatorType: TV): void {
        if (this.elementValidators === undefined) {
            this.elementValidators = {};
        }
        const className: string = elementType.className;
        if (this.elementValidators[className] === undefined) {
            this.elementValidators[className] = [];
        }
        const validatorInstance: ElementValidatorBase<IContainer> = new (validatorType)();
        this.elementValidators[className].push(validatorInstance);
        console.log(this.elementValidators);
    }
}
