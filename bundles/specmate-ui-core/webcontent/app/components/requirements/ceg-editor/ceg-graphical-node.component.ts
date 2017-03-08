import { Component, Input, ElementRef } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { Config } from '../../../config/config';
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

    private d3: D3;
    private width: number = Config.CEG_NODE_WIDTH;
    private height: number = Config.CEG_NODE_HEIGHT;

    constructor(private d3Service: D3Service, private elementRef: ElementRef, private router: Router, private route: ActivatedRoute) {
        this.d3 = d3Service.getD3();
        this.d3.select(this.elementRef.nativeElement).call(
            this.d3.drag()
                .on('drag', () => this.drag())
        );
    }

    private drag(): void {
        if (this.isWithinBounds()) {
            this.node.x += this.d3.event.dx;
            this.node.y += this.d3.event.dy;
        }
    }

    private isWithinBounds(): boolean {

        var destX: number = this.node.x + this.d3.event.dx;
        var destY: number = this.node.y + this.d3.event.dy;

        return destX >= 0 &&
            destX + Config.CEG_NODE_WIDTH <= this.editorSizeX &&
            destY >= 0 &&
            destY + Config.CEG_NODE_HEIGHT <= this.editorSizeY;
    }

    private get editorSizeX(): number {
        return this.elementRef.nativeElement.parentNode.getBoundingClientRect().width;
    }
    private get editorSizeY(): number {
        return this.elementRef.nativeElement.parentNode.getBoundingClientRect().height;
    }

    select(): void {
        this.router.navigate([{ outlets: { 'ceg-node-details': [this.node.url, 'ceg-node-details'] } }]);
    }
}