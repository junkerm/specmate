import { IContainer } from "../../../../model/IContainer";
import { Config } from "../../../../config/config";
import { ISpecmatePositionableModelObject } from "../../../../model/ISpecmatePositionableModelObject";
import { Id } from "../../../../util/Id";
import { SpecmateDataService } from "../../../../services/data/specmate-data.service";
import { GraphicalNodeBase } from "./graphical-node-base";

export abstract class DraggableElementBase<T extends ISpecmatePositionableModelObject> extends GraphicalNodeBase<T> {

    private isGrabbed: boolean = false;
    private prevX: number;
    private prevY: number;

    private _rawX: number;
    private _rawY: number;

    private get rawX(): number {
        if(this._rawX === undefined) {
            return this.center.x;
        }
        return this._rawX;
    }

    private set rawX(x: number) {
        this._rawX = x;
    }

    private get rawY(): number {
        if(this._rawX === undefined) {
            return this.center.y;
        }
        return this._rawY;
    }

    private set rawY(y: number) {
        this._rawY = y;
    }

    protected abstract get dataService(): SpecmateDataService;

    private get x(): number {
        if(this.isOffX && !this.isGrabbed) {
            this.rawX = this.center.x;
        }
        return DraggableElementBase.roundToGrid(this.rawX);
    }

    private get y(): number {
        if(this.isOffY && !this.isGrabbed) {
            this.rawY = this.center.y;
        }
        return DraggableElementBase.roundToGrid(this.rawY);
    }

    private get isOffX(): boolean {
        return this.isCoordOff(this.rawX, this.center.x);
    }

    private get isOffY(): boolean {
        return this.isCoordOff(this.rawY, this.center.y);
    }

    private isCoordOff(rawCoord: number, nodeCoord: number): boolean {
        return rawCoord === undefined || Math.abs(rawCoord - nodeCoord) >= Config.GRAPHICAL_EDITOR_GRID_SPACE;
    }

    public get dragDummyPosition(): {x: number, y: number} {
        if(this.isGrabbed) {
            return {
                x: 0,
                y: 0
            };
        }
        return this.topLeft;
    }

    public get dragDummyDimensions(): {width: number, height: number} {
        if(this.isGrabbed) {
            return {
                width: this.topLeft.x + this.dimensions.width + 300,
                height: this.topLeft.y + this.dimensions.height + 300
            };
        }
        return this.dimensions;
    }

    public static roundToGrid(coord: number): number {
        let rest: number = coord % Config.GRAPHICAL_EDITOR_GRID_SPACE;
        if(rest === 0) {
            return coord;
        }
        return coord - rest;
    }

    public drag(e: MouseEvent): void {
        e.preventDefault();
        
        if(this.isGrabbed) {
            let movementX: number = (this.prevX ? e.offsetX - this.prevX : 0);
            let movementY:number = (this.prevY ? e.offsetY - this.prevY : 0);
            let destX: number = this.rawX + movementX;
            let destY: number = this.rawY + movementY;
            if(this.isMove(movementX, movementY) && this.isWithinBounds(destX, destY)) {
                this.rawX = destX;
                this.rawY = destY;
                // Works, because this.element.x === this.center.x && this.element.y === this.center.y
                this.element.x = this.x;
                this.element.y = this.y;
            }
            this.prevX = e.offsetX;
            this.prevY = e.offsetY;
        }
    }

    public leave(e: MouseEvent): void {
        e.preventDefault();
        this.dragEnd();
    }

    public grab(e: MouseEvent): void {
        e.preventDefault();
        this.dragStart(e);
    }

    public drop(e: MouseEvent): void {
        e.preventDefault();
        this.dragEnd();
    }

    private dragStart(e: MouseEvent): void {
        this.isGrabbed = true;
    }

    private dragEnd(): void {
        if(this.isGrabbed) {
            this.isGrabbed = false;
            this.prevX = undefined;
            this.prevY = undefined;
            this.dataService.updateElement(this.element, true, Id.uuid);
        }
    }

    private isMove(movementX: number, movementY: number): boolean {
        return movementX > 0 || movementX < 0 || movementY > 0 || movementY < 0;
    }

    private isWithinBounds(destX: number, destY: number): boolean {
        return destX - this.dimensions.width / 2 >= 0 && destY - this.dimensions.height / 2 >= 0;
    }
}