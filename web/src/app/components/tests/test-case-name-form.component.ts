import {TestCase} from '../../model/TestCase';
import {ParameterAssignment} from '../../model/ParameterAssignment';
import { SpecmateDataService } from '../../services/specmate-data.service';
import { Validators, FormControl, FormGroup } from '@angular/forms';
import { Input, OnInit, Component } from '@angular/core';

@Component({
    moduleId: module.id,
    selector: 'test-case-name-form',
    templateUrl: 'test-case-name-form.component.html',
    styleUrls: ['test-case-name-form.component.css']
})
export class TestCaseNameForm {

    /** The form group */
    formGroup: FormGroup;

    private _testCase: TestCase;

    /** The parameter assignment */
    @Input()
    set testCase(testCase: TestCase) {
        this._testCase = testCase;
        this.buildFormGroup();
    }

    constructor(private dataService: SpecmateDataService) {
        this.formGroup = new FormGroup({});
    }

    ngDoCheck(args: any) {
        this.updateForm();
    }

    private updateForm(): void {
        let formBuilderObject: any = {};
        formBuilderObject.testCase = this._testCase.name;
        this.formGroup.setValue(formBuilderObject);
    }
    
    private buildFormGroup(): void {
        this.formGroup = new FormGroup({
            'testCase': new FormControl(this._testCase.name, Validators.required)
        });
        this.formGroup.valueChanges.subscribe(() => {
                this._testCase.name = this.formGroup.controls['testCase'].value;
                this.dataService.updateElement(this._testCase, true);
            }
        );
    }
}