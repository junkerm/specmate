import { Component, Input } from '@angular/core';
import { Config } from '../../../../../../../../config/config';
import { ProcessStep } from '../../../../../../../../model/ProcessStep';
import { SpecmateDataService } from '../../../../../../../data/modules/data-service/services/specmate-data.service';
import { ValidationService } from '../../../../../../../forms/modules/validation/services/validation.service';
import { SelectedElementService } from '../../../../../../side/modules/selected-element/services/selected-element.service';
import { MultiselectionService } from '../../../tool-pallette/services/multiselection.service';
import { DraggableElementBase } from '../../elements/draggable-element-base';

@Component({
    moduleId: module.id.toString(),
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

    constructor(
        protected dataService: SpecmateDataService,
        selectedElementService: SelectedElementService,
        validationService: ValidationService,
        multiselectionService: MultiselectionService) {
        super(selectedElementService, validationService, multiselectionService);
    }
}
