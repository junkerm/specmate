import { ValidationResult } from './validation-result';
import { IContainer } from '../model/IContainer';
import { TranslateService } from '@ngx-translate/core';

export abstract class ElementValidatorBase<T extends IContainer> {
    public abstract validate(element: T, contents: IContainer[]): ValidationResult;
}
