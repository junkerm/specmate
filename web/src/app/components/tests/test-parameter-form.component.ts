import {SpecmateDataService} from '../../services/specmate-data.service';
import {Validators, FormControl,  FormGroup} from '@angular/forms';
import {TestParameter} from '../../model/TestParameter';
import {Input, OnInit,  Component} from '@angular/core';

@Component({
    moduleId: module.id,
    selector: 'test-specification-editor',
    templateUrl: 'test-specification-editor.component.html'
})
export class TestParameterForm  {

    /** The form group */
    formGroup: FormGroup;

    /** The test parameter to display */
    @Input()
    testParameter: TestParameter;

     constructor(private dataService: SpecmateDataService) { 
        this.buildFormGroup();
    }

    private buildFormGroup():void {
        this.formGroup = new FormGroup({
            'parameter': new FormControl(this.testParameter.name,Validators.required)
        });
        this.formGroup.valueChanges.subscribe(() => this.dataService.updateElement(this.testParameter));
    }
}