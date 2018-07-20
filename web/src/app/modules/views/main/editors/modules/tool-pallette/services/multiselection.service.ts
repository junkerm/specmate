import { Injectable } from '@angular/core';
import { SelectedElementService } from '../../../../../side/modules/selected-element/services/selected-element.service';
import { IContainer } from '../../../../../../../model/IContainer';
import { Area, Point, Square } from '../../graphical-editor/util/area';
import { GraphicalElementBase } from '../../graphical-editor/elements/graphical-element-base';
import { SelectionRect } from '../../../../../side/modules/selected-element/util/selection-rect';

@Injectable()
export class MultiselectionService {
    private rect: SelectionRect;

    constructor(private select: SelectedElementService) {
        this.rect = new SelectionRect();
        this.select.selectionChanged.subscribe( (newSel: IContainer[]) => {
            this._selection = this._components.filter(comp =>
                newSel.some(c => c.url === comp.element.url)
            );
        });
    }

    private _components: GraphicalElementBase<IContainer>[] = [];
    private _selection: GraphicalElementBase<IContainer>[] = [];

    public get selectionRect(): SelectionRect {
        return this.rect;
    }

    public mouseDown(pos: Point): void {
        this.rect.startRect(pos);
        this._selection = [];
    }

    public mouseMove(pos: Point): void {
        this.rect.updateRect(pos);
    }

    public mouseUp(pos: Point): Promise<void> {
        this.rect.endRect(pos);

        let xMin = this.rect.x;
        let yMin = this.rect.y;
        let xMax = this.rect.x + this.rect.width;
        let yMax = this.rect.y + this.rect.height;

        let area = new Square(xMin, yMin, xMax, yMax);
        this.select.selectedElements = this._components.filter(c => c.isInSelectionArea(area))
                                                       .map(c => c.element);

        return Promise.resolve();
    }

    public get selection(): GraphicalElementBase<IContainer>[] {
        return this._selection;
    }

    public announceComponent(component: GraphicalElementBase<IContainer>) {
        this._components.push(component);
        // Newly created elements may not have an element yet.
        if (!component.element || this.select.isSelected(component.element)) {
            this._selection.push(component);
        }
    }

    public retractComponent(component: GraphicalElementBase<IContainer>) {
        let ind = this._components.indexOf(component);
        this._components.splice(ind, 1);
    }
}
