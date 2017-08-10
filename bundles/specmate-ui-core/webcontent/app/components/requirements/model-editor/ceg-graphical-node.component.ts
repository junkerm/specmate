import {Id} from '../../../util/Id';
import { SpecmateDataService } from '../../../services/specmate-data.service';
import { CEGEffectNode } from '../../../model/CEGEffectNode';
import { CEGCauseNode } from '../../../model/CEGCauseNode';
import { Type} from '../../../util/Type';
import { Component, Input, ElementRef } from '@angular/core';

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

    @Input()
    valid: boolean;

    private d3: D3;

    width: number = Config.CEG_NODE_WIDTH;
    height: number = Config.CEG_NODE_HEIGHT;

    constructor(private d3Service: D3Service, private elementRef: ElementRef, private dataService: SpecmateDataService) {
        this.d3 = d3Service.getD3();
        this.d3.select(this.elementRef.nativeElement)
            .call(this.d3.drag().on('drag', () => this.drag()).on('end', () => this.dragEnd()));
    }

    private get title(): string {
        return this.node.variable + ' ' + this.node.condition;
    }

    private get type(): string {
        return this.node.type;
    }

    private drag(): void {
        if (this.isWithinBounds) {
            this.node.x += this.d3.event.dx;
            this.node.y += this.d3.event.dy;
        }
    }

    private dragEnd(): void {
        this.dataService.updateElement(this.node, true, Id.uuid);
    }

    private get isWithinBounds(): boolean {
        let destX: number = this.node.x + this.d3.event.dx;
        let destY: number = this.node.y + this.d3.event.dy;

        return destX >= 0 && destY >= 0;
    }

    private get isCauseNode(): boolean {
        return Type.is(this.node, CEGCauseNode);
    }

    private get isEffectNode(): boolean {
        return Type.is(this.node, CEGEffectNode);
    }
}
