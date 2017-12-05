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
import { SelectedElementService } from "../../../../../services/editor/selected-element.service";
import { ElementFactoryBase } from "../../../../../factory/element-factory-base";
import { ProcessStartFactory } from "../../../../../factory/process-start-factory";

export class StartTool extends CreateNodeToolBase<ProcessStart> {
    
    protected modelType: { className: string; } = Process;
    
    public name: string = "Add Start";
    public icon: string = "plus";

    constructor(parent: IContainer, dataService: SpecmateDataService, selectedElementService: SelectedElementService) {
        super(parent, dataService, selectedElementService);
    }

    protected getElementFactory(coords: { x: number; y: number; }): ElementFactoryBase<ProcessStart> {
        return new ProcessStartFactory(coords, this.dataService);
    }
}