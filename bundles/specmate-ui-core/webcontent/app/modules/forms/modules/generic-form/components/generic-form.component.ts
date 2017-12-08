import { Component, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Type } from '../../../../../util/type';
import { FieldMetaItem, MetaInfo } from '../../../../../model/meta/field-meta';
import { Arrays } from '../../../../../util/arrays';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { Converters } from '../conversion/converters';
import { Id } from '../../../../../util/id';

@Component({
    moduleId: module.id.toString(),
    selector: 'generic-form',
    templateUrl: 'generic-form.component.html'
})
export class GenericForm {

    public errorMessage = 'This field is invalid.';

    private formGroup: FormGroup;

    private ready = false;

    private _element: any;

    @Input()
    public hiddenFields: string[];

    private _meta: FieldMetaItem[];

    private static isBooleanText(str: string): boolean {
        return GenericForm.convertToBoolean(str) !== undefined;
    }

    private static convertToBoolean(str: string): boolean {
        if (typeof(str) === 'boolean') {
            return str;
        }
        if (str.toLowerCase && str.toLowerCase() === 'true') {
            return true;
        } else if (str === '' || (str.toLowerCase && str.toLocaleLowerCase() === 'false')) {
            return false;
        }
        return undefined;
    }

    private get element(): any {
        return this._element;
    }

    @Input()
    private set element(element: any) {
        if (!element) {
            return;
        }
        if (!this._element || !Type.is(this._element, element)) {
            this._element = element;
            this.ready = false;
            this.createForm();
        } else {
            this._element = element;
            this.updateForm();
        }
    }

    public get meta(): FieldMetaItem[] {
        if (!this._meta) {
            return [];
        }
        return this._meta.filter((metaItem: FieldMetaItem) => !Arrays.contains(this.hiddenFields, metaItem.name));
    }

    constructor(private formBuilder: FormBuilder, protected dataService: SpecmateDataService) {
        this.initEmpty();
    }

    ngDoCheck(args: any) {
        this.updateForm();
    }

    private orderFieldMeta(): void {
        this._meta.sort((item1: FieldMetaItem, item2: FieldMetaItem) => Number.parseInt(item1.position) - Number.parseInt(item2.position));
    }

    private initEmpty(): void {
        this.formGroup = this.formBuilder.group({});
    }

    private createForm(): void {
        if (!this._element) {
            return;
        }
        this._meta = MetaInfo[this.element.className];
        if (!this._meta) {
            this.initEmpty();
            return;
        }
        this.orderFieldMeta();
        let formBuilderObject: any = {};
        for (let i = 0; i < this._meta.length; i++) {
            let fieldMeta: FieldMetaItem = this._meta[i];
            let fieldName: string = fieldMeta.name;
            let formBuilderObjectValue: any[] = [''];
            if (this._meta[i].required) {
                formBuilderObjectValue.push(Validators.required);
            }
            formBuilderObject[fieldName] = formBuilderObjectValue;
        }
        this.formGroup = this.formBuilder.group(formBuilderObject);

        this.formGroup.valueChanges.subscribe(() => {
            this.updateFormModel();
            return '';
        });
        this.ready = true;
        this.updateForm();
    }

    private updateForm(): void {
        if (!this.ready) {
            return;
        }
        let changed = false;
        let formBuilderObject: any = {};
        for (let i = 0; i < this._meta.length; i++) {
            let fieldMeta: FieldMetaItem = this._meta[i];
            let fieldName: string = fieldMeta.name;
            let fieldType: string = fieldMeta.type;
            let updateValue: any = this.element[fieldName] || '';
            let converter = Converters[fieldMeta.type];
            if (converter) {
                updateValue = converter.convertFromModelToControl(updateValue);
            }
            formBuilderObject[fieldName] = updateValue;
            if (this.formGroup.controls[fieldName].value !== updateValue) {
                changed = true;
            }
        }
        if (changed) {
            this.formGroup.setValue(formBuilderObject);
        }
    }

    private updateFormModel(): void {
        // We need this, since in some cases, the update event on the control is fired,
        // even though the data did actually not change. We want to prevent unnecessary updates.
        let changed = false;
        for (let i = 0; i < this._meta.length; i++) {
            let fieldMeta: FieldMetaItem = this._meta[i];
            let fieldName: string = fieldMeta.name;
            let updateValue: string = this.formGroup.controls[fieldName].value;
            if (updateValue === undefined) {
                updateValue = '';
            }
            let converter = Converters[fieldMeta.type];
            if (converter) {
                updateValue = converter.convertFromControlToModel(updateValue);
            }
            // We do not need to clone here (hopefully), because only simple values can be passed via forms.
            if (this.element[fieldName] + '' !== updateValue + '') {
                this.element[fieldName] = updateValue;
                changed = true;
            }
        }
        if (changed && this.isValid) {
            this.dataService.updateElement(this.element, true, Id.uuid);
        }
    }

    public get isValid(): boolean {
        return this.formGroup.valid;
    }
}

export class FieldType {
    public static TEXT = 'text';
    public static TEXT_LONG = 'longText';
    public static CHECKBOX = 'checkbox';
    public static SINGLE_SELECT = 'singleSelect';
}
