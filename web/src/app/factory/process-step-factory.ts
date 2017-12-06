import { PositionableElementFactoryBase } from "./positionable-element-factory-base";
import { ProcessStep } from "../model/ProcessStep";
import { IContainer } from "../model/IContainer";
import { Id } from "../util/id";
import { Url } from "../util/url";
import { Config } from "../config/config";

export class ProcessStepFactory extends PositionableElementFactoryBase<ProcessStep> {
    public create(parent: IContainer, commit: boolean, compoundId?: string): Promise<ProcessStep> {
        compoundId = compoundId || Id.uuid;

        let id: string = Id.uuid;
        let url: string = Url.build([parent.url, id]);
        let node: ProcessStep = new ProcessStep();
        node.name = Config.PROCESS_NEW_STEP_NAME;
        node.description = Config.PROCESS_NEW_STEP_DESCRIPTION;
        node.id = id;
        node.url = url;
        node.x = this.coords.x;
        node.y = this.coords.y;

        return this.dataService.createElement(node, true, compoundId).then(() => node);
    }
    
}