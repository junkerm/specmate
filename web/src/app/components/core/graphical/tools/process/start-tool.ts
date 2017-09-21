import { Config } from "../../../../../config/config";
import { SpecmateDataService } from "../../../../../services/data/specmate-data.service";
import { Id } from "../../../../../util/Id";
import { Url } from "../../../../../util/Url";
import { IContainer } from "../../../../../model/IContainer";
import { CreateToolBase } from "../create-tool-base";
import { DraggableElementBase } from "../../elements/draggable-element-base";
import { CreateNodeToolBase } from "../create-node-tool-base";
import { ProcessStart } from "../../../../../model/ProcessStart";
import { Process } from "../../../../../model/Process";

export class StartTool extends CreateNodeToolBase<ProcessStart> {
    protected modelType: { className: string; } = Process;
    
    name: string = "Add Start";
    icon: string = "plus";

    constructor(parent: IContainer, dataService: SpecmateDataService) {
        super(parent, dataService);
    }

    protected createNode(id: string, coords: {x: number, y: number}): ProcessStart {
        var url: string = Url.build([this.parent.url, id]);
        var node: ProcessStart = new ProcessStart();
        node.name = Config.PROCESS_NEW_START_NAME
        node.description = Config.PROCESS_NEW_START_DESCRIPTION;
        node.id = id;
        node.url = url;
        node.x = coords.x;
        node.y = coords.y;
        return node;
    }
}