import { Id } from '../../../util/Id';
import { SpecmateDataService } from '../../../services/specmate-data.service';
import { CEGEffectNode } from '../../../model/CEGEffectNode';
import { CEGCauseNode } from '../../../model/CEGCauseNode';
import { Type} from '../../../util/Type';
import { Component, Input, ElementRef } from '@angular/core';

import { Config } from '../../../config/config';
import { CEGNode } from '../../../model/CEGNode';

@Component({
    moduleId: module.id,
    selector: '[ceg-graphical-node]',
    templateUrl: 'ceg-graphical-node.component.svg',
    styleUrls: ['ceg-graphical-node.component.css']
})

export class CEGGraphicalNode {

    public width: number = Config.CEG_NODE_WIDTH;
    public height: number = Config.CEG_NODE_HEIGHT;
    
    @Input()
    node: CEGNode;

    @Input()
    selected: boolean;

    @Input()
    valid: boolean;

    private isGrabbed: boolean = false;
    private prevX: number;
    private prevY: number;
    private rawX: number;
    private rawY: number;

    public get x(): number {
        if(this.isOffX && !this.isGrabbed) {
            this.rawX = this.node.x;
        }
        return CEGGraphicalNode.roundToGrid(this.rawX);
    }

    public get y(): number {
        if(this.isOffY && !this.isGrabbed) {
            this.rawY = this.node.y;
        }
        return CEGGraphicalNode.roundToGrid(this.rawY);
    }

    private get isOffX(): boolean {
        return this.isCoordOff(this.rawX, this.node.x);
    }

    private get isOffY(): boolean {
        return this.isCoordOff(this.rawY, this.node.y);
    }

    private isCoordOff(rawCoord: number, nodeCoord: number): boolean {
        return rawCoord === undefined || Math.abs(rawCoord - nodeCoord) >= Config.CEG_EDITOR_GRID_SPACE;
    }

    private get title(): string {
        return this.node.variable + ' ' + this.node.condition;
    }

    private get type(): string {
        return this.node.type;
    }

    constructor(private dataService: SpecmateDataService) { }

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
                this.node.x = this.x;
                this.node.y = this.y;
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
            this.dataService.updateElement(this.node, true, Id.uuid);
        }
    }

    private isMove(movementX: number, movementY: number): boolean {
        return movementX > 0 || movementX < 0 || movementY > 0 || movementY < 0;
    }

    private isWithinBounds(destX: number, destY: number): boolean {
        return destX >= 0 && destY >= 0;
    }

    private get isCauseNode(): boolean {
        return Type.is(this.node, CEGCauseNode);
    }

    private get isEffectNode(): boolean {
        return Type.is(this.node, CEGEffectNode);
    }
}
