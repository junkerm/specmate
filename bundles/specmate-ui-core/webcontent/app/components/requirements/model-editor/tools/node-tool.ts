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
    cursor: string = 'cell';
    selectedElements: CEGNode[];
    done: boolean = false;

    constructor(protected parent: IContainer, protected dataService: SpecmateDataService) {
        super(parent, dataService);
    }

    get newNode(): CEGNode {
        return new CEGNode();
    }

    click(event: MouseEvent): Promise<void> {
        return this.createNewNode(event.offsetX, event.offsetY);
    }

    select(element: CEGNode): Promise<void> {
        this.selectedElements[0] = element;
        return Promise.resolve();
    }

    private createNewNode(x: number, y: number): Promise<void> {
        let node = this.nodeFactory(Id.uuid, x, y);
        return this.createAndSelect(node).then(() => {
            this.done = true;
        });
    }

    private nodeFactory(id: string, x: number, y: number): CEGNode {
        var url: string = Url.build([this.parent.url, id]);

        var node: CEGNode = this.newNode;
        node.name = Config.CEG_NEW_NODE_NAME;
        node.description = Config.CEG_NEW_NODE_DESCRIPTION;
        node.id = id;
        node.url = url;
        node.type = Config.CEG_NODE_NEW_TYPE;
        node.variable = Config.CEG_NODE_NEW_VARIABLE;
        node.condition = Config.CEG_NODE_NEW_CONDITION;
        node.x = x;
        node.y = y;
        return node;
    }
}