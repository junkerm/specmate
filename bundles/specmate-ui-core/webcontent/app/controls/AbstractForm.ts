import { Arrays } from "../util/Arrays";
import { Validators, FormGroup, FormBuilder } from "@angular/forms";

export abstract class AbstractForm {

    protected inputForm: FormGroup;

    protected abstract formFields: string[];
    protected abstract requiredFields: string[];
    protected abstract formModel: any;

    constructor(private formBuilder: FormBuilder) {
        this.createForm();
    }

    private createForm(): void {
        var formBuilderObject: any = {};
        for (let i = 0; i < this.formFields.length; i++) {
            let fieldName: string = this.formFields[i];
            let formBuilderObjectValue: any[] = [''];
            if (Arrays.contains(this.requiredFields, fieldName)) {
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
        for (let i = 0; i < this.formFields.length; i++) {
            let fieldName: string = this.formFields[i];
            let updateValue: string = this.inputForm.controls[fieldName].value;
            if (!updateValue) {
                updateValue = '';
            }
            this.formModel[fieldName] = updateValue;
        }
    }

    protected updateForm(): void {
        let updateObject: any = {};
        for (let i = 0; i < this.formFields.length; i++) {
            let fieldName: string = this.formFields[i];
            updateObject[fieldName] = this.formModel[fieldName];
        }
        this.inputForm.setValue(updateObject);
    }

    public get isValid(): boolean {
        return this.inputForm.valid;
    }
}