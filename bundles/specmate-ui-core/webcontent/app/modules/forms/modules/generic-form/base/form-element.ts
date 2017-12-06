import { Input } from "@angular/core";
import { FormGroup } from "@angular/forms";
import { FieldMetaItem } from "../../../../../model/meta/field-meta";

export abstract class FormElement {

    @Input()
    public meta: FieldMetaItem;

    @Input()
    public form: FormGroup;

    public errorMessage = 'This field is required.';
}