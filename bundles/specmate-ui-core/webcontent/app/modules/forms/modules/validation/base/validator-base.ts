import { IContainer } from "../../../../../model/IContainer";

export abstract class ValidatorBase<T extends IContainer> {
    public abstract isValid(element: T): boolean;
}