import { FormGroup } from '@angular/forms';
import { Input, Component } from '@angular/core';
import { FieldMetaItem } from "../../../model/meta/field-meta";
import { FormElement } from "./form-element";

@Component({
    moduleId: module.id,
    selector: 'form-checkbox-input',
    templateUrl: 'form-checkbox-input.component.html'
})
export class FormCheckboxInput extends FormElement {
    private get control() {
        return this.form.controls[this.meta.name];
    }

    private get value(): string {
        return this.control.value;
    }

    private set value(val: string) {
        this.control.value = val;
    }
}