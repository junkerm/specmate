import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { FormElement } from '../base/form-element';

@Component({
    moduleId: module.id.toString(),
    selector: '[form-checkbox-input]',
    templateUrl: 'form-checkbox-input.component.html'
})
export class FormCheckboxInput extends FormElement {

    constructor(translate: TranslateService) {
        super(translate);
    }

    private get control() {
        return this.form.controls[this.meta.name];
    }

    private get value(): string {
        return this.control.value;
    }

    private set value(val: string) {
        this.control.setValue(val);
    }
}
