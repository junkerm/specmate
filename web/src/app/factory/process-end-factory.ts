import { PositionableElementFactoryBase } from "./positionable-element-factory-base";
import { ProcessEnd } from "../model/ProcessEnd";
import { IContainer } from "../model/IContainer";
import { Url } from "../util/Url";
import { Id } from "../util/Id";
import { Config } from "../config/config";

export class ProcessEndFactory extends PositionableElementFactoryBase<ProcessEnd> {
    public create(parent: IContainer, commit: boolean, compoundId?: string): Promise<ProcessEnd> {
        compoundId = compoundId ||Id.uuid;
        let id: string = Id.uuid;
        let url: string = Url.build([parent.url, id]);
        let node: ProcessEnd = new ProcessEnd();
        node.name = Config.PROCESS_NEW_END_NAME
        node.description = Config.PROCESS_NEW_END_DESCRIPTION;
        node.id = id;
        node.url = url;
        node.x = this.coords.x;
        node.y = this.coords.y;
        return this.dataService.createElement(node, true, compoundId).then(() => node);
    }
    
}