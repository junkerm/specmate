
import { IContainer } from "../model/IContainer";
import { Objects } from "../util/Objects";
import { EOperation } from "./operations";

export class Command {

    private _originalValue: IContainer;
    private _newValue: IContainer;
    private _changedFields: string[];

    constructor(public url: string, originalValue: IContainer, newValue: IContainer, public operation: EOperation) {
        this._originalValue = Objects.clone(originalValue);
        this._newValue = Objects.clone(newValue);
    }

    public get originalValue(): IContainer {
        return this._originalValue;
    }

    public get newValue(): IContainer {
        return this._newValue;
    }

    public resolve(): void {
        this.operation = EOperation.RESOLVED;
    }

    private get changedFields(): string[] {
        if(!this._changedFields) {
            this._changedFields = Objects.changedFields(this._originalValue, this._newValue);
        }
        return this._changedFields;
    }
}