import { Input } from '@angular/core';
import { FormGroup, ValidationErrors } from '@angular/forms';
import { FieldMetaItem } from '../../../../../model/meta/field-meta';
import { TranslateService } from '@ngx-translate/core';
import { formArrayNameProvider } from '@angular/forms/src/directives/reactive_directives/form_group_name';

export abstract class FormElement {

    @Input()
    public meta: FieldMetaItem;

    @Input()
    public form: FormGroup;

    constructor(private translate: TranslateService) { }

    public get errorMessage(): string {
        const control = this.form.controls[this.meta.name];
        if (control != undefined && control.errors != undefined) {
            let error = control.errors;
            if (error.required != undefined) {
                return this.translate.instant('requiredField');
            }
            if (error.pattern != undefined) {
                return this.translate.instant('invalidCharacter');
            }
        }
        return this.translate.instant('fieldInvalid');
    }
}
