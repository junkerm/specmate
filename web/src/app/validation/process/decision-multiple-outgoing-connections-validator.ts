import { ElementValidatorBase } from '../element-validator-base';
import { Process } from '../../model/Process';
import { Validator } from '../validator-decorator';
import { IContainer } from '../../model/IContainer';
import { ValidationResult } from '../validation-result';
import { Type } from '../../util/type';
import { Config } from '../../config/config';
import { ProcessStep } from '../../model/ProcessStep';
import { ProcessDecision } from '../../model/ProcessDecision';
import { ProcessConnection } from '../../model/ProcessConnection';

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
