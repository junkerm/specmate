import { FormGroup } from '@angular/forms';
import { FieldMetaItem } from '../../../controls/AbstractForm';
import { Input, Component } from '@angular/core';

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