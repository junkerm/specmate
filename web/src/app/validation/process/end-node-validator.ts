import { ElementValidatorBase } from '../element-validator-base';
import { Process } from '../../model/Process';
import { Validator } from '../validator-decorator';
import { IContainer } from '../../model/IContainer';
import { ValidationResult } from '../validation-result';
import { Type } from '../../util/type';
import { ProcessEnd } from '../../model/ProcessEnd';
import { Config } from '../../config/config';

@Validator(Process)
export class EndNodeValidator extends ElementValidatorBase<Process> {
    public validate(element: Process, contents: IContainer[]): ValidationResult {
        let hasEndNodes: boolean = contents.filter((element: IContainer) => Type.is(element, ProcessEnd)).length > 0;
        if (!hasEndNodes) {
            return new ValidationResult(Config.ERROR_NO_END_NODE, false, []);
        }
        return ValidationResult.VALID;
    }
}
