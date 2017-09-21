import { Config } from "../../../../../config/config";
import { SpecmateDataService } from "../../../../../services/data/specmate-data.service";
import { Id } from "../../../../../util/Id";
import { Url } from "../../../../../util/Url";
import { IContainer } from "../../../../../model/IContainer";
import { CreateTool } from "../create-tool";
import { DraggableElementBase } from "../../elements/draggable-element-base";
import { CreateNodeToolBase } from "../create-node-tool-base";
import { ProcessStep } from "../../../../../model/ProcessStep";
import { Process } from "../../../../../model/Process";

export class StepTool extends CreateNodeToolBase<ProcessStep> {
    protected modelType: { className: string; } = Process;
    
    name: string = "Add Activity";
    icon: string = "plus";

    constructor(parent: IContainer, dataService: SpecmateDataService) {
        super(parent, dataService);
    }

    protected createNode(id: string, coords: {x: number, y: number}): ProcessStep {
        var url: string = Url.build([this.parent.url, id]);
        var node: ProcessStep = new ProcessStep();
        node.name = Config.PROCESS_NEW_STEP_NAME;
        node.description = Config.PROCESS_NEW_STEP_DESCRIPTION;
        node.id = id;
        node.url = url;
        node.x = coords.x;
        node.y = coords.y;
        return node;
    }
}