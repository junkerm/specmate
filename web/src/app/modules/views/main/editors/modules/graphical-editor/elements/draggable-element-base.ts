import { Input } from '@angular/core';
import { Config } from '../../../../../../../config/config';
import { ISpecmatePositionableModelObject } from '../../../../../../../model/ISpecmatePositionableModelObject';
import { Id } from '../../../../../../../util/id';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { ValidationService } from '../../../../../../forms/modules/validation/services/validation.service';
import { SelectedElementService } from '../../../../../side/modules/selected-element/services/selected-element.service';
import { MultiselectionService } from '../../tool-pallette/services/multiselection.service';
import { Coords } from '../util/coords';
import { GraphicalNodeBase } from './graphical-node-base';

export abstract class DraggableElementBase<T extends ISpecmatePositionableModelObject> extends GraphicalNodeBase<T> {
    constructor(selectedElementService: SelectedElementService, validationService: ValidationService,
                        multiselectionService: MultiselectionService) {
        super(selectedElementService, validationService, multiselectionService);
    }

    private isGrabbed = false;
    private isGrabTrigger = false;

    private prevX: number;
    private prevY: number;

    private _rawX: number;
    private _rawY: number;

    protected _zoom = 1;

    @Input()
    public set zoom(zoom: number) {
        this._zoom = zoom;
    }

    private get rawX(): number {
        if (this._rawX === undefined) {
            return this.center.x;
        }
        return this._rawX;
    }

    private set rawX(x: number) {
        this._rawX = x;
    }

    private get rawY(): number {
        if (this._rawY === undefined) {
            return this.center.y;
        }
        return this._rawY;
    }

    private set rawY(y: number) {
        this._rawY = y;
    }

    private userIsDraggingElsewhere = true;

    public get cursorClass() {
        if (this.userIsDraggingElsewhere) {
            return 'draggable-element-default';
        }
        if (this.isGrabbed) {
            return 'draggable-element-grabbed';
        }
        return 'draggable-element-grab';
    }

    protected abstract get dataService(): SpecmateDataService;

    private get x(): number {
        if (this.isOffX && !this.isGrabbed) {
            this.rawX = this.center.x;
        }
        return Coords.roundToGrid(this.rawX);
    }

    private get y(): number {
        if (this.isOffY && !this.isGrabbed) {
            this.rawY = this.center.y;
        }
        return Coords.roundToGrid(this.rawY);
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
        if (this.isGrabbed && this.isGrabTrigger) {
            return {
                x: 0,
                y: 0
            };
        }
        return this.topLeft;
    }

    public get dragDummyDimensions(): {width: number, height: number} {
        if (this.isGrabbed && this.isGrabTrigger) {
            return {
                width: this.topLeft.x + this.dimensions.width + 300,
                height: this.topLeft.y + this.dimensions.height + 300
            };
        }
        return this.dimensions;
    }

    public leave(e: MouseEvent): void {
        e.preventDefault();
        e.stopPropagation();
        this.dragEnd();
        this.userIsDraggingElsewhere = true;
    }

    public enter(e: MouseEvent): void {
        // Check if the icon should change depending on whether the user enters
        // the space with a button pressed.
        this.userIsDraggingElsewhere = e.buttons !== 0;
    }

    public grab(e: MouseEvent): void {
        e.preventDefault();
        e.stopPropagation();
        this.dragStart(e);
    }

    public drop(e: MouseEvent): void {
        e.preventDefault();
        e.stopPropagation();
        if (this.userIsDraggingElsewhere) {
            this.multiselectionService.mouseUp(e, this._zoom);
        }

        this.userIsDraggingElsewhere = false;
        this.dragEnd();
    }

    private moveable: DraggableElementBase<ISpecmatePositionableModelObject>[];

    private dragStart(e: MouseEvent): void {
        // Since we only move selected elements we need to update the selection
        // before starting the movement.
        if (!this.selected ) {
            if (e.shiftKey) {
                this.selectedElementService.addToSelection(this.element);
            } else {
                this.selectedElementService.select(this.element);
            }
        } else {
            if (e.shiftKey) {
                // A desection of the current Element aborts any drag & drop action.
                this.selectedElementService.deselectElement(this.element);
                return;
            }
        }
        this.isGrabTrigger = true;
        // Get all moveable, selected elements
        this.moveable = this.multiselectionService.selection.map( elem => <DraggableElementBase<ISpecmatePositionableModelObject>>elem)
            .filter(elem => (elem.moveNode !== undefined) && (elem.dropNode !== undefined));
        this.moveable.forEach(elem => {
            elem.isGrabbed = true;
            // All elements should jump to the next position at the same time, so snap to the grid.
            elem.snapToGrid();
        });
    }

    private snapToGrid() {
        this.rawX = Coords.roundToGrid(this.rawX);
        this.rawY = Coords.roundToGrid(this.rawY);
    }

    public drag(e: MouseEvent): void {
        e.preventDefault();

        if (this.isGrabbed) {
             e.stopPropagation();
            // Compute movement delta
            let movementX: number = (this.prevX ? e.offsetX - this.prevX : 0); // this._zoom;
            let movementY: number = (this.prevY ? e.offsetY - this.prevY : 0); // this._zoom;
            // Update prev Position
            this.prevX = e.offsetX;
            this.prevY = e.offsetY;

            if (!this.isMove(movementX, movementY)) {
                return;
            }

            // Check if all elements can move
            let allowedMove = this.moveable.every(e => e.isAllowedMove(movementX, movementY));

            // Inform all movable elements
            if (allowedMove) {
                this.moveable.forEach(elem => elem.moveNode(movementX, movementY));
            }
        }
    }

    public isAllowedMove(movementX: number, movementY: number) {
        let destX: number = this.rawX + movementX;
        let destY: number = this.rawY + movementY;
        return this.isWithinBounds(destX, destY);
    }

    public moveNode(movementX: number, movementY: number) {
        let destX: number = this.rawX + movementX;
        let destY: number = this.rawY + movementY;
        this.rawX = destX;
        this.rawY = destY;
        // Works, because this.element.x === this.center.x && this.element.y === this.center.y
        this.element.x = this.x;
        this.element.y = this.y;
    }

    public dropNode(): void {
        this.isGrabbed = false;
        this.isGrabTrigger = false;
        this.prevX = undefined;
        this.prevY = undefined;
        this.dataService.updateElement(this.element, true, Id.uuid);
    }

    private dragEnd(): void {
        if (this.isGrabbed) {
            this.moveable.forEach(elem => elem.dropNode());
        }
    }

    private isMove(movementX: number, movementY: number): boolean {
        return movementX > 0 || movementX < 0 || movementY > 0 || movementY < 0;
    }

    private isWithinBounds(destX: number, destY: number): boolean {
        return destX - this.dimensions.width / 2 >= 0 && destY - this.dimensions.height / 2 >= 0;
    }
}
