import { GraphicalElementBase } from "./graphical-element-base";
import { IContainer } from "../../../../model/IContainer";
import { Config } from "../../../../config/config";
import { ISpecmatePositionableModelObject } from "../../../../model/ISpecmatePositionableModelObject";
import { Id } from "../../../../util/Id";
import { SpecmateDataService } from "../../../../services/data/specmate-data.service";

export abstract class DraggableElementBase<T extends ISpecmatePositionableModelObject> extends GraphicalElementBase<T> {

    private isGrabbed: boolean = false;
    private prevX: number;
    private prevY: number;
    private rawX: number;
    private rawY: number;

    protected abstract get dataService(): SpecmateDataService;

    public get x(): number {
        if(this.isOffX && !this.isGrabbed) {
            this.rawX = this.element.x;
        }
        return DraggableElementBase.roundToGrid(this.rawX);
    }

    public get y(): number {
        if(this.isOffY && !this.isGrabbed) {
            this.rawY = this.element.y;
        }
        return DraggableElementBase.roundToGrid(this.rawY);
    }

    private get isOffX(): boolean {
        return this.isCoordOff(this.rawX, this.element.x);
    }

    private get isOffY(): boolean {
        return this.isCoordOff(this.rawY, this.element.y);
    }

    private isCoordOff(rawCoord: number, nodeCoord: number): boolean {
        return rawCoord === undefined || Math.abs(rawCoord - nodeCoord) >= Config.CEG_EDITOR_GRID_SPACE;
    }

    public static roundToGrid(coord: number): number {
        let rest: number = coord % Config.CEG_EDITOR_GRID_SPACE;
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
        return destX >= 0 && destY >= 0;
    }
}