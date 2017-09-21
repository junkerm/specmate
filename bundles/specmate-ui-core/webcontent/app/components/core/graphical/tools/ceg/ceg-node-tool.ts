import { CEGNode } from "../../../../../model/CEGNode";
import { CEGConnection } from "../../../../../model/CEGConnection";
import { Config } from "../../../../../config/config";
import { SpecmateDataService } from "../../../../../services/data/specmate-data.service";
import { Id } from "../../../../../util/Id";
import { Url } from "../../../../../util/Url";
import { IContainer } from "../../../../../model/IContainer";
import { CreateTool } from "../create-tool";
import { DraggableElementBase } from "../../elements/draggable-element-base";
import { CreateNodeToolBase } from "../create-node-tool-base";
import { CEGModel } from "../../../../../model/CEGModel";

export class CEGNodeTool extends CreateNodeToolBase<CEGNode> {
    protected modelType: { className: string; } = CEGModel;
    
    name: string = "Add Node";
    icon: string = "plus";

    constructor(parent: IContainer, dataService: SpecmateDataService) {
        super(parent, dataService);
    }

    protected createNode(id: string, coords: {x: number, y: number}): CEGNode {
        var url: string = Url.build([this.parent.url, id]);
        var node: CEGNode = new CEGNode();
        node.name = Config.CEG_NEW_NODE_NAME;
        node.description = Config.CEG_NEW_NODE_DESCRIPTION;
        node.id = id;
        node.url = url;
        node.type = Config.CEG_NODE_NEW_TYPE;
        node.variable = Config.CEG_NODE_NEW_VARIABLE;
        node.condition = Config.CEG_NODE_NEW_CONDITION;
        node.x = coords.x;
        node.y = coords.y;
        return node;
    }
}