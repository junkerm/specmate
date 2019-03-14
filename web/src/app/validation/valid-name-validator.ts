import { ElementValidatorBase } from './element-validator-base';
import { IContainer } from '../model/IContainer';
import { ValidationResult } from './validation-result';
import { Config } from '../config/config';
import { TranslateService } from '@ngx-translate/core';
import { FieldMetaItem, MetaInfo } from '../model/meta/field-meta';

export class ValidNameValidator extends ElementValidatorBase<IContainer> {

    constructor() {
        super();
    }

    public validate(element: IContainer, contents: IContainer[] = []): ValidationResult {
        let validPattern = MetaInfo.INamed[0].allowedPattern;
        let validName: RegExp = new RegExp(validPattern);
        if (!element.name.match(validName)) {
            let message = Config.ERROR_INVALID_NAME;
            return new ValidationResult(message, false, [element]);
        }
        return ValidationResult.VALID;
        }
}
