import { Arrays } from "../util/Arrays";
import { Validators, FormGroup, FormBuilder } from "@angular/forms";
import { SpecmateDataService } from '../services/specmate-data.service';
import { FieldMetaItem } from "../model/meta/field-meta";

export abstract class AbstractForm {

    public errorMessage: string = 'This field is required.';

    protected inputForm: FormGroup;

    protected abstract formModel: any;

    protected abstract fieldMeta: FieldMetaItem[];

    constructor(private formBuilder: FormBuilder, protected dataService: SpecmateDataService) {
        this.createForm();
    }

    protected createForm(): void {
        if (!this.fieldMeta) {
            this.inputForm = this.formBuilder.group({});
            return;
        }
        var formBuilderObject: any = {};
        for (let i = 0; i < this.fieldMeta.length; i++) {
            let fieldMeta: FieldMetaItem = this.fieldMeta[i];
            let fieldName: string = fieldMeta.name;
            let formBuilderObjectValue: any[] = [''];
            if (this.fieldMeta[i].required) {
                formBuilderObjectValue.push(Validators.required);
            }
            formBuilderObject[fieldName] = formBuilderObjectValue;
        }
        this.inputForm = this.formBuilder.group(formBuilderObject);
    }

    protected updateFormModel(): void {
        if (!this.inputForm.valid) {
            return;
        }
        // We need this, since in some cases, the update event on th control is fired, even though the data did actually not change. We want to prevent unnecessary updates.
        let changed: boolean = false;
        for (let i = 0; i < this.fieldMeta.length; i++) {
            let fieldMeta: FieldMetaItem = this.fieldMeta[i];
            let fieldName: string = fieldMeta.name;
            let updateValue: string | boolean = this.inputForm.controls[fieldName].value;
            if (updateValue === undefined) {
                updateValue = '';
            }
            // We do not need to clone here (hopefully), because only simple values can be passed via forms.
            if(this.formModel[fieldName] !== updateValue) {
                this.formModel[fieldName] = updateValue;
                changed = true;
            }
        }
        if(changed) {
            this.dataService.updateElement(this.formModel, true);
        }
        else {
            console.log("SKIPPING UPDATE");
        }
    }

    protected updateForm(): void {
        if (!this.formModel) {
            return;
        }
        let updateObject: any = {};
        for (let i = 0; i < this.fieldMeta.length; i++) {
            let fieldMeta: FieldMetaItem = this.fieldMeta[i];
            let fieldName: string = fieldMeta.name;
            let value: any = this.formModel[fieldName] || '';
            if(AbstractForm.isBoolean(value)) {
                value = AbstractForm.convertToBoolean(value);
            }
            updateObject[fieldName] = value;
        }
        this.inputForm.setValue(updateObject);
    }

    public get isValid(): boolean {
        return this.inputForm.valid;
    }

    private static isBoolean(str: string): boolean {
        return AbstractForm.convertToBoolean(str) !== undefined;
    }

    private static convertToBoolean(str: string): boolean {
        if(str.toLowerCase() === 'true') {
            return true;
        } else if (str === '' || str.toLocaleLowerCase() === 'false') {
            return false;
        }
        return undefined;
    }
}

export class FieldType {
    public static TEXT: string = 'text';
    public static TEXT_LONG: string = 'longText';
    public static CHECKBOX: string = 'checkbox';
    public static SINGLE_SELECT: string = 'singleSelect';
}