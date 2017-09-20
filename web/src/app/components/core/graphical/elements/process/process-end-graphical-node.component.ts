import { Component, Input, ElementRef } from '@angular/core';
import { Config } from "../../../../../config/config";
import { SpecmateDataService } from "../../../../../services/data/specmate-data.service";
import { Type } from "../../../../../util/Type";
import { Id } from "../../../../../util/Id";
import { GraphicalElementBase } from "../graphical-element-base";
import { ProcessStep } from "../../../../../model/ProcessStep";
import { DraggableElementBase } from "../draggable-element-base";
import { ProcessEnd } from '../../../../../model/ProcessEnd';

@Component({
    moduleId: module.id,
    selector: '[process-end-graphical-node]',
    templateUrl: 'process-end-graphical-node.component.svg',
    styleUrls: ['process-end-graphical-node.component.css']
})

export class ProcessEndGraphicalNode extends DraggableElementBase<ProcessEnd> {
    
    public nodeType: { className: string; } = ProcessEnd;

    public get dimensions(): {width: number, height: number} {
        return {
            width: Config.PROCESS_START_END_NODE_RADIUS * 2,
            height: Config.PROCESS_START_END_NODE_RADIUS * 2
        }
    }

    public get radius(): number {
        return Config.PROCESS_START_END_NODE_RADIUS;
    }
    
    @Input()
    node: ProcessStep;

    public get element(): ProcessStep {
        return this.node;
    }

    @Input()
    selected: boolean;

    @Input()
    valid: boolean;

    constructor(protected dataService: SpecmateDataService) {
        super();
    }
}
