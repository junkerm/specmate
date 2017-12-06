import { Component } from '@angular/core';
import { FormElement } from '../base/form-element';

@Component({
    moduleId: module.id,
    selector: '[form-text-input]',
    templateUrl: 'form-text-input.component.html'
})
export class FormTextInput extends FormElement { }