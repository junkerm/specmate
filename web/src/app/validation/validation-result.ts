import { IContainer } from '../model/IContainer';
import { ValidationMessage } from './validation-message';
import { ValidationErrorSeverity } from './validation-error-severity';

export class ValidationResult {
    public message: ValidationMessage;
    constructor(message: string|ValidationMessage,
        public isValid: boolean, public elements: IContainer[],
            public severity: ValidationErrorSeverity = ValidationErrorSeverity.SAVE_ENABLED) {
        if (typeof message === 'string') {
            this.message = new ValidationMessage(message);
        } else {
            this.message = message;
        }
    }
    public static VALID: ValidationResult = new ValidationResult('', true, [], ValidationErrorSeverity.IGNORE);
}
