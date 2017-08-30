import { Id } from '../../util/Id';
import { TestCase } from '../../model/TestCase';
import { SimpleInputFormBase } from '../core/forms/simple-input-form-base';
import { ParameterAssignment } from '../../model/ParameterAssignment';
import { SpecmateDataService } from '../../services/data/specmate-data.service';
import { Validators, FormControl, FormGroup } from '@angular/forms';
import { Input, OnInit, Component } from '@angular/core';

@Component({
    moduleId: module.id,
    selector: 'test-case-name-form',
    templateUrl: 'test-case-name-form.component.html',
    styleUrls: ['test-case-name-form.component.css']
})
export class TestCaseNameForm extends SimpleInputFormBase {

    @Input()
    set testCase(testCase: TestCase) {
        this.modelElement = testCase;
    }

    protected get fields(): string[] {
        return ['name'];
    }

    constructor(protected dataService: SpecmateDataService) {
        super();
    }
}