import { IContainer } from '../../model/IContainer';
import { Process } from '../../model/Process';
import { ProcessEnd } from '../../model/ProcessEnd';
import { Type } from '../../util/type';
import { ElementValidatorBase } from '../element-validator-base';
import { ValidationMessage } from '../validation-message';
import { ValidationResult } from '../validation-result';
import { Validator } from '../validator-decorator';

@Validator(Process)
export class EndNodeValidator extends ElementValidatorBase<Process> {
    public validate(element: Process, contents: IContainer[]): ValidationResult {
        let hasEndNodes: boolean = contents.filter((element: IContainer) => Type.is(element, ProcessEnd)).length > 0;
        if (!hasEndNodes) {
            return new ValidationResult(ValidationMessage.ERROR_NO_END_NODE, false, []);
        }
        return ValidationResult.VALID;
    }
}
