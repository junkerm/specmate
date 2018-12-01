import { ElementValidatorBase } from './element-validator-base';
import { IContainer } from '../model/IContainer';
import { ValidationResult } from './validation-result';
import { Config } from '../config/config';
import { TranslateService } from '@ngx-translate/core';

export class RequiredFieldsValidator extends ElementValidatorBase<IContainer> {

    constructor(private fields: string[]) {
        super();
    }

    public validate(element: IContainer, contents: IContainer[] = []): ValidationResult {
        const missingFields: string[] = this.fields.filter((field: string) => !element[field] || element[field].length === 0);
        if (missingFields.length > 0) {
            let fieldText = '[' + missingFields.join(', ') + ']';
            let message = Config.ERROR_MISSING_FIELDS.replace('{{fields}}', fieldText);
            return new ValidationResult(message, false, [element]);
        }
        return ValidationResult.VALID;
    }
}
