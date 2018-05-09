import { ElementValidatorBase } from '../element-validator-base';
import { Process } from '../../model/Process';
import { Validator } from '../validator-decorator';
import { IContainer } from '../../model/IContainer';
import { ValidationResult } from '../validation-result';
import { ProcessConnection } from '../../model/ProcessConnection';
import { ProcessDecision } from '../../model/ProcessDecision';
import { IModelNode } from '../../model/IModelNode';
import { Type } from '../../util/type';
import { ProcessEnd } from '../../model/ProcessEnd';
import { ProcessStart } from '../../model/ProcessStart';
import { ProcessStep } from '../../model/ProcessStep';
import { Config } from '../../config/config';

@Validator(Process)
export class MissingConditionValidator extends ElementValidatorBase<Process> {
    public validate(element: Process, contents: IContainer[]): ValidationResult {
        let processNodes: IModelNode[] =
            contents.filter((element: IContainer) =>
                Type.is(element, ProcessEnd) ||
                Type.is(element, ProcessStart) ||
                Type.is(element, ProcessDecision) ||
                Type.is(element, ProcessStep)) as IModelNode[];

        let processConnections: ProcessConnection[] =
            contents.filter((element: IContainer) => Type.is(element, ProcessConnection)) as ProcessConnection[];
        let decisionNodes: ProcessDecision[] =
            processNodes.filter((element: IModelNode) => Type.is(element, ProcessDecision)) as ProcessDecision[];
        let decisionConnections: ProcessConnection[] =
            processConnections.filter((connection: ProcessConnection) =>
                decisionNodes.find((node: ProcessDecision) => node.url === connection.source.url) !== undefined);

        let invalidElements: IContainer[] =
            decisionConnections.filter((connection: ProcessConnection) =>
                connection.condition === undefined || connection.condition === null || connection.condition === '');

        if (invalidElements.length > 0) {
            return new ValidationResult(Config.ERROR_MISSING_CONDITION, false, invalidElements);
        }
        return ValidationResult.VALID;
    }
}
