import { IContainer } from '../../model/IContainer';
import { IModelNode } from '../../model/IModelNode';
import { Process } from '../../model/Process';
import { ProcessDecision } from '../../model/ProcessDecision';
import { ProcessEnd } from '../../model/ProcessEnd';
import { ProcessStart } from '../../model/ProcessStart';
import { ProcessStep } from '../../model/ProcessStep';
import { Type } from '../../util/type';
import { ElementValidatorBase } from '../element-validator-base';
import { ValidationMessage } from '../validation-message';
import { ValidationResult } from '../validation-result';
import { Validator } from '../validator-decorator';

@Validator(Process)
export class NodeNoIncomingValidator extends ElementValidatorBase<Process> {
    public validate(element: Process, contents: IContainer[]): ValidationResult {
        let processNodes: IModelNode[] =
            contents.filter((element: IContainer) =>
                Type.is(element, ProcessEnd) ||
                Type.is(element, ProcessStart) ||
                Type.is(element, ProcessDecision) ||
                Type.is(element, ProcessStep)) as IModelNode[];
        let nodesWithoutIncomingConnections: IContainer[] =
            processNodes.filter((element: IModelNode) =>
                (!element.incomingConnections ||
                    (element.incomingConnections && element.incomingConnections.length === 0)) &&
                !Type.is(element, ProcessStart));
        if (nodesWithoutIncomingConnections.length > 0) {
            return new ValidationResult(ValidationMessage.ERROR_NODE_WITHOUT_INCOMING, false, nodesWithoutIncomingConnections);
        }
        return ValidationResult.VALID;
    }
}
