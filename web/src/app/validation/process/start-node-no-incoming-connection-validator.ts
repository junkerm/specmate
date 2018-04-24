import { ElementValidatorBase } from '../element-validator-base';
import { Process } from '../../model/Process';
import { Validator } from '../validator-decorator';
import { IContainer } from '../../model/IContainer';
import { ValidationResult } from '../validation-result';
import { Type } from '../../util/type';
import { Config } from '../../config/config';
import { ProcessStart } from '../../model/ProcessStart';

@Validator(Process)
export class StartNodeNoIncomingConnectionValidator extends ElementValidatorBase<Process> {
    public validate(element: Process, contents: IContainer[]): ValidationResult {
        const invalidNodes: IContainer[] = contents
            .filter((element: IContainer) => Type.is(element, ProcessStart))
            .filter((element: ProcessStart) => element.incomingConnections && element.incomingConnections.length > 0);
        if (invalidNodes.length > 0) {
            return new ValidationResult(Config.ERROR_PROCESS_START_INCOMING_CONNECTION, false, invalidNodes);
        }
        return ValidationResult.VALID;
    }
}
