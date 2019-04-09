import { CEGModel } from '../../model/CEGModel';
import { CEGNode } from '../../model/CEGNode';
import { IContainer } from '../../model/IContainer';
import { Type } from '../../util/type';
import { ElementValidatorBase } from '../element-validator-base';
import { ValidationMessage } from '../validation-message';
import { ValidationResult } from '../validation-result';
import { Validator } from '../validator-decorator';

@Validator(CEGModel)
export class DuplicateIOVariableValidator extends ElementValidatorBase<CEGModel> {
    public validate(element: CEGModel, contents: IContainer[]): ValidationResult {
        let variableMap: { [variable: string]: string } = {};
        const invalidNodes: IContainer[] = [];
        for (let content of contents) {
            if (!Type.is(content, CEGNode)) {
                continue;
            }
            let node: CEGNode = content as CEGNode;
            let type: string;
            if (!node.incomingConnections || node.incomingConnections.length <= 0) {
                type = 'input';
            } else if (!node.outgoingConnections || node.outgoingConnections.length <= 0) {
                type = 'output';
            } else {
                type = 'intermediate';
            }
            let existing: string = variableMap[node.variable];
            if (existing) {
                if (existing === 'input' && type === 'output' || existing === 'output' && type === 'input') {
                    invalidNodes.push(node);
                }
            } else {
                variableMap[node.variable] = type;
            }
        }
        if (invalidNodes.length > 0) {
            return new ValidationResult(ValidationMessage.ERROR_DUPLICATE_IO_VARIABLE, false, invalidNodes);
        }
        return ValidationResult.VALID;
    }
}
