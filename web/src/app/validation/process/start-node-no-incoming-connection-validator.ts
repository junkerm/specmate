import { ElementValidatorBase } from '../element-validator-base';
import { Process } from '../../model/Process';
import { Validator } from '../validator-decorator';
import { IContainer } from '../../model/IContainer';
import { ValidationResult } from '../validation-result';
import { Type } from '../../util/type';
import { ProcessStart } from '../../model/ProcessStart';
import { ValidationMessage } from '../validation-message';

@Validator(Process)
export class StartNodeNoIncomingConnectionValidator extends ElementValidatorBase<Process> {
    public validate(element: Process, contents: IContainer[]): ValidationResult {
        const invalidNodes: IContainer[] = contents
            .filter((element: IContainer) => Type.is(element, ProcessStart))
            .filter((element: ProcessStart) => element.incomingConnections && element.incomingConnections.length > 0);
        if (invalidNodes.length > 0) {
            return new ValidationResult(ValidationMessage.ERROR_PROCESS_START_INCOMING_CONNECTION, false, invalidNodes);
        }
        return ValidationResult.VALID;
    }
}
