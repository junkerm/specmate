import { Component, Input } from '@angular/core';
import { SimpleInputFormBase } from '../../../../../../forms/modules/generic-form/base/simple-input-form-base';
import { TestCase } from '../../../../../../../model/TestCase';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';

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
