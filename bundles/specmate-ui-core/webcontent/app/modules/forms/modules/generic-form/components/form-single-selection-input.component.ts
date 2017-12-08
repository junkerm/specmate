import { Component } from '@angular/core';
import { FormElement } from '../base/form-element';

@Component({
    moduleId: module.id.toString(),
    selector: '[form-single-selection-input]',
    templateUrl: 'form-single-selection-input.component.html'
})
export class FormSingleSelectionInput extends FormElement {
    private get options(): string[] {
        return JSON.parse(this.meta.values);
    }
}
