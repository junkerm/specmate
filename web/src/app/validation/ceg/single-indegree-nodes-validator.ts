import { ElementValidatorBase } from '../element-validator-base';
import { CEGModel } from '../../model/CEGModel';
import { CEGNode } from '../../model/CEGNode';
import { IContainer } from '../../model/IContainer';
import { ValidationResult } from '../validation-result';
import { Type } from '../../util/type';
import { Validator } from '../validator-decorator';
import { ValidationMessage } from '../validation-message';

@Validator(CEGModel)
export class SingleIndegreeNodesValidator extends ElementValidatorBase<CEGModel> {

    public validate(element: CEGModel, contents: IContainer[]): ValidationResult {
        let invalidNodes: IContainer[] = contents.filter((element: IContainer) => {
            if (!Type.is(element, CEGNode)) {
                return false;
            }
            let node: CEGNode = element as CEGNode;
            if (node.incomingConnections === undefined || node.outgoingConnections === undefined) {
                return false;
            }
            return node.incomingConnections.length == 1 && node.outgoingConnections.length > 0;
        });

        if (invalidNodes.length === 0) {
            return ValidationResult.VALID;
        }
        return new ValidationResult(ValidationMessage.ERROR_SINGLE_INDEGREE_NODE, false, invalidNodes);
    }
}
