import { Component, Input } from '@angular/core';
import { DraggableElementBase } from '../../elements/draggable-element-base';
import { ProcessStep } from '../../../../../../../../model/ProcessStep';
import { Config } from '../../../../../../../../config/config';
import { SpecmateDataService } from '../../../../../../../data/modules/data-service/services/specmate-data.service';
import { SelectedElementService } from '../../../../../../side/modules/selected-element/services/selected-element.service';
import { ValidationService } from '../../../../../../../forms/modules/validation/services/validation.service';

@Component({
    moduleId: module.id,
    selector: '[process-step-graphical-node]',
    templateUrl: 'process-step-graphical-node.component.svg',
    styleUrls: ['process-step-graphical-node.component.css']
})

export class ProcessStepGraphicalNode extends DraggableElementBase<ProcessStep> {
    public nodeType: { className: string; } = ProcessStep;

    public get dimensions(): {width: number, height: number} {
        return {
            width: Config.CEG_NODE_WIDTH,
            height: Config.CEG_NODE_HEIGHT
        };
    }
    
    @Input()
    node: ProcessStep;

    public get element(): ProcessStep {
        return this.node;
    }

    private get title(): string {
        return this.node.name;
    }

    constructor(protected dataService: SpecmateDataService, selectedElementService: SelectedElementService, validationService: ValidationService) {
        super(selectedElementService, validationService);
    }
}
