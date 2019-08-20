import { IContainer } from '../../model/IContainer';
import { Process } from '../../model/Process';
import { ProcessStart } from '../../model/ProcessStart';
import { Type } from '../../util/type';
import { ElementValidatorBase } from '../element-validator-base';
import { ValidationMessage } from '../validation-message';
import { ValidationResult } from '../validation-result';
import { Validator } from '../validator-decorator';

@Validator(Process)
export class StartNodeValidator extends ElementValidatorBase<Process> {
    public validate(element: Process, contents: IContainer[]): ValidationResult {
        let hasSingleStartNode: boolean = contents.filter((element: IContainer) => Type.is(element, ProcessStart)).length === 1;
        if (!hasSingleStartNode) {
            return new ValidationResult(ValidationMessage.ERROR_NOT_ONE_START_NODE, false, [element]);
        }
        return ValidationResult.VALID;
    }
}
