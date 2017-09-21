import { Config } from "../../../../config/config";
import { SpecmateDataService } from "../../../../services/data/specmate-data.service";
import { Id } from "../../../../util/Id";
import { Url } from "../../../../util/Url";
import { IContainer } from "../../../../model/IContainer";
import { CreateToolBase } from "./create-tool-base";
import { DraggableElementBase } from "../elements/draggable-element-base";
import { IModelNode } from "../../../../model/IModelNode";

export abstract class CreateNodeToolBase<T extends IModelNode> extends CreateToolBase {

    color: string = "primary";
    cursor: string = 'cell';
    selectedElements: T[];
    done: boolean = false;

    constructor(protected parent: IContainer, protected dataService: SpecmateDataService) {
        super(parent, dataService);
    }

    click(event: MouseEvent): Promise<void> {
        return this.createNewNode({
            x: DraggableElementBase.roundToGrid(event.offsetX),
            y: DraggableElementBase.roundToGrid(event.offsetY)
        });
    }

    select(element: T): Promise<void> {
        this.selectedElements[0] = element;
        return Promise.resolve();
    }

    private createNewNode(coords: {x: number, y: number}): Promise<void> {
        let node = this.createNode(Id.uuid, coords);
        return this.createAndSelect(node).then(() => {
            this.done = true;
        });
    }

    protected abstract createNode(id: string, coords: {x: number, y: number}): T;
}