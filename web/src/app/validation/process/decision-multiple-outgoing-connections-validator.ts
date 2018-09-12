import { Config } from '../../config/config';
import { IContainer } from '../../model/IContainer';
import { Process } from '../../model/Process';
import { ProcessDecision } from '../../model/ProcessDecision';
import { Type } from '../../util/type';
import { ElementValidatorBase } from '../element-validator-base';
import { ValidationResult } from '../validation-result';
import { Validator } from '../validator-decorator';

@Validator(Process)
export class DecisionMultipleOutgoingConnectionsValidator extends ElementValidatorBase<Process> {
    public validate(element: Process, contents: IContainer[]): ValidationResult {
        const invalidNodes: IContainer[] = contents
            .filter((element: IContainer) => Type.is(element, ProcessDecision))
            .filter((element: ProcessDecision) =>
                element.outgoingConnections && element.outgoingConnections.length < 2);
        if (invalidNodes.length > 0) {
            return new ValidationResult(Config.ERROR_PROCESS_DECISION_WITH_ONE_OR_LESS_OUTGOING_CONNECTIONS, false, invalidNodes);
        }
        return ValidationResult.VALID;
    }
}
