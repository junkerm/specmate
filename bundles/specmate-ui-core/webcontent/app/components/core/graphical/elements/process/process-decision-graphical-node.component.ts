import { Component, Input, ElementRef } from '@angular/core';
import { Config } from "../../../../../config/config";
import { SpecmateDataService } from "../../../../../services/data/specmate-data.service";
import { Type } from "../../../../../util/Type";
import { Id } from "../../../../../util/Id";
import { GraphicalElementBase } from "../graphical-element-base";
import { ProcessStep } from "../../../../../model/ProcessStep";
import { DraggableElementBase } from "../draggable-element-base";
import { ProcessDecision } from "../../../../../model/ProcessDecision";
import { SelectedElementService } from '../../../../../services/editor/selected-element.service';

@Component({
    moduleId: module.id,
    selector: '[process-decision-graphical-node]',
    templateUrl: 'process-decision-graphical-node.component.svg',
    styleUrls: ['process-decision-graphical-node.component.css']
})

export class ProcessDecisionGraphicalNode extends DraggableElementBase<ProcessDecision> {
    
    public nodeType: { className: string; } = ProcessDecision;

    public get dimensions(): {width: number, height: number} {
        return {
            width: Config.PROCESS_DECISION_NODE_DIM * 2,
            height: Config.PROCESS_DECISION_NODE_DIM * 2
        }
    }

    @Input()
    node: ProcessStep;

    public get element(): ProcessStep {
        return this.node;
    }

    @Input()
    valid: boolean;

    public get top(): {x: number, y: number} {
        return {
            x: this.topLeft.x + this.dimensions.width / 2,
            y: this.topLeft.y
        };
    }

    public get right(): {x: number, y: number} {
        return {
            x: this.topLeft.x + this.dimensions.width,
            y: this.topLeft.y + this.dimensions.width / 2
        };
    }

    public get bottom(): {x: number, y: number} {
        return {
            x: this.topLeft.x + this.dimensions.width / 2,
            y: this.topLeft.y + this.dimensions.width
        };
    }

    public get left(): {x: number, y: number} {
        return {
            x: this.topLeft.x,
            y: this.topLeft.y + this.dimensions.width / 2
        };
    }

    private get title(): string {
        return this.node.name;
    }

    constructor(protected dataService: SpecmateDataService, selectedElementService: SelectedElementService) {
        super(selectedElementService);
    }

    private get coords(): number[] {
        return [
            this.top.x,
            this.top.y,
            this.right.x,
            this.right.y,
            this.bottom.x,
            this.bottom.y,
            this.left.x,
            this.left.y
        ];
    }

    public get svgPathString(): string {
        return 'M 0 0 M ' + this.coords.join(' ') + ' Z';
    }
}
