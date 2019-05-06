import { ElementValidatorBase } from './element-validator-base';
import { IContainer } from '../model/IContainer';
import { ValidationResult } from './validation-result';
import { ValidationErrorSeverity } from './validation-error-severity';

export class TextLengthValidator extends ElementValidatorBase<IContainer> {
    public validate(element: IContainer, contents?: IContainer[]): ValidationResult {
        const keys = Object.keys(element);
        for (let i = 0; i < keys.length; i++) {
            const key = keys[i];
            const currentAttribute = element[key];
            if (typeof currentAttribute === 'string') {
                if (currentAttribute.length >= 4000) {
                    return new ValidationResult('errorTextTooLong',
                        false, [element], ValidationErrorSeverity.SAVE_DISABLED);
                }
            }
        }
        return ValidationResult.VALID;
    }

}
