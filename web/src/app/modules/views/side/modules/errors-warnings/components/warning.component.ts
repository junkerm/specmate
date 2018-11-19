import { Component, Input, OnInit } from '@angular/core';
import { IContainer } from '../../../../../../model/IContainer';
import { ValidationResult } from '../../../../../../validation/validation-result';
import { SpecmateDataService } from '../../../../../data/modules/data-service/services/specmate-data.service';
import { ValidationService } from '../../../../../forms/modules/validation/services/validation.service';
import { SelectedElementService } from '../../selected-element/services/selected-element.service';
import { AdditionalInformationService } from '../../links-actions/services/additional-information.service';

@Component({
    selector: 'warning',
    templateUrl: 'warning.component.html',
    styleUrls: ['warning.component.css']
})

export class Warning implements OnInit {
    private valResults: ValidationResult[] = [];
    private affectedElements: IContainer[] = [];

    @Input()
    public set elements(elems: ValidationResult[]) {
        if (!elems) {
            return;
        }
        this.valResults = elems;
        if (elems.length > 0) {
            this.affectedElements = elems[0].elements;
        } else {
            this.affectedElements = [];
        }
    }

    constructor(
        private selectedElementService: SelectedElementService,
        private additionalInformation: AdditionalInformationService) { }

    ngOnInit() { }

    private get name() {
        if (this.affectedElements.length == 0) {
            return 'Model Error';
        }

        return 'Element Error:\n' + this.affectedElements.map( e => {
            if (!e.name || e.name.length == 0) {
                return 'Unnamed Element';
            }
            return e.name;
        }).join(',\n');
    }

    selectElement() {
        if (this.affectedElements.length > 0) {
            this.selectedElementService.selectAll(this.affectedElements);
        } else {
            if (this.additionalInformation.model) {
                this.selectedElementService.select(this.additionalInformation.model);
            } else if (this.additionalInformation.element) {
                this.selectedElementService.select(this.additionalInformation.element);
            } else {
                this.selectedElementService.deselect();
            }
        }
    }
}
