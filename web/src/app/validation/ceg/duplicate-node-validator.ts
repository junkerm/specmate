import { ElementValidatorBase } from '../element-validator-base';
import { Validator } from '../validator-decorator';
import { CEGModel } from '../../model/CEGModel';
import { ValidationResult } from '../validation-result';
import { CEGNode } from '../../model/CEGNode';
import { IContainer } from '../../model/IContainer';
import { Type } from '../../util/type';

@Validator()
export class DuplicateNodeValidator extends ElementValidatorBase<CEGModel> {
    public validate(element: CEGModel, contents: IContainer[]): ValidationResult {
        let nodes: CEGNode[] =
            contents.filter((element: IContainer) => Type.is(element, CEGNode)).map((element: IContainer) => element as CEGNode);
        for (let i = 0; i < nodes.length; i++) {
            let currentNode: CEGNode = nodes[i];
            let isDuplicate: boolean =
                nodes.some((otherNode: CEGNode) =>
                    otherNode.variable === currentNode.variable &&
                    otherNode.condition === currentNode.condition &&
                    otherNode !== currentNode);
            if (isDuplicate) {
                return new ValidationResult('Duplicate node found', false);
            }
        }
        return ValidationResult.VALID;
    }
}
