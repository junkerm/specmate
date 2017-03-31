import { Component, Input } from '@angular/core';
import { FormGroup } from "@angular/forms";
import { FieldMetaItem } from "../../../model/meta/field-meta";

@Component({
    moduleId: module.id,
    selector: 'form-text-input',
    templateUrl: 'form-text-input.component.html'
})
export class FormTextInput {
    @Input()
    meta: FieldMetaItem;

    @Input()
    form: FormGroup;
}