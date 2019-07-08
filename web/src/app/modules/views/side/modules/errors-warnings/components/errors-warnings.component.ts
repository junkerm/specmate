import { Component } from '@angular/core';
import { IContainer } from '../../../../../../model/IContainer';
import { Arrays } from '../../../../../../util/arrays';
import { ValidationResult } from '../../../../../../validation/validation-result';
import { ValidationService } from '../../../../../forms/modules/validation/services/validation.service';
import { AdditionalInformationService } from '../../links-actions/services/additional-information.service';
import { TranslateService } from '@ngx-translate/core';

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

    public get isCollapsed(): boolean {
        if (!this.validationResults) {
            return true;
        }
        return this._isCollapsed;
    }

    public get model(): IContainer {
        return this.additionalInformationService.element;
    }

    public visible = true;
    constructor(private validationService: ValidationService,
        private additionalInformationService: AdditionalInformationService,
        private translate: TranslateService) { }

    public get validationResults(): { name: string, message: string }[] {
        const validationResults = this.validationService
            .getValidationResults(this.additionalInformationService.element);
        const relevantValidationResults = validationResults.filter(result => !result.isValid);
        return relevantValidationResults.map(result => {
            return {
                name: result.elements[0].name,
                message: result.message.getMessageTranslated(this.translate)
            };
        });
    }

    // Errors and Warnings shall not be shown in folder view
    public get showErrors(): boolean {
        if (this.model !== undefined
            && this.model.className === 'Folder') {
            return false;
        }
        return true;
    }

}
