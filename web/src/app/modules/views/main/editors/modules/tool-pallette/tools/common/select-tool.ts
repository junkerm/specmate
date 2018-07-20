import { DragAndDropToolBase } from '../drag-and-drop-tool-base';
import { IContainer } from '../../../../../../../../model/IContainer';
import { SelectedElementService } from '../../../../../../side/modules/selected-element/services/selected-element.service';
import { DraggableElementBase } from '../../../graphical-editor/elements/draggable-element-base';
import { MultiselectionService } from '../../services/multiselection.service';
import { Point } from '../../../graphical-editor/util/area';

export class SelectTool extends DragAndDropToolBase {
    public icon = 'mouse-pointer';
    public color = 'primary';
    public cursor = 'default';
    public name = 'tools.select';
    public selectedElements: any[] = [];
    public done = false;

    public activate() {
        this.selectedElements = [];
    }

    public deactivate() {
        this.selectedElements = [];
    }

    public click(event: MouseEvent, zoom: number): Promise<void> {
        return Promise.resolve();
    }

    public select(element: IContainer): Promise<void> {
        if (this.selectedElementService.isSelected(element)) {
            return Promise.resolve();
        }
        this.selectedElements = [element];
        this.selectedElementService.selectedElements = this.selectedElements;
        let blur = (<HTMLElement>document.activeElement).blur;
        if (blur) {
            (<HTMLElement>document.activeElement).blur();
        }
        return Promise.resolve();
    }

    private rawPosition: Point;
    private relativePosition: Point;
    private getMousePosition(evt: MouseEvent, zoom: number): Point {
        let deltaX = (evt.screenX - this.rawPosition.x) / zoom;
        let deltaY = (evt.screenY - this.rawPosition.y) / zoom;
        let xScaled = (this.relativePosition.x + deltaX);
        let yScaled = (this.relativePosition.y + deltaY);

        return {
            x: Math.round(xScaled),
            y: Math.round(yScaled)
        };
    }

    public mouseDown(event: MouseEvent, zoom: number): Promise<void> {
        this.rawPosition = {
            x: event.screenX,
            y: event.screenY
        };

        this.relativePosition = {
            x: event.offsetX,
            y: event.offsetY
        };

        this.rect.mouseDown(this.getMousePosition(event, zoom));
        return Promise.resolve();
    }

    public mouseDrag(event: MouseEvent, zoom: number): Promise<void> {
        this.rect.mouseMove(this.getMousePosition(event, zoom));
        return Promise.resolve();
    }

    public mouseUp(event: MouseEvent, zoom: number): Promise<void> {
        return this.rect.mouseUp(this.getMousePosition(event, zoom));
    }

    constructor(protected selectedElementService: SelectedElementService, private rect: MultiselectionService) {
        super(selectedElementService);
    }
}
