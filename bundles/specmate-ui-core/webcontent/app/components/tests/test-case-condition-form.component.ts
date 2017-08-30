import { Id } from '../../util/Id';
import { SimpleInputFormBase } from '../core/forms/simple-input-form-base';
import { ParameterAssignment } from '../../model/ParameterAssignment';
import { SpecmateDataService } from '../../services/data/specmate-data.service';
import { Validators, FormControl, FormGroup } from '@angular/forms';
import { Input, OnInit, Component } from '@angular/core';

@Component({
    moduleId: module.id,
    selector: 'test-case-condition-form',
    templateUrl: 'test-case-condition-form.component.html',
    styleUrls: ['test-case-condition-form.component.css']
})
export class TestCaseConditionForm extends SimpleInputFormBase {

    @Input()
    set paramAssignment(paramAssignment: ParameterAssignment) {
        this.modelElement = paramAssignment;
    }

    get fields(): string[] {
        return ['condition'];
    }

    constructor(protected dataService: SpecmateDataService) {
        super();
    }
}