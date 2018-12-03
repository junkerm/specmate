import { Component } from '@angular/core';
import { ValidationResult } from '../../../../../../validation/validation-result';
import { ValidationService } from '../../../../../forms/modules/validation/services/validation.service';
import { AdditionalInformationService } from '../../links-actions/services/additional-information.service';


@Component({
    moduleId: module.id.toString(),
    selector: 'errors-warnings',
    templateUrl: 'errors-warnings.component.html',
    styleUrls: ['errors-warnings.component.css']
})
export class ErrorsWarings {
    private _isCollapsed = false;
    public set isCollapsed(collapsed: boolean) {
        this._isCollapsed = collapsed;
    }

    public get isCollapsed() {
        if (!this.warnings) {
            return true;
        }
        return this._isCollapsed;
    }


    public visible = true;
    constructor(private validationService: ValidationService,
                private additionalInformationService: AdditionalInformationService) { }


    private _currentWarningStringSet = new Set();
    private _currentWarnings: ValidationResult[][] = [];
    private get warnings() {
        if (!this.additionalInformationService.element) {
            return;
        }
        let invalidResults = this.validationService.findValidationResults(this.additionalInformationService.element, elem => !elem.isValid);
        let newWarnings: ValidationResult[][] = [];
        let newWarningStringSet = new Set();
        let changed = false;
        for (const key in invalidResults) {
            let list = invalidResults[key];
            let arrStr = ErrorsWarings.getArrayURLString(list);
            if (!this._currentWarningStringSet.has(arrStr)) {
                changed = true;
            }
            newWarningStringSet.add(arrStr);
            newWarnings.push(list);
        }
        if (newWarnings.length != this._currentWarnings.length) {
            changed = true;
        }
        if (changed) {
            Promise.resolve().then( () => {
                this._currentWarningStringSet = newWarningStringSet;
                this._currentWarnings = newWarnings;
            });
        }
        return this._currentWarnings;
    }

    private static getArrayURLString(array: ValidationResult[]) {
        return array.map(res =>  {
            return res.elements.map(element  => element.url).sort().join(' ');
        }).sort(). join(',');
    }

}
