import { Component, Input, OnInit } from '@angular/core';
import { IContainer } from '../../../../../../model/IContainer';
import { ValidationResult } from '../../../../../../validation/validation-result';
import { SpecmateDataService } from '../../../../../data/modules/data-service/services/specmate-data.service';
import { ValidationService } from '../../../../../forms/modules/validation/services/validation.service';
import { SelectedElementService } from '../../selected-element/services/selected-element.service';
import { Url } from '../../../../../../util/url';
import { AdditionalInformationService } from '../../links-actions/services/additional-information.service';

@Component({
    selector: 'warning',
    templateUrl: 'warning.component.html',
    styleUrls: ['warning.component.css']
})

export class Warning implements OnInit {
    private warnElement: IContainer;
    private valResults: ValidationResult[] = [];

    @Input()
    public set element(elem: IContainer) {
        this.warnElement = elem;
        this.dataService.readContents(this.additionalInformationService.element.url).then((contents: IContainer[]) => {
            this.valResults = this.validationService.validate(this.additionalInformationService.element, contents);
            console.log(this.valResults);
        });
    }

    constructor(private validationService: ValidationService,
                private selectedElementService: SelectedElementService,
                private dataService: SpecmateDataService,
                private additionalInformationService: AdditionalInformationService) { }

    ngOnInit() { }

    private selectElement() {
        if (this.warnElement) {
            this.selectedElementService.select(this.warnElement);
        }
    }
}
