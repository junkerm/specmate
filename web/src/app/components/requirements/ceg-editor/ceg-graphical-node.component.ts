import { Component, Input } from '@angular/core';
import { CEGNode } from '../../../model/CEGNode';

import { D3Service } from 'd3-ng2-service';

@Component({
    moduleId: module.id,
    selector: '[ceg-graphical-node]',
    templateUrl: 'ceg-graphical-node.component.svg'
})

export class CEGGraphicalNode {

    @Input()
    node: CEGNode;

    @Input()
    x: number;

    @Input()
    y: number;

    textX: number;
    textY: number;


    private offsetX: number;
    private offsetY: number;

    private dragging: boolean = false;

    constructor(private d3Service: D3Service) { }

    private changePos(x: number, y: number): void {
        this.x = x;
        this.y = y;
        this.textX = this.x;
        this.textY = this.y + 40;
    }

    ngDrag(evt: DragEvent) {
        if(evt.screenX == 0 && evt.screenY == 0) {
            return;
        }
        this.changePos(evt.offsetX, evt.offsetY);
    }

    ngDragStart(evt: DragEvent) {

    }

    ngDragEnd(evt: DragEvent) {
        this.changePos(evt.offsetX, evt.offsetY);
    }
}