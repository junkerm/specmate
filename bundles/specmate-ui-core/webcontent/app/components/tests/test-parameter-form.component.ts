import { SpecmateDataService } from '../../services/specmate-data.service';
import { Validators, FormControl, FormGroup } from '@angular/forms';
import { TestParameter } from '../../model/TestParameter';
import { Input, OnInit, Component } from '@angular/core';

@Component({
    moduleId: module.id,
    selector: 'test-parameter-form',
    templateUrl: 'test-parameter-form.component.html',
    styleUrls: ['test-parameter-form.component.css']
})
export class TestParameterForm {

    /** The form group */
    formGroup: FormGroup;

    private _testParameter: TestParameter;

    /** The test parameter to display */
    @Input()
    set testParameter(testParameter: TestParameter) {
        this._testParameter = testParameter;
        this.buildFormGroup();
    }

    ngDoCheck(args: any) {
        this.updateForm();
    }

    private updateForm(): void {
        let formBuilderObject: any = {};
        formBuilderObject.parameter = this._testParameter.name;
        this.formGroup.setValue(formBuilderObject);
    }
    
    constructor(private dataService: SpecmateDataService) {
        this.formGroup = new FormGroup({});
    }

    private buildFormGroup(): void {
        this.formGroup = new FormGroup({
            'parameter': new FormControl(this._testParameter.name, Validators.required)
        });
        this.formGroup.valueChanges.subscribe(() => {
                this._testParameter.name = this.formGroup.controls["parameter"].value;
                this.dataService.updateElement(this._testParameter, true);
            }
        );
    }
    
    deleteParameter(): void {
        this.dataService.deleteElement(this._testParameter.url, true);
    }
}