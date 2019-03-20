import { ElementValidatorBase } from '../element-validator-base';
import { Process } from '../../model/Process';
import { Validator } from '../validator-decorator';
import { IContainer } from '../../model/IContainer';
import { ValidationResult } from '../validation-result';
import { Type } from '../../util/type';
import { IModelNode } from '../../model/IModelNode';
import { ProcessEnd } from '../../model/ProcessEnd';
import { ProcessStart } from '../../model/ProcessStart';
import { ProcessDecision } from '../../model/ProcessDecision';
import { ProcessStep } from '../../model/ProcessStep';
import { ValidationMessage } from '../validation-message';

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
