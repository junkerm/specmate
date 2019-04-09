import { IContainer } from '../model/IContainer';
import { ValidationResult } from './validation-result';

export abstract class ElementValidatorBase<T extends IContainer> {
    public abstract validate(element: T, contents: IContainer[]): ValidationResult;
}
