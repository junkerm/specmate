import { Component, OnInit } from '@angular/core';
import { ValidationService } from '../../../../../forms/modules/validation/services/validation.service';
import { AdditionalInformationService } from '../../links-actions/services/additional-information.service';


@Component({
    moduleId: module.id.toString(),
    selector: 'errors-warnings',
    templateUrl: 'errors-warnings.component.html',
    styleUrls: ['errors-warnings.component.css']
})
export class ErrorsWarings implements OnInit {
    public isCollapsed = false;
    public visible = true;
    constructor(private validationService: ValidationService) { }

    ngOnInit() { }

    private get warnings() {
        // console.log('Warnings');
        // console.log(this.validationService.currentInvalidElements);
        let warnObjectList = this.validationService.currentInvalidElements.filter((elem, pos, arr) => {
            return arr.indexOf(elem) == pos;
        });
        return warnObjectList;
    }
}
