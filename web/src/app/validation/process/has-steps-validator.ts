import { ElementValidatorBase } from '../element-validator-base';
import { Process } from '../../model/Process';
import { Validator } from '../validator-decorator';
import { IContainer } from '../../model/IContainer';
import { ValidationResult } from '../validation-result';
import { IModelNode } from '../../model/IModelNode';
import { Type } from '../../util/type';
import { ProcessEnd } from '../../model/ProcessEnd';
import { ProcessStart } from '../../model/ProcessStart';
import { ProcessDecision } from '../../model/ProcessDecision';
import { ProcessStep } from '../../model/ProcessStep';
import { ValidationMessage } from '../validation-message';

@Validator(Process)
export class HasStepsValidator extends ElementValidatorBase<Process> {
    public validate(element: Process, contents: IContainer[]): ValidationResult {
        let processNodes: IModelNode[] =
            contents.filter((element: IContainer) =>
                Type.is(element, ProcessEnd) ||
                Type.is(element, ProcessStart) ||
                Type.is(element, ProcessDecision) ||
                Type.is(element, ProcessStep)) as IModelNode[];
        let processSteps: IModelNode[] = processNodes.filter((element: IModelNode) => Type.is(element, ProcessStep));
        if (processSteps.length === 0) {
            return new ValidationResult(ValidationMessage.ERROR_NO_STEPS, false, []);
        }
        return ValidationResult.VALID;
    }
}
