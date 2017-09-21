import { Config } from "../../../../../config/config";
import { SpecmateDataService } from "../../../../../services/data/specmate-data.service";
import { Id } from "../../../../../util/Id";
import { Url } from "../../../../../util/Url";
import { IContainer } from "../../../../../model/IContainer";
import { CreateToolBase } from "../create-tool-base";
import { DraggableElementBase } from "../../elements/draggable-element-base";
import { CreateNodeToolBase } from "../create-node-tool-base";
import { ProcessEnd } from "../../../../../model/ProcessEnd";
import { Process } from "../../../../../model/Process";

export class EndTool extends CreateNodeToolBase<ProcessEnd> {
    protected modelType: { className: string; } = Process;
    
    name: string = "Add End";
    icon: string = "plus";

    constructor(parent: IContainer, dataService: SpecmateDataService) {
        super(parent, dataService);
    }

    protected createNode(id: string, coords: {x: number, y: number}): ProcessEnd {
        var url: string = Url.build([this.parent.url, id]);
        var node: ProcessEnd = new ProcessEnd();
        node.name = Config.PROCESS_NEW_END_NAME
        node.description = Config.PROCESS_NEW_END_DESCRIPTION;
        node.id = id;
        node.url = url;
        node.x = coords.x;
        node.y = coords.y;
        return node;
    }
}