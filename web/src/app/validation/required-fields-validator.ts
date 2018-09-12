import { Config } from '../config/config';
import { IContainer } from '../model/IContainer';
import { ElementValidatorBase } from './element-validator-base';
import { ValidationResult } from './validation-result';

export class RequiredFieldsValidator extends ElementValidatorBase<IContainer> {

    constructor(private fields: string[]) {
        super();
    }

    public validate(element: IContainer, contents: IContainer[] = []): ValidationResult {
        const missingFields: string[] = this.fields.filter((field: string) => !element[field] || element[field].length === 0);
        if (missingFields.length > 0) {
            return new ValidationResult(Config.ERROR_MISSING_FIELDS, false, [element]);
        }
        return ValidationResult.VALID;
    }
}
