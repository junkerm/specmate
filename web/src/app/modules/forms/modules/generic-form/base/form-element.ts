import { Input } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { FieldMetaItem } from '../../../../../model/meta/field-meta';
import { TranslateService } from '@ngx-translate/core';

export abstract class FormElement {

    @Input()
    public meta: FieldMetaItem;

    @Input()
    public form: FormGroup;

    constructor(private translate: TranslateService) { }

    public get errorMessage(): string {
        return this.translate.instant('requiredField');
    }
}
