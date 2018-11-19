import { Component, OnInit } from '@angular/core';
import { ValidationResult } from '../../../../../../validation/validation-result';
import { ValidationService } from '../../../../../forms/modules/validation/services/validation.service';
import { AdditionalInformationService } from '../../links-actions/services/additional-information.service';


@Component({
    moduleId: module.id.toString(),
    selector: 'errors-warnings',
    templateUrl: 'errors-warnings.component.html',
    styleUrls: ['errors-warnings.component.css']
})
export class ErrorsWarings implements OnInit {
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

    ngOnInit() { }

    private _tmpStringSet = new Set();
    private _tmpList: ValidationResult[][] = [];
    private get warnings() {
        if (!this.additionalInformationService.element) {
            return;
        }
        let map = this.validationService.findValidationResults(this.additionalInformationService.element, elem => !elem.isValid);

        let warnings: ValidationResult[][] = [];
        let strSet = new Set();
        let changed = false;
        for (const key in map) {
            let list = map[key];
            let arrStr = ErrorsWarings.getArrayURLString(list);
            if (!this._tmpStringSet.has(arrStr)) {
                changed = true;
            }
            strSet.add(arrStr);
            warnings.push(list);
        }
        if (warnings.length != this._tmpList.length) {
            changed = true;
        }
        if (changed) {
            Promise.resolve().then( () => {
                this._tmpStringSet = strSet;
                this._tmpList = warnings;
            });
        }
        return this._tmpList;
    }

    private static getArrayURLString(array: ValidationResult[]) {
        return array.map(res =>  {
            return res.elements.map(element  => element.url).sort().join(' ');
        }).sort(). join(',');
    }

}
