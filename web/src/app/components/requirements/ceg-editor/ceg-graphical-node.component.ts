import {CEGEffectNode} from '../../../model/CEGEffectNode';
import {CEGCauseNode} from '../../../model/CEGCauseNode';
import {Type} from '../../../util/Type';
import { Component, Input, ElementRef } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { Config } from '../../../config/config';
import { CEGNode } from '../../../model/CEGNode';

import { D3Service } from 'd3-ng2-service';
import { D3 } from 'd3-ng2-service';

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

    private d3: D3;

    constructor(private d3Service: D3Service, private elementRef: ElementRef, private router: Router, private route: ActivatedRoute) {
        this.d3 = d3Service.getD3();
        this.d3.select(this.elementRef.nativeElement)
            .call(this.d3.drag().on('drag', () => this.drag()));
    }

    private drag(): void {
        if (this.isWithinBounds) {
            this.node.x += this.d3.event.dx;
            this.node.y += this.d3.event.dy;
        }
    }

    private get isWithinBounds(): boolean {

        let destX: number = this.node.x + this.d3.event.dx;
        let destY: number = this.node.y + this.d3.event.dy;

        return destX >= 0 &&
            destX + Config.CEG_NODE_WIDTH <= this.editorSizeX &&
            destY >= 0 &&
            destY + Config.CEG_NODE_HEIGHT <= this.editorSizeY;
    }

    private get isCauseNode(): boolean {
        return Type.is(this.node, CEGCauseNode);
    }

    private get isEffectNode(): boolean {
        return Type.is(this.node, CEGEffectNode);
    }

    private get editorSizeX(): number {
        return this.elementRef.nativeElement.parentNode.getBoundingClientRect().width;
    }

    private get editorSizeY(): number {
        return this.elementRef.nativeElement.parentNode.getBoundingClientRect().height;
    }
}
