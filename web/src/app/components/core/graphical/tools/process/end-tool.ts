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
import { SelectedElementService } from "../../../../../services/editor/selected-element.service";
import { ProcessEndFactory } from "../../../../../factory/process-end-factory";
import { ElementFactoryBase } from "../../../../../factory/element-factory-base";

export class EndTool extends CreateNodeToolBase<ProcessEnd> {
    
    protected modelType: { className: string; } = Process;
    
    public name: string = "Add End";
    public icon: string = "plus";

    constructor(parent: IContainer, dataService: SpecmateDataService, selectedElementService: SelectedElementService) {
        super(parent, dataService, selectedElementService);
    }

    protected getElementFactory(coords: { x: number; y: number; }): ElementFactoryBase<ProcessEnd> {
        return new ProcessEndFactory(coords, this.dataService);
    }
}