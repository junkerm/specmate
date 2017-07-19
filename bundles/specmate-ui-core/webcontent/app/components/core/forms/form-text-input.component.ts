import { Component, Input } from '@angular/core';
import { FormGroup } from "@angular/forms";
import { FieldMetaItem } from "../../../model/meta/field-meta";
import { FormElement } from "./form-element";

@Component({
    moduleId: module.id,
    selector: '[form-text-input]',
    templateUrl: 'form-text-input.component.html'
})
export class FormTextInput extends FormElement { }