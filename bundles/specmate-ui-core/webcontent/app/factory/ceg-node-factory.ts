import { ElementFactoryBase } from "./element-factory-base";
import { CEGNode } from "../model/CEGNode";
import { IContainer } from "../model/IContainer";
import { SpecmateDataService } from "../services/data/specmate-data.service";
import { Config } from "../config/config";
import { Url } from "../util/Url";
import { Id } from "../util/Id";
import { componentFactoryName } from "@angular/compiler";
import { PositionableElementFactoryBase } from "./positionable-element-factory-base";

export class CEGNodeFactory extends PositionableElementFactoryBase<CEGNode> {

    public create(parent: IContainer, commit: boolean, compoundId?: string): Promise<CEGNode> {

        compoundId = compoundId || Id.uuid;

        let id: string = Id.uuid;
        var url: string = Url.build([parent.url, id]);
        var node: CEGNode = new CEGNode();
        node.name = Config.CEG_NEW_NODE_NAME;
        node.description = Config.CEG_NEW_NODE_DESCRIPTION;
        node.id = id;
        node.url = url;
        node.type = Config.CEG_NODE_NEW_TYPE;
        node.variable = Config.CEG_NODE_NEW_VARIABLE;
        node.condition = Config.CEG_NODE_NEW_CONDITION;
        node.x = this.coords.x;
        node.y = this.coords.y;

        return this.dataService.createElement(node, true, compoundId).then(() => node);
    }
}