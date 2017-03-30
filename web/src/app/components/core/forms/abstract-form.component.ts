import { Component, Input } from "@angular/core";
import { Validators, FormGroup, FormBuilder } from "@angular/forms";
import { FieldMetaItem } from "../../../model/meta/field-meta";
import { SpecmateDataService } from "../../../services/specmate-data.service";

@Component({
    moduleId: module.id,
    selector: 'input-form',
    templateUrl: 'abstract-form.component.html'
})
export class AbstractForm {

    public errorMessage: string = 'This field is required.';

    public inputForm: FormGroup;

    @Input()
    private formModel: any;

    @Input()
    protected fieldMeta: FieldMetaItem[];

    constructor(private formBuilder: FormBuilder, protected dataService: SpecmateDataService) {
        this.createForm();
    }

    private orderFieldMeta(): void {
        this.fieldMeta.sort((item1: FieldMetaItem, item2: FieldMetaItem) => Number.parseInt(item1.position) - Number.parseInt(item2.position));
    }

    public createForm(): void {
        if (!this.fieldMeta) {
            this.inputForm = this.formBuilder.group({});
            return;
        }
        this.orderFieldMeta();
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

    public updateFormModel(): void {
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

    public updateForm(): void {
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