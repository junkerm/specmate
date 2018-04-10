import { ElementValidatorBase } from './element-validator-base';
import { IContainer } from '../model/IContainer';
import { ValidationResult } from './validation-result';
import { Config } from '../config/config';

export class RequiredFieldsValidator extends ElementValidatorBase<IContainer> {

    constructor(private fields: string[]) {
        super();
    }

    public validate(element: IContainer, contents: IContainer[] = []): ValidationResult {
        const missingFields: string[] = this.fields.filter((field: string) => !element[field] || element[field].length === 0);
        if (missingFields.length > 0) {
            return new ValidationResult('Missing Fields: ' + missingFields.join(', '), false, []);
        }
        return ValidationResult.VALID;
    }
}
