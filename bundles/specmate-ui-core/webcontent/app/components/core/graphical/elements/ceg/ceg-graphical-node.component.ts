import { Component, Input, ElementRef } from '@angular/core';
import { Config } from "../../../../../config/config";
import { CEGNode } from "../../../../../model/CEGNode";
import { SpecmateDataService } from "../../../../../services/data/specmate-data.service";
import { Type } from "../../../../../util/Type";
import { Id } from "../../../../../util/Id";
import { IContainer } from "../../../../../model/IContainer";
import { GraphicalElementBase } from "../graphical-element-base";
import { DraggableElementBase } from "../draggable-element-base";

@Component({
    moduleId: module.id,
    selector: '[ceg-graphical-node]',
    templateUrl: 'ceg-graphical-node.component.svg',
    styleUrls: ['ceg-graphical-node.component.css']
})

export class CEGGraphicalNode extends DraggableElementBase<CEGNode> {
    public nodeType: { className: string; } = CEGNode;

    public get dimensions(): {width: number, height: number} {
        return {
            width: Config.CEG_NODE_WIDTH,
            height: Config.CEG_NODE_HEIGHT
        };
    }

    @Input()
    node: CEGNode;

    public get element(): CEGNode {
        return this.node;
    }

    @Input()
    selected: boolean;

    @Input()
    valid: boolean;

    private get title(): string {
        return this.node.variable + ' ' + this.node.condition;
    }

    private get type(): string {
        return this.node.type;
    }

    constructor(protected dataService: SpecmateDataService) {
        super();
    }
}
