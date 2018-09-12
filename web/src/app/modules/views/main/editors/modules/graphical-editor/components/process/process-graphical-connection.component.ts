import { Component } from '@angular/core';
import { ProcessConnection } from '../../../../../../../../model/ProcessConnection';
import { ValidationService } from '../../../../../../../forms/modules/validation/services/validation.service';
import { SelectedElementService } from '../../../../../../side/modules/selected-element/services/selected-element.service';
import { MultiselectionService } from '../../../tool-pallette/services/multiselection.service';
import { GraphicalConnectionBase } from '../../elements/graphical-connection-base';

@Component({
    moduleId: module.id.toString(),
    selector: '[process-graphical-connection]',
    templateUrl: 'process-graphical-connection.component.svg',
    styleUrls: ['process-graphical-connection.component.css']
})
export class ProcessGraphicalConnection extends GraphicalConnectionBase<ProcessConnection> {
    public nodeType: { className: string; } = ProcessConnection;

    constructor(selectedElementService: SelectedElementService,
                validationService: ValidationService,
                multiselectionService: MultiselectionService) {
        super(selectedElementService, validationService, multiselectionService);
    }

    public get showCondition(): boolean {
        return this.connection && this.connection.condition && this.connection.condition.length > 0;
    }

    public get conditionText(): string {
        if (this.showCondition) {
            return '[' + this.connection.condition + ']';
        }
        return '';
    }
}
