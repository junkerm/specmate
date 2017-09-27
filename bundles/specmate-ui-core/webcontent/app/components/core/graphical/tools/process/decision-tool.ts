import { Config } from "../../../../../config/config";
import { SpecmateDataService } from "../../../../../services/data/specmate-data.service";
import { Id } from "../../../../../util/Id";
import { Url } from "../../../../../util/Url";
import { IContainer } from "../../../../../model/IContainer";
import { CreateToolBase } from "../create-tool-base";
import { DraggableElementBase } from "../../elements/draggable-element-base";
import { CreateNodeToolBase } from "../create-node-tool-base";
import { ProcessDecision } from "../../../../../model/ProcessDecision";
import { Process } from "../../../../../model/Process";

export class DecisionTool extends CreateNodeToolBase<ProcessDecision> {
    protected modelType: { className: string; } = Process;
    
    name: string = "Add Decision";
    icon: string = "plus";

    constructor(parent: IContainer, dataService: SpecmateDataService) {
        super(parent, dataService);
    }

    protected createNode(id: string, coords: {x: number, y: number}): ProcessDecision {
        var url: string = Url.build([this.parent.url, id]);
        var node: ProcessDecision = new ProcessDecision();
        node.name = Config.PROCESS_NEW_DECISION_NAME
        node.description = Config.PROCESS_NEW_DECISION_DESCRIPTION;
        node.id = id;
        node.url = url;
        node.x = coords.x;
        node.y = coords.y;
        return node;
    }
}