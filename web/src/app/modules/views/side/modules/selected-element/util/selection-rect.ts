import { Point } from '../../../../main/editors/modules/graphical-editor/util/area';

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

    public get isRectDrawing(): boolean {
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
