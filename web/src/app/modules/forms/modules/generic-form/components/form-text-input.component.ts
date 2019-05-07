import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { FormElement } from '../base/form-element';

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
