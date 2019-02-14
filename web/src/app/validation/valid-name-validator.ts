import { ElementValidatorBase } from './element-validator-base';
import { IContainer } from '../model/IContainer';
import { ValidationResult } from './validation-result';
import { Config } from '../config/config';
import { TranslateService } from '@ngx-translate/core';

export class ValidNameValidator extends ElementValidatorBase<IContainer> {

    constructor() {
        super();
    }

    public validate(element: IContainer, contents: IContainer[] = []): ValidationResult {
       let invalidCharacters: RegExp = new RegExp('[,;|]');
       if (element.name.match(invalidCharacters)) {
        let message = Config.ERROR_INVALID_NAME;
        return new ValidationResult(message, false, [element]);
       }
        return ValidationResult.VALID;
    }
}
