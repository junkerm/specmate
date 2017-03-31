import { FormGroup } from '@angular/forms';
import { Input, Component } from '@angular/core';
import { FieldMetaItem } from "../../../model/meta/field-meta";

@Component({
    moduleId: module.id,
    selector: 'form-checkbox-input',
    templateUrl: 'form-checkbox-input.component.html'
})
export class FormCheckboxInput {
    @Input()
    meta: FieldMetaItem;

    @Input()
    form: FormGroup;

    private get control() {
        return this.form.controls[this.meta.name];
    }

    constructor() { }
}