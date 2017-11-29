import { ValidatorBase } from "./validator-base";
import { IContainer } from "../../model/IContainer";

export class RequiredFieldsValidator extends ValidatorBase<IContainer> {
    
    constructor(private fields: string[]) {
        super();
    }

    public isValid(element: IContainer): boolean {
        return this.fields.some((field: string) => !element[field] || element[field].length === 0);
    }
}