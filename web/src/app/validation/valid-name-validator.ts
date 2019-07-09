import { IContainer } from '../model/IContainer';
import { MetaInfo } from '../model/meta/field-meta';
import { ElementValidatorBase } from './element-validator-base';
import { ValidationErrorSeverity } from './validation-error-severity';
import { ValidationMessage } from './validation-message';
import { ValidationResult } from './validation-result';
import { ValidationUtil } from './validation-util';

export class ValidNameValidator extends ElementValidatorBase<IContainer> {

    public validate(element: IContainer, contents: IContainer[] = []): ValidationResult {
        if (element === undefined || element.name === undefined) {
            return ValidationResult.VALID;
        }
        if (!ValidationUtil.isValidName(element.name)) {
            let message = ValidationMessage.ERROR_INVALID_NAME;
            return new ValidationResult(message, false, [element], ValidationErrorSeverity.SAVE_DISABLED);
        }
        return ValidationResult.VALID;
    }
}
