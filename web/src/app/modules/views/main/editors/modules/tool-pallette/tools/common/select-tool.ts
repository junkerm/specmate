import { DragAndDropToolBase } from '../drag-and-drop-tool-base';
import { IContainer } from '../../../../../../../../model/IContainer';
import { SelectedElementService } from '../../../../../../side/modules/selected-element/services/selected-element.service';
import { DraggableElementBase } from '../../../graphical-editor/elements/draggable-element-base';

type Point = {x: number, y: number};

export class SelectTool extends DragAndDropToolBase {
    public icon = 'crosshair';
    public color = 'primary';
    public cursor = 'crosshair';
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
        return Promise.resolve();
    }

    private getMousePosition(evt: MouseEvent, zoom: number): Point {
        return {
            x: DraggableElementBase.roundToGrid(evt.offsetX / zoom),
            y: DraggableElementBase.roundToGrid(evt.offsetY / zoom)
        };
    }

    private _selectionStart: Point;
    private _selectionEnd: Point;
    public mouseDown(event: MouseEvent, zoom: number): Promise<void> {
        this._selectionStart = this.getMousePosition(event, zoom);
        this._selectionEnd = this._selectionStart;

        return Promise.resolve();
    }

    public mouseDrag(event: MouseEvent, zoom: number): Promise<void> {
        this._selectionEnd = this.getMousePosition(event, zoom);
        return Promise.resolve();
    }

    public mouseUp(event: MouseEvent, zoom: number): Promise<void> {
        this._selectionEnd = this.getMousePosition(event, zoom);
        if (this._selectionStart.x > this._selectionEnd.x) {
            let tmp = this._selectionStart.x;
            this._selectionStart.x = this._selectionEnd.x;
            this._selectionEnd.x = tmp;
        }

        if (this._selectionStart.y > this._selectionEnd.y) {
            let tmp = this._selectionStart.y;
            this._selectionStart.y = this._selectionEnd.y;
            this._selectionEnd.y = tmp;
        }
        return Promise.resolve();
    }

    constructor(protected selectedElementService: SelectedElementService) {
        super(selectedElementService);
    }
}
