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

    @Input()
    node: CEGNode;

    @Input()
    selected: boolean;

    @Input()
    valid: boolean;

    private grabbed: boolean = false;
    private prevX: number;
    private prevY: number;

    private field: string = 'offset';

    width: number = Config.CEG_NODE_WIDTH;
    height: number = Config.CEG_NODE_HEIGHT;

    constructor(private dataService: SpecmateDataService) { }

    private get title(): string {
        return this.node.variable + ' ' + this.node.condition;
    }

    private get type(): string {
        return this.node.type;
    }

    public drag(e: MouseEvent): void {
        e.preventDefault();
        
        if(this.grabbed) {
            let movementX = (this.prevX ? e.offsetX - this.prevX : 0);
            let movementY = (this.prevY ? e.offsetY - this.prevY : 0);
            let destX: number = this.node.x + movementX;
            let destY: number = this.node.y + movementY;
            if(this.isMove(movementX, movementY) && this.isWithinBounds(destX, destY)) {
                this.node.x = destX;
                this.node.y = destY;
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
        this.grabbed = true;
    }

    private dragEnd(): void {
        if(this.grabbed) {
            this.grabbed = false;
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
