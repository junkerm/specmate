import { FormControl, FormGroup, Validators } from '@angular/forms';
import { IContainer } from '../../../../../model/IContainer';
import { Id } from '../../../../../util/id';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';

export abstract class SimpleInputFormBase {

    private _modelElement: IContainer;
    protected fields: string[];
    public formGroup: FormGroup;
    protected dataService: SpecmateDataService;

    protected get modelElement(): IContainer {
        return this._modelElement;
    }

    protected set modelElement(modelElement: IContainer) {
        this._modelElement = modelElement;
        if (this._modelElement) {
            this.buildFormGroup();
        }
    }

    constructor() {
        this.formGroup = new FormGroup({});
    }

    ngDoCheck(args: any) {
        this.updateFormGroupIfChanged(this.modelElement, this.fields);
    }

    private updateFormGroupIfChanged(modelElement: IContainer, fields: string[]): void {
        if (!modelElement) {
            return;
        }
        let changed = false;
        fields.forEach((field: string) => {
            let currentFormValue: string = this.formGroup.controls[field].value;
            let currentModelValue: string = modelElement[field];
            if (currentFormValue !== currentModelValue) {
                changed = true;
            }
        });

        if (changed) {
            let formBuilderObject: any = {};
            for (let field of fields) {
                formBuilderObject[field] = this.modelElement[field] || '';
            }
            this.formGroup.setValue(formBuilderObject);
        }
    }

    private buildFormGroup(): void {
        this.formGroup = this.buildFormGroupObject(this.modelElement, this.fields);
        this.formGroup.valueChanges.subscribe(() => this.updateModelPropertiesIfChanged(this.modelElement, this.fields));
    }

    private buildFormGroupObject(modelElement: IContainer, fields: string[]): FormGroup {
        let formGroupObject: any = {};
        for (let i = 0; i < fields.length; i++) {
            let currentField: string = fields[i];
            formGroupObject[currentField] = new FormControl(modelElement[currentField], Validators.required);
        }
        return new FormGroup(formGroupObject);
    }

    private updateModelPropertiesIfChanged(modelElement: IContainer, fields: string[]): void {
        let changed = false;
        for (let i = 0; i < fields.length; i++) {
            let currentChanged: boolean = this.updateModelPropertyIfChanged(modelElement, fields[i]);
            if (currentChanged) {
                changed = true;
            }
        }
        if (changed) {
            this.dataService.updateElement(modelElement, true, Id.uuid);
        }
    }

    private updateModelPropertyIfChanged(modelElement: IContainer, field: string): boolean {
        let formValue: string = this.formGroup.controls[field].value;
        let modelValue: string = modelElement[field];
        if (modelValue !== formValue) {
            modelElement[field] = formValue;
            return true;
        }
        return false;
    }

    public get isInitialized(): boolean {
        return !this.fields.some((field: string) => this.formGroup.controls[field] === undefined);
    }
}
