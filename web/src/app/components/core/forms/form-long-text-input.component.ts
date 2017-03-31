import { Input, Component } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { FieldMetaItem } from "../../../model/meta/field-meta";

@Component({
    moduleId: module.id,
    selector: 'form-long-text-input',
    templateUrl: 'form-long-text-input.component.html'
})
export class FormLongTextInput {
    constructor() { }

    @Input()
    meta: FieldMetaItem;

    @Input()
    form: FormGroup;
}