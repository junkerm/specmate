import { Component } from '@angular/core';
import { GraphicalConnectionBase } from '../../elements/graphical-connection-base';
import { CEGConnection } from '../../../../../../../../model/CEGConnection';
import { SelectedElementService } from '../../../../../../side/modules/selected-element/services/selected-element.service';
import { ValidationService } from '../../../../../../../forms/modules/validation/services/validation.service';

@Component({
    moduleId: module.id.toString(),
    selector: '[ceg-graphical-connection]',
    templateUrl: 'ceg-graphical-connection.component.svg',
    styleUrls: ['ceg-graphical-connection.component.css']
})
export class CEGGraphicalConnection extends GraphicalConnectionBase<CEGConnection> {
    public nodeType: { className: string; } = CEGConnection;

    constructor(selectedElementService: SelectedElementService, validationService: ValidationService) {
        super(selectedElementService, validationService);
    }

    private get isNegated(): boolean {
        return (this.connection.negate + '').toLowerCase() === 'true';
    }
}
