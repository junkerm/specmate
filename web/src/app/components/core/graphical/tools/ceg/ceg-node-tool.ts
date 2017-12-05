import { CEGNode } from "../../../../../model/CEGNode";
import { CEGConnection } from "../../../../../model/CEGConnection";
import { Config } from "../../../../../config/config";
import { SpecmateDataService } from "../../../../../services/data/specmate-data.service";
import { Id } from "../../../../../util/Id";
import { Url } from "../../../../../util/Url";
import { IContainer } from "../../../../../model/IContainer";
import { CreateToolBase } from "../create-tool-base";
import { DraggableElementBase } from "../../elements/draggable-element-base";
import { CreateNodeToolBase } from "../create-node-tool-base";
import { CEGModel } from "../../../../../model/CEGModel";
import { SelectedElementService } from "../../../../../services/editor/selected-element.service";
import { CEGNodeFactory } from "../../../../../factory/ceg-node-factory";
import { ElementFactoryBase } from "../../../../../factory/element-factory-base";

export class CEGNodeTool extends CreateNodeToolBase<CEGNode> {
    
    protected modelType: { className: string; } = CEGModel;
    
    public name: string = "Add Node";
    public icon: string = "plus";

    constructor(parent: IContainer, dataService: SpecmateDataService, selectedElementService: SelectedElementService) {
        super(parent, dataService, selectedElementService);
    }

    protected getElementFactory(coords: { x: number; y: number; }): ElementFactoryBase<CEGNode> {
        return new CEGNodeFactory(coords, this.dataService);
    }
}