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
        this.valResults = elems;
        this.affectedElements = elems[0].elements;
    }

    constructor(private validationService: ValidationService,
                private selectedElementService: SelectedElementService,
                private dataService: SpecmateDataService,
                private additionalInformationService: AdditionalInformationService) { }

    ngOnInit() { }

    private get name() {
        return 'Elements: ' + this.affectedElements.map( e => e.name).join(', ');
    }

    private selectElement() {
        if (this.affectedElements) {
            this.selectedElementService.selectAll(this.affectedElements);
        }
    }
}
