import { EOperation } from './e-operation';
import { IContainer } from '../../../../../model/IContainer';
import { Objects } from '../../../../../util/objects';
import { Config } from '../../../../../config/config';

export class Command {

    private _originalValue: IContainer;
    private _newValue: IContainer;
    private _changedFields: string[];
    private _resolved: boolean;

    constructor(
        public url: string,
        originalValue: IContainer,
        newValue: IContainer,
        public operation: EOperation,
        public compoundId: string) {

        this._originalValue = Objects.clone(originalValue);
        this._newValue = Objects.clone(newValue);
        if (operation === EOperation.INIT) {
            this.resolve();
        }
    }

    public get originalValue(): IContainer {
        return this._originalValue;
    }

    public get newValue(): IContainer {
        return this._newValue;
    }

    public resolve(): void {
        this._resolved = true;
        if (this.operation === EOperation.CREATE) {
            this.operation = EOperation.INIT;
            this._originalValue = Objects.clone(this._newValue);
        }
    }

    public get isResolved(): boolean {
        return this._resolved;
    }

    public get changedFields(): string[] {
        if (!this._changedFields) {
            let changedFields: string[] = Objects.changedFields(this._originalValue, this._newValue);
            if (changedFields === undefined) {
                return undefined;
            }
            this._changedFields = changedFields.sort();
        }
        return this._changedFields;
    }

    public changedSameFields(other: Command): boolean {
        if (this.changedFields === undefined || other.changedFields === undefined) {
            return false;
        }
        if (this.changedFields.length !== other.changedFields.length) {
            return false;
        }
        for (let i = 0; i < this.changedFields.length; i++) {
            if (this.changedFields[i] !== other.changedFields[i]) {
                return false;
            }
        }
        return true;
    }

    public get isDifference(): boolean {
        return this.changedFields !== undefined && this.changedFields.length > 0;
    }
    public mergeKeepOriginalValue(next: Command): Command {
        if (this.isMergeable(next)) {
            throw new Error(Config.MERGE_CONFLICT);
        }
        return new Command(this.url, this._originalValue, next._newValue, this.operation, next.compoundId);
    }

    private isMergeable(other: Command): boolean {
        return this.operation !== EOperation.UPDATE || this.operation !== other.operation;
    }
}
