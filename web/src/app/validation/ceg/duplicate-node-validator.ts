import { ElementValidatorBase } from '../element-validator-base';
import { Validator } from '../validator-decorator';
import { CEGModel } from '../../model/CEGModel';
import { ValidationResult } from '../validation-result';
import { CEGNode } from '../../model/CEGNode';
import { IContainer } from '../../model/IContainer';
import { Type } from '../../util/type';

@Validator(CEGModel)
export class DuplicateNodeValidator extends ElementValidatorBase<CEGModel> {
    public validate(element: CEGModel, contents: IContainer[]): ValidationResult {
        let nodes: CEGNode[] =
            contents.filter((element: IContainer) => Type.is(element, CEGNode)).map((element: IContainer) => element as CEGNode);
        let duplicates: CEGNode[] = [];
        for (let i = 0; i < nodes.length; i++) {
            let currentNode: CEGNode = nodes[i];
            let currentDuplicates: CEGNode[] =
                nodes.filter((otherNode: CEGNode) =>
                    otherNode.variable === currentNode.variable &&
                    otherNode.condition === currentNode.condition &&
                    otherNode !== currentNode);
            duplicates = duplicates.concat(currentDuplicates);
        }
        if (duplicates.length > 0) {
            return new ValidationResult('Duplicate nodes found', false, duplicates);
        }
        return ValidationResult.VALID;
    }
}
