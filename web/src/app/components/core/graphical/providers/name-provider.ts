import { ProviderBase } from "./provider-base";
import { IContainer } from "../../../../model/IContainer";
import { Type } from "../../../../util/Type";
import { CEGModel } from "../../../../model/CEGModel";
import { Process } from "../../../../model/Process";

export class NameProvider extends ProviderBase {
    constructor(model: IContainer) {
        super(model);
    }

    public get name(): string {
        if(this.isCEGModel) {
            return "Cause Effect Graph";
        } else if(this.isProcessModel) {
            return "Process Model";
        }
    }
}