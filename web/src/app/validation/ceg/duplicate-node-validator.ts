import { ElementValidatorBase } from '../element-validator-base';
import { Validator } from '../validator-decorator';
import { CEGModel } from '../../model/CEGModel';
import { ValidationResult } from '../validation-result';
import { CEGNode } from '../../model/CEGNode';
import { IContainer } from '../../model/IContainer';
import { Type } from '../../util/type';
import { Config } from '../../config/config';

@Validator(CEGModel)
export class DuplicateNodeValidator extends ElementValidatorBase<CEGModel> {
    public validate(element: CEGModel, contents: IContainer[]): ValidationResult {
        let nodes: CEGNode[] =
            contents.filter((element: IContainer) => Type.is(element, CEGNode)).map((element: IContainer) => element as CEGNode);
        let duplicates: Set<CEGNode> = new Set();
        for (let i = 0; i < nodes.length; i++) {
            let currentNode: CEGNode = nodes[i];
            let currentDuplicates: CEGNode[] =
                nodes.filter((otherNode: CEGNode) =>
                    otherNode.variable === currentNode.variable &&
                    otherNode.condition === currentNode.condition &&
                    otherNode !== currentNode &&
                    !duplicates.has(otherNode));
            currentDuplicates.forEach( node => duplicates.add(node));
        }

        let dupList = Array.from(duplicates.keys());
        if (dupList.length > 0) {
            return new ValidationResult(Config.ERROR_DUPLICATE_NODE, false, dupList);
        }
        return ValidationResult.VALID;
    }
}
