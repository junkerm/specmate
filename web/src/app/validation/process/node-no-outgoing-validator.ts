import { Config } from '../../config/config';
import { IContainer } from '../../model/IContainer';
import { IModelNode } from '../../model/IModelNode';
import { Process } from '../../model/Process';
import { ProcessDecision } from '../../model/ProcessDecision';
import { ProcessEnd } from '../../model/ProcessEnd';
import { ProcessStart } from '../../model/ProcessStart';
import { ProcessStep } from '../../model/ProcessStep';
import { Type } from '../../util/type';
import { ElementValidatorBase } from '../element-validator-base';
import { ValidationResult } from '../validation-result';
import { Validator } from '../validator-decorator';

@Validator(Process)
export class NodeNoOutgoingValidator extends ElementValidatorBase<Process> {
    public validate(element: Process, contents: IContainer[]): ValidationResult {
        let processNodes: IModelNode[] =
            contents.filter((element: IContainer) =>
                Type.is(element, ProcessEnd) ||
                Type.is(element, ProcessStart) ||
                Type.is(element, ProcessDecision) ||
                Type.is(element, ProcessStep)) as IModelNode[];
        let nodesWithoutOutgoing: IContainer[] =
            processNodes.filter((element: IModelNode) =>
                (!element.outgoingConnections ||
                    (element.outgoingConnections && element.outgoingConnections.length === 0)) &&
                !Type.is(element, ProcessEnd));
        if (nodesWithoutOutgoing.length > 0) {
            return new ValidationResult(Config.ERROR_NODE_WITHOUT_OUTGOING, false, nodesWithoutOutgoing);
        }
        return ValidationResult.VALID;
    }
}
