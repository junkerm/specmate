import { ITool } from "./ITool";
import { CEGNode } from "../../../../model/CEGNode";
import { CEGConnection } from "../../../../model/CEGConnection";
import { Config } from "../../../../config/config";
import { SpecmateDataService } from "../../../../services/specmate-data.service";
import { Id } from "../../../../util/Id";
import { Url } from "../../../../util/Url";
import { IContainer } from "../../../../model/IContainer";

export class NodeTool implements ITool {
    name: string = "Add Node";
    icon: string = "plus";
    color: string = "primary";
    selectedElements: (CEGNode | CEGConnection)[];

    constructor(private parent: IContainer, private contents: IContainer[], private dataService: SpecmateDataService) {
        this.selectedElements = [];
    }

    get newNode(): CEGNode {
        return new CEGNode();
    }

    activate(): void {
        this.selectedElements = [];
    }

    deactivate(): void {
        this.selectedElements = [];
    }

    click(event: MouseEvent): void {
        this.addNode(event.offsetX, event.offsetY);
    }

    select(element: CEGNode | CEGConnection): void { }

    private addNode(x: number, y: number): void {
        var node: CEGNode = this.createNewNode(x, y);
        this.dataService.createElement(node)
        .then((contents: IContainer[]) => {
            
        });
        this.selectedElements[0] = node;
    }

    private createNewNode(x: number, y: number): CEGNode {
        var id: string = Id.generate(this.contents, Config.CEG_NODE_BASE_ID);
        var url: string = Url.build([this.parent.url, id]);

        var node: CEGNode = this.newNode;
        node.name = Config.CEG_NEW_NODE_NAME;
        node.description = Config.CEG_NEW_NODE_DESCRIPTION;
        node.id = id;
        node.url = url;
        node.x = x;
        node.y = y;
        return node;
    }
}