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
        if(Type.is(this.modelType, CEGModel)) {
            return "Cause Effect Graph";
        } else if(Type.is(this.modelType, Process)) {
            return "Process Model";
        }
    }
}