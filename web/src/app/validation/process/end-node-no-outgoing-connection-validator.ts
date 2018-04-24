import { ElementValidatorBase } from '../element-validator-base';
import { Process } from '../../model/Process';
import { Validator } from '../validator-decorator';
import { IContainer } from '../../model/IContainer';
import { ValidationResult } from '../validation-result';
import { ProcessEnd } from '../../model/ProcessEnd';
import { Type } from '../../util/type';
import { Config } from '../../config/config';

@Validator(Process)
export class EndNodeNoOutgoingConnectionValidator extends ElementValidatorBase<Process> {
    public validate(element: Process, contents: IContainer[]): ValidationResult {
        const invalidNodes: IContainer[] = contents
            .filter((element: IContainer) => Type.is(element, ProcessEnd))
            .filter((element: ProcessEnd) => element.outgoingConnections && element.outgoingConnections.length > 0);
        if (invalidNodes.length > 0) {
            return new ValidationResult(Config.ERROR_PROCESS_END_OUTGOING_CONNECTION, false, invalidNodes);
        }
        return ValidationResult.VALID;
    }
}
