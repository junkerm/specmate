import { Id } from '../../util/Id';
import { SimpleInputFormBase } from '../core/forms/simple-input-form-base';
import { SpecmateDataService } from '../../services/data/specmate-data.service';
import { Validators, FormControl, FormGroup } from '@angular/forms';
import { TestParameter } from '../../model/TestParameter';
import { Input, OnInit, Component } from '@angular/core';

@Component({
    moduleId: module.id,
    selector: 'test-parameter-form',
    templateUrl: 'test-parameter-form.component.html',
    styleUrls: ['test-parameter-form.component.css']
})
export class TestParameterForm extends SimpleInputFormBase {

    @Input()
    set testParameter(testParameter: TestParameter) {
        this.modelElement = testParameter;
    }

    get testParameter(): TestParameter {
        return this.modelElement as TestParameter;
    }

    get fields(): string[] {
        return ['name'];
    }

    constructor(protected dataService: SpecmateDataService) {
        super();
    }

    deleteParameter(): void {
        this.dataService.deleteElement(this.testParameter.url, true, Id.uuid);
    }
}