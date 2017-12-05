import { ElementFactoryBase } from "./element-factory-base";
import { ProcessDecision } from "../model/ProcessDecision";
import { IContainer } from "../model/IContainer";
import { Url } from "../util/Url";
import { Id } from "../util/Id";
import { Config } from "../config/config";
import { PositionableElementFactoryBase } from "./positionable-element-factory-base";

export class ProcessDecisionFactory extends PositionableElementFactoryBase<ProcessDecision> {
    public create(parent: IContainer, commit: boolean, compoundId?: string): Promise<ProcessDecision> {
        
        compoundId = compoundId || Id.uuid;

        let id: string = Id.uuid;
        let url: string = Url.build([parent.url, id]);
        let node: ProcessDecision = new ProcessDecision();
        node.name = Config.PROCESS_NEW_DECISION_NAME
        node.description = Config.PROCESS_NEW_DECISION_DESCRIPTION;
        node.id = id;
        node.url = url;
        node.x = this.coords.x;
        node.y = this.coords.y;

        return this.dataService.createElement(node, true, compoundId).then(() => Promise.resolve(node));
    }
    
}