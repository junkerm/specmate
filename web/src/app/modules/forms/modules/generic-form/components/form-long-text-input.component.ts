import { Component } from '@angular/core';
import { FormElement } from '../base/form-element';
import { TranslateService } from '@ngx-translate/core';

@Component({
    moduleId: module.id.toString(),
    selector: '[form-long-text-input]',
    templateUrl: 'form-long-text-input.component.html'
})
export class FormLongTextInput extends FormElement {
    constructor(translate: TranslateService) {
        super(translate);
    }
}
