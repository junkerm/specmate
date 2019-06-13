import { Input } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { FieldMetaItem } from '../../../../../model/meta/field-meta';

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
