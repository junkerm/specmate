import { IContainer } from '../model/IContainer';
import { MetaInfo } from '../model/meta/field-meta';
import { ElementValidatorBase } from './element-validator-base';
import { ValidationMessage } from './validation-message';
import { ValidationResult } from './validation-result';

export class ValidNameValidator extends ElementValidatorBase<IContainer> {

    public validate(element: IContainer, contents: IContainer[] = []): ValidationResult {
        if (MetaInfo.INamed === undefined || MetaInfo.INamed[0] === undefined) {
            return ValidationResult.VALID;
        }
        let validPattern = MetaInfo.INamed[0].allowedPattern;
        if (validPattern === undefined) {
            return ValidationResult.VALID;
        }
        let validName: RegExp = new RegExp(validPattern);
        if (element === undefined || element.name === undefined) {
            return ValidationResult.VALID;
        }
        if (!element.name.match(validName)) {
            let message = ValidationMessage.ERROR_INVALID_NAME;
            return new ValidationResult(message, false, [element]);
        }
        return ValidationResult.VALID;
    }
}
