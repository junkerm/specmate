import { Injectable } from '@angular/core';
import { SelectedElementService } from '../../../../../side/modules/selected-element/services/selected-element.service';
import { IContainer } from '../../../../../../../model/IContainer';
import { Area, Point, Square } from '../../graphical-editor/util/area';
import { GraphicalElementBase } from '../../graphical-editor/elements/graphical-element-base';
import { THIS_EXPR } from '../../../../../../../../../node_modules/@angular/compiler/src/output/output_ast';

@Injectable()
export class MultiselectionService {
    private rect: SelectionRect;

    constructor(private select: SelectedElementService) {
        this.rect = new SelectionRect();
        this.select.selectionChanged.subscribe( (evt: any) => { /*this._selection = [];*/ });
    }

    private _start: Point = {x: 0, y: 0};
    private _end: Point = {x: 0, y: 0};
    private _drawRect = false;

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

        let tmpSelected = this._components.filter(c => c.isInSelectionArea(area));
        this.select.selectedElements = tmpSelected.map(c => c.element);
        this._selection = tmpSelected;

        return Promise.resolve();
    }

    public get selection(): GraphicalElementBase<IContainer>[] {
        return this._selection;
    }

    public selectElem(elem: GraphicalElementBase<IContainer>) {
        this._selection = [elem];
    }

    public announceComponent(component: GraphicalElementBase<IContainer>) {
        this._components.push(component);
    }

    public retractComponent(component: GraphicalElementBase<IContainer>) {
        let ind = this._components.indexOf(component);
        this._components.splice(ind, 1);
    }
}

export class SelectionRect {
    private _start: Point = {x: 0, y: 0};
    private _end: Point = {x: 0, y: 0};
    private _drawRect = false;

    constructor() { }

    public get x(): number {
        return Math.min(this._start.x, this._end.x);
    }

    public get y(): number {
        return Math.min(this._start.y, this._end.y);
    }

    public get start(): Point {
        return this._start;
    }

    public get end(): Point {
        return this._end;
    }

    public get width(): number {
        return Math.abs(this._end.x - this._start.x);
    }

    public get height(): number {
        return Math.abs(this._end.y - this._start.y);
    }

    public get drawRect(): boolean {
        return this._drawRect;
    }

    public startRect(point: Point) {
        this._start = point;
        this._end = point;
        this._drawRect = true;
    }

    public updateRect(point: Point) {
        this._end = point;
    }

    public endRect(point: Point) {
        this.updateRect(point);
        this._drawRect = false;
    }
}
