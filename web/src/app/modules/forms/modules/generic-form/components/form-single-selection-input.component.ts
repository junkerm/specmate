import { Component } from '@angular/core';
import { FormElement } from '../base/form-element';
import { TranslateService } from '@ngx-translate/core';

@Component({
    moduleId: module.id.toString(),
    selector: '[form-single-selection-input]',
    templateUrl: 'form-single-selection-input.component.html'
})
export class FormSingleSelectionInput extends FormElement {

    constructor(translate: TranslateService) {
        super(translate);
    }

    public get options(): string[] {
        return JSON.parse(this.meta.values);
    }
}
