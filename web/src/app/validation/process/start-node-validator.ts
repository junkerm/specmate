import { ElementValidatorBase } from '../element-validator-base';
import { Process } from '../../model/Process';
import { Validator } from '../validator-decorator';
import { IContainer } from '../../model/IContainer';
import { ValidationResult } from '../validation-result';
import { Type } from '../../util/type';
import { ProcessStart } from '../../model/ProcessStart';
import { ValidationMessage } from '../validation-message';

@Validator(Process)
export class StartNodeValidator extends ElementValidatorBase<Process> {
    public validate(element: Process, contents: IContainer[]): ValidationResult {
        let hasSingleStartNode: boolean = contents.filter((element: IContainer) => Type.is(element, ProcessStart)).length === 1;
        if (!hasSingleStartNode) {
            return new ValidationResult(ValidationMessage.ERROR_NOT_ONE_START_NODE, false, []);
        }
        return ValidationResult.VALID;
    }
}
