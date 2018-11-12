import { Component, OnInit } from '@angular/core';
import { ValidationService } from '../../../../../forms/modules/validation/services/validation.service';
import { AdditionalInformationService } from '../../links-actions/services/additional-information.service';
import { SpecmateDataService } from '../../../../../data/modules/data-service/services/specmate-data.service';
import { ValidationResult } from '../../../../../../validation/validation-result';
import { IContainer } from '../../../../../../model/IContainer';


@Component({
    moduleId: module.id.toString(),
    selector: 'errors-warnings',
    templateUrl: 'errors-warnings.component.html',
    styleUrls: ['errors-warnings.component.css']
})
export class ErrorsWarings implements OnInit {
    public isCollapsed = false;
    public visible = true;
    constructor(private validationService: ValidationService,
                private dataService: SpecmateDataService,
                private additionalInformationService: AdditionalInformationService) { }

    ngOnInit() { }

    private contents: IContainer[];


    private get warnings() {
        if (!this.additionalInformationService.element) {
            return;
        }
        let map = this.validationService.findValidationResults(this.additionalInformationService.element, elem => !elem.isValid);
        /*let map: {[key: string]: ValidationResult[]} = {};
        this.dataService.readContents(this.additionalInformationService.element.url)
                        .then( contents => this.contents = contents);
        this.validationService.validate(this.additionalInformationService.element, this.contents)
                            .filter((e) => !e.isValid)
                            .forEach( (r) => {
            let key = r.elements.map( e => e.url).join(' ');
            if (!map[key]) {
                map[key] = [];
            }
            map[key].push(r);
        });*/

        let warnings = [];
        for (const key in map) {
            warnings.push(map[key]);
        }
        return warnings;
    }
}
