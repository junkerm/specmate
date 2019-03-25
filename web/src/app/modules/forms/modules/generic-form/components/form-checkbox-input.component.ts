import { Component } from '@angular/core';
import { FormElement } from '../base/form-element';
import { TranslateService } from '@ngx-translate/core';

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

    public get value(): string {
        return this.control.value;
    }

    public set value(val: string) {
        this.control.setValue(val);
    }
}
