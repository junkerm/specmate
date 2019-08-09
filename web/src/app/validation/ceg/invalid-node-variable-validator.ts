import { CEGModel } from '../../model/CEGModel';
import { CEGNode } from '../../model/CEGNode';
import { IContainer } from '../../model/IContainer';
import { Type } from '../../util/type';
import { ElementValidatorBase } from '../element-validator-base';
import { ValidationMessage } from '../validation-message';
import { ValidationResult } from '../validation-result';
import { Validator } from '../validator-decorator';
import { ValidNameValidator } from '../valid-name-validator';
import { ValidationUtil } from '../validation-util';
import { ValidationErrorSeverity } from '../validation-error-severity';

@Validator(CEGNode)
export class InvalidNodeVariableValidator extends ElementValidatorBase<CEGNode> {

    public validate(element: CEGNode, contents: IContainer[]): ValidationResult {
        if (element === undefined || element.variable === undefined) {
            return ValidationResult.VALID;
        }
        if (!ValidationUtil.isValidName(element.variable)) {
            let message = ValidationMessage.ERROR_INVALID_VARIABLE;
            return new ValidationResult(message, false, [element], ValidationErrorSeverity.SAVE_DISABLED);
        }
        return ValidationResult.VALID;
    }
}
