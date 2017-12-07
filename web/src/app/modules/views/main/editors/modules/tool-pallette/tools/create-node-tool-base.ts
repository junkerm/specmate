import { IModelNode } from '../../../../../../../model/IModelNode';
import { CreateToolBase } from './create-tool-base';
import { IContainer } from '../../../../../../../model/IContainer';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { SelectedElementService } from '../../../../../side/modules/selected-element/services/selected-element.service';
import { DraggableElementBase } from '../../graphical-editor/elements/draggable-element-base';
import { ElementFactoryBase } from '../../../../../../../factory/element-factory-base';

export abstract class CreateNodeToolBase<T extends IModelNode> extends CreateToolBase {

    public color = 'primary';
    public cursor = 'cell';
    public selectedElements: T[];
    public done = false;

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
