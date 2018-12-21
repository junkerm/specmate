import { Component, Input } from '@angular/core';
import { DraggableElementBase } from '../../elements/draggable-element-base';
import { ProcessDecision } from '../../../../../../../../model/ProcessDecision';
import { Config } from '../../../../../../../../config/config';
import { ProcessStep } from '../../../../../../../../model/ProcessStep';
import { SpecmateDataService } from '../../../../../../../data/modules/data-service/services/specmate-data.service';
import { SelectedElementService } from '../../../../../../side/modules/selected-element/services/selected-element.service';
import { ValidationService } from '../../../../../../../forms/modules/validation/services/validation.service';
import { MultiselectionService } from '../../../tool-pallette/services/multiselection.service';

@Component({
    moduleId: module.id.toString(),
    selector: '[process-decision-graphical-node]',
    templateUrl: 'process-decision-graphical-node.component.html',
    styleUrls: ['process-decision-graphical-node.component.css']
})

export class ProcessDecisionGraphicalNode extends DraggableElementBase<ProcessDecision> {

    public nodeType: { className: string; } = ProcessDecision;

    public get dimensions(): {width: number, height: number} {
        return {
            width: Config.PROCESS_DECISION_NODE_DIM * 2,
            height: Config.PROCESS_DECISION_NODE_DIM * 2
        };
    }

    @Input()
    public node: ProcessStep;

    public get element(): ProcessStep {
        return this.node;
    }

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

    constructor(
        protected dataService: SpecmateDataService,
        selectedElementService: SelectedElementService,
        validationService: ValidationService,
        multiselectionService: MultiselectionService) {
        super(selectedElementService, validationService, multiselectionService);
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
