import { Component } from '@angular/core';
import { FormElement } from '../base/form-element';
import { TranslateService } from '@ngx-translate/core';

@Component({
    moduleId: module.id.toString(),
    selector: '[form-text-input]',
    templateUrl: 'form-text-input.component.html'
})
export class FormTextInput extends FormElement {
    constructor(translate: TranslateService) {
        super(translate);
    }
}
