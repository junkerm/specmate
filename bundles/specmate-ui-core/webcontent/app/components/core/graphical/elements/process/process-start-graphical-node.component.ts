import { Component, Input, ElementRef } from '@angular/core';
import { Config } from "../../../../../config/config";
import { SpecmateDataService } from "../../../../../services/data/specmate-data.service";
import { Type } from "../../../../../util/Type";
import { Id } from "../../../../../util/Id";
import { GraphicalElementBase } from "../graphical-element-base";
import { ProcessStep } from "../../../../../model/ProcessStep";
import { DraggableElementBase } from "../draggable-element-base";
import { ProcessStart } from '../../../../../model/ProcessStart';
import { SelectedElementService } from '../../../../../services/editor/selected-element.service';

@Component({
    moduleId: module.id,
    selector: '[process-start-graphical-node]',
    templateUrl: 'process-start-graphical-node.component.svg',
    styleUrls: ['process-start-graphical-node.component.css']
})

export class ProcessStartGraphicalNode extends DraggableElementBase<ProcessStart> {
    
    public nodeType: { className: string; } = ProcessStart;

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
    valid: boolean;

    constructor(protected dataService: SpecmateDataService, selectedElementService: SelectedElementService) {
        super(selectedElementService);
    }
}
