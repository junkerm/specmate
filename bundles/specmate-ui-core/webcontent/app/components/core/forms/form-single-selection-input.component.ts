import { Input, Component } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { FieldMetaItem } from "../../../model/meta/field-meta";
import { FormElement } from "./form-element";

@Component({
    moduleId: module.id,
    selector: 'form-single-selection-input',
    templateUrl: 'form-single-selection-input.component.html'
})
export class FormSingleSelectionInput extends FormElement {
    private get options(): string[] {
        return JSON.parse(this.meta.values);
    }
}