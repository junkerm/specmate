import { Input, Component } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { FieldMetaItem } from "../../../model/meta/field-meta";

@Component({
    moduleId: module.id,
    selector: 'form-single-selection-input',
    templateUrl: 'form-single-selection-input.component.html'
})
export class FormSingleSelectionInput {
    constructor() { }

    @Input()
    meta: FieldMetaItem;

    @Input()
    form: FormGroup;

    private get options(): string[] {
        return JSON.parse(this.meta.values);
    }
}