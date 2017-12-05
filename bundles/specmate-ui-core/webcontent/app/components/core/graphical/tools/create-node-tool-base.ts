import { Config } from "../../../../config/config";
import { SpecmateDataService } from "../../../../services/data/specmate-data.service";
import { Id } from "../../../../util/Id";
import { Url } from "../../../../util/Url";
import { IContainer } from "../../../../model/IContainer";
import { CreateToolBase } from "./create-tool-base";
import { DraggableElementBase } from "../elements/draggable-element-base";
import { IModelNode } from "../../../../model/IModelNode";
import { SelectedElementService } from "../../../../services/editor/selected-element.service";
import { ElementFactoryBase } from "../../../../factory/element-factory-base";

export abstract class CreateNodeToolBase<T extends IModelNode> extends CreateToolBase {

    color: string = "primary";
    cursor: string = 'cell';
    selectedElements: T[];
    done: boolean = false;

    constructor(protected parent: IContainer, protected dataService: SpecmateDataService, selectedElementService: SelectedElementService) {
        super(parent, dataService, selectedElementService);
    }

    click(event: MouseEvent, zoom: number): Promise<void> {
        return this.createNewNodeAtCoords({
            x: DraggableElementBase.roundToGrid(event.offsetX / zoom),
            y: DraggableElementBase.roundToGrid(event.offsetY / zoom)
        });
    }

    select(element: T): Promise<void> {
        this.selectedElements[0] = element;
        this.selectedElementService.select(element);
        return Promise.resolve();
    }

    private createNewNodeAtCoords(coords: {x: number, y: number}): Promise<void> {
        return this.getElementFactory(coords).create(this.parent, false)
            .then((node: T) => this.select(node))
            .then(() => this.done = true)
            .then(() => Promise.resolve());
    }

    protected abstract getElementFactory(coords: {x: number, y: number}): ElementFactoryBase<T>;
}