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
export class NodeSingleOutgoingConnectionValidator extends ElementValidatorBase<Process> {
    public validate(element: Process, contents: IContainer[]): ValidationResult {
        const invalidNodes: IContainer[] = contents
            .filter((element: IContainer) => !Type.is(element, ProcessDecision))
            .filter((element: IContainer) =>
                (<any>element).outgoingConnections && (<any>element).outgoingConnections.length !== 1);
        if (invalidNodes.length > 0) {
            return new ValidationResult(Config.ERROR_PROCESS_NODE_MULTIPLE_OUTGOING_CONNECTIONS, false, invalidNodes);
        }
        return ValidationResult.VALID;
    }
}
