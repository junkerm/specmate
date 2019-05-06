import { Injectable } from '@angular/core';
import { IContainer } from '../../../../../../../model/IContainer';
import { SelectedElementService } from '../../../../../side/modules/selected-element/services/selected-element.service';
import { SelectionRect } from '../../../../../side/modules/selected-element/util/selection-rect';
import { GraphicalElementBase } from '../../graphical-editor/elements/graphical-element-base';
import { Point, Square } from '../../graphical-editor/util/area';

@Injectable()
export class MultiselectionService {
    private rect: SelectionRect;
    private _components: GraphicalElementBase<IContainer>[] = [];
    private _selection: GraphicalElementBase<IContainer>[] = [];
    private rawPosition: Point;
    private relativePosition: Point;

    constructor(private selectedElementService: SelectedElementService) {
        this.rect = new SelectionRect();
        this.selectedElementService.selectionChanged.subscribe( (newSel: IContainer[]) => {
            this._selection = this._components.filter(comp =>
                newSel.some(c => c.url === comp.element.url)
            );
        });
    }

    public get selectionRect(): SelectionRect {
        return this.rect;
    }

    private getMousePosition(evt: MouseEvent, zoom: number): Point {
        // We can't use plain offsetX/Y since its relative to the element the mouse is hovering over
        // So if the user drags across a node the offset suddenly jumps to 0.
        // Instead we use offset to initialize and then update using the change in screen x/y.
        let deltaX = (evt.pageX - this.rawPosition.x) / zoom;
        let deltaY = (evt.pageY - this.rawPosition.y) / zoom;
        let xScaled = (this.relativePosition.x + deltaX);
        let yScaled = (this.relativePosition.y + deltaY);

        return new Point(Math.round(xScaled), Math.round(yScaled));
    }

    private get isDraggingRect(): boolean {
        // We only want to update the selection rect when we are actually using (i.e. drawing it)
        // This prevents invisible selection rects that change the selection without informing the user.
        return this.rect.isRectDrawing;
    }
    public mouseDown(evt: MouseEvent, zoom: number): void {
        this.rawPosition = {
            x: evt.pageX,
            y: evt.pageY
        };

        this.relativePosition = {
            x: evt.offsetX / zoom,
            y: evt.offsetY / zoom
        };

        this.rect.startRect(this.getMousePosition(evt, zoom));
        this._selection = [];
    }

    public mouseMove(evt: MouseEvent, zoom: number): void {
        if (!this.isDraggingRect) {
            return;
        }
        this.rect.updateRect(this.getMousePosition(evt, zoom));
    }

    public mouseUp(evt: MouseEvent, zoom: number): Promise<void> {
        if (!this.isDraggingRect) {
            return Promise.resolve();
        }
        this.rect.endRect(this.getMousePosition(evt, zoom));

        let xMin = this.rect.x;
        let yMin = this.rect.y;
        let xMax = this.rect.x + Math.max(this.rect.width, 1);
        let yMax = this.rect.y + Math.max(this.rect.height, 1);

        let area = new Square(xMin, yMin, xMax, yMax);

        let elements = this._components.filter(c => c.isInSelectionArea(area))
                                                       .map(c => c.element);
        if (evt.shiftKey) {
            this.selectedElementService.toggleSelection(elements);
        } else {
            this.selectedElementService.selectedElements = elements;
        }

        return Promise.resolve();
    }

    public get selection(): GraphicalElementBase<IContainer>[] {
        return this._selection;
    }

    public announceComponent(component: GraphicalElementBase<IContainer>) {
        this._components.push(component);
        // Newly created elements may not have an element yet.
        if (!component.element || this.selectedElementService.isSelected(component.element)) {
            this._selection.push(component);
        }
    }

    public retractComponent(component: GraphicalElementBase<IContainer>) {
        let ind = this._components.indexOf(component);
        this._components.splice(ind, 1);
    }
}
