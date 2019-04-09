import { Component, Input } from '@angular/core';
import { Config } from '../../../../../../../../config/config';
import { ProcessEnd } from '../../../../../../../../model/ProcessEnd';
import { ProcessStep } from '../../../../../../../../model/ProcessStep';
import { SpecmateDataService } from '../../../../../../../data/modules/data-service/services/specmate-data.service';
import { ValidationService } from '../../../../../../../forms/modules/validation/services/validation.service';
import { SelectedElementService } from '../../../../../../side/modules/selected-element/services/selected-element.service';
import { MultiselectionService } from '../../../tool-pallette/services/multiselection.service';
import { DraggableElementBase } from '../../elements/draggable-element-base';

@Component({
    moduleId: module.id.toString(),
    selector: '[process-end-graphical-node]',
    templateUrl: 'process-end-graphical-node.component.html',
    styleUrls: ['process-end-graphical-node.component.css']
})

export class ProcessEndGraphicalNode extends DraggableElementBase<ProcessEnd> {

    public nodeType: { className: string; } = ProcessEnd;

    public get dimensions(): {width: number, height: number} {
        return {
            width: Config.PROCESS_START_END_NODE_RADIUS * 2,
            height: Config.PROCESS_START_END_NODE_RADIUS * 2
        };
    }

    public get radius(): number {
        return Config.PROCESS_START_END_NODE_RADIUS;
    }

    @Input()
    node: ProcessStep;

    public get element(): ProcessStep {
        return this.node;
    }

    constructor(
        protected dataService: SpecmateDataService,
        selectedElementService: SelectedElementService,
        validationService: ValidationService,
        multiselectionService: MultiselectionService) {
        super(selectedElementService, validationService, multiselectionService);
    }
}
