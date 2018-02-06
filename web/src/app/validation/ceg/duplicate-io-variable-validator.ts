import { ElementValidatorBase } from '../element-validator-base';
import { Validator } from '../validator-decorator';
import { CEGModel } from '../../model/CEGModel';
import { CEGNode } from '../../model/CEGNode';
import { IContainer } from '../../model/IContainer';
import { ValidationResult } from '../validation-result';
import { Type } from '../../util/type';
import { Config } from '../../config/config';

@Validator(CEGModel)
export class DuplicateIOVariableValidator extends ElementValidatorBase<CEGModel> {
    public validate(element: CEGModel, contents: IContainer[]): ValidationResult {
        let variableMap: { [variable: string]: string } = {};
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
                    return ValidationResult.VALID;
                }
            } else {
                variableMap[node.variable] = type;
            }
        }
        return new ValidationResult(Config.ERROR_DUPLICATE_IO_VARIABLE, false, []);
    }
}
