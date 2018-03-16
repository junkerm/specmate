import { ElementValidatorBase } from './element-validator-base';
import { ValidationService } from '../modules/forms/modules/validation/services/validation.service';

export function Validator(elementType: {className: string}) {
    return function(validatorTarget: any) {
        if (!(validatorTarget.prototype instanceof ElementValidatorBase)) {
            throw new Error('When you are using the @Validator-decorator, you must directly extend ElementValidatorBase');
        }

        ValidationService.registerElementValidator(elementType, validatorTarget);
    };
}
