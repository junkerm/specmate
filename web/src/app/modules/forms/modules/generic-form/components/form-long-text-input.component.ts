import { Component } from '@angular/core';
import { FormElement } from '../base/form-element';

@Component({
    moduleId: module.id.toString(),
    selector: '[form-long-text-input]',
    templateUrl: 'form-long-text-input.component.html'
})
export class FormLongTextInput extends FormElement { }
