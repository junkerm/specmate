import { Component, Input, ElementRef } from '@angular/core';
import { CEGNode } from '../../../model/CEGNode';

import { D3Service } from 'd3-ng2-service';
import { D3 } from 'd3-ng2-service';

@Component({
    moduleId: module.id,
    selector: '[ceg-graphical-node]',
    templateUrl: 'ceg-graphical-node.component.svg'
})

export class CEGGraphicalNode {

    @Input()
    node: CEGNode;

    private offsetX: number;
    private offsetY: number;

    private dragging: boolean = false;
    private d3: D3;


    constructor(private d3Service: D3Service, private elementRef: ElementRef) {
        this.d3 = d3Service.getD3();
        this.d3.select(this.elementRef.nativeElement).call(
            this.d3.drag()
                .on('drag', () => this.drag())
        );
    }

    private drag(): void {
        console.log("DRAG");
        this.node.x += this.d3.event.dx;
        this.node.y += this.d3.event.dy;
    }
}