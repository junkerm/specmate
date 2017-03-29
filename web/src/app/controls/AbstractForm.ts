import { Arrays } from "../util/Arrays";
import { Validators, FormGroup, FormBuilder } from "@angular/forms";
import { SpecmateDataService } from '../services/specmate-data.service';

export type FieldMetaItem = {
    name: string,
    shortDesc: string,
    longDesc: string,
    type: FieldType,
    required?: boolean
}


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
        for (let i = 0; i < this.fieldMeta.length; i++) {
            let fieldMeta: FieldMetaItem = this.fieldMeta[i];
            let fieldName: string = fieldMeta.name;
            let updateValue: string = this.inputForm.controls[fieldName].value;
            if (!updateValue) {
                updateValue = '';
            }
            // We do not need to clone here (hopefully), because only simple values can be passed via forms.
            this.formModel[fieldName] = updateValue;
        }
        this.dataService.updateElement(this.formModel, true);
    }

    protected updateForm(): void {
        if (!this.formModel) {
            return;
        }
        let updateObject: any = {};
        for (let i = 0; i < this.fieldMeta.length; i++) {
            let fieldMeta: FieldMetaItem = this.fieldMeta[i];
            let fieldName: string = fieldMeta.name;
            updateObject[fieldName] = this.formModel[fieldName] || '';
        }
        this.inputForm.setValue(updateObject);
    }

    public get isValid(): boolean {
        return this.inputForm.valid;
    }
}

export class FieldType {
    public static TEXT: string = 'TEXT';
    public static TEXT_LONG: string = 'TEXT_LONG';
    public static CHECKBOX: string = 'CHECKBOX';
}