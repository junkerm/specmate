import { CEGNode } from "../../../../model/CEGNode";
import { CEGConnection } from "../../../../model/CEGConnection";
import { Config } from "../../../../config/config";
import { SpecmateDataService } from "../../../../services/specmate-data.service";
import { Id } from "../../../../util/Id";
import { Url } from "../../../../util/Url";
import { IContainer } from "../../../../model/IContainer";
import { CreateTool } from "./create-tool";

export class NodeTool extends CreateTool<CEGNode> {
    name: string = "Add Node";
    icon: string = "plus";
    color: string = "primary";
    selectedElements: CEGNode[];

    constructor(protected parent: IContainer, protected dataService: SpecmateDataService) {
        super(parent, dataService);
    }

    get newNode(): CEGNode {
        return new CEGNode();
    }

    click(event: MouseEvent): void {
        this.createNewNode(event.offsetX, event.offsetY);
    }

    select(element: CEGNode): void {
        this.selectedElements[0] = element;
    }

    private createNewNode(x: number, y: number): void {
        this.getNewId(Config.CEG_NODE_BASE_ID).then((id: string) => {
            let node = this.nodeFactory(id, x, y);
            this.createAndSelect(node);
        });
    }

    private nodeFactory(id: string, x: number, y: number): CEGNode {
        var url: string = Url.build([this.parent.url, id]);

        var node: CEGNode = this.newNode;
        node.name = Config.CEG_NEW_NODE_NAME;
        node.description = Config.CEG_NEW_NODE_DESCRIPTION;
        node.id = id;
        node.url = url;
        node.variable = Config.CEG_NODE_NEW_VARIABLE;
        node.operator = Config.CEG_NODE_NEW_OPERATOR;
        node.value = Config.CEG_NODE_NEW_VALUE;
        node.x = x;
        node.y = y;
        return node;
    }
}