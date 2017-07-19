import {ParameterAssignment} from '../../model/ParameterAssignment';
import { SpecmateDataService } from '../../services/specmate-data.service';
import { Validators, FormControl, FormGroup } from '@angular/forms';
import { Input, OnInit, Component } from '@angular/core';

@Component({
    moduleId: module.id,
    selector: 'test-case-value-form',
    templateUrl: 'test-case-value-form.component.html',
    styleUrls: ['test-case-value-form.component.css']
})
export class TestCaseValueForm {

    /** The form group */
    formGroup: FormGroup;

    private _paramAssignment: ParameterAssignment;

    /** The parameter assignment */
    @Input()
    set paramAssignment(paramAssignment: ParameterAssignment) {
        this._paramAssignment = paramAssignment;
        this.buildFormGroup();
    }

    constructor(private dataService: SpecmateDataService) {
        this.formGroup = new FormGroup({});
    }

    private buildFormGroup(): void {
        this.formGroup = new FormGroup({
            'paramAssignment': new FormControl(this._paramAssignment ? this._paramAssignment.value : "", Validators.required)
        });
        this.formGroup.valueChanges.subscribe(() => {
                if(!this._paramAssignment) {
                    return;
                }
                this._paramAssignment.value = this.formGroup.controls['paramAssignment'].value;
                this.dataService.updateElement(this._paramAssignment, true);
            }
        );
    }
}