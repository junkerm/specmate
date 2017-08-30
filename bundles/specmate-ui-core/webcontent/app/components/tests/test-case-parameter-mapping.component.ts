import {SpecmateDataService} from '../../services/data/specmate-data.service';
import {Component} from '@angular/core';
import { TestCaseComponentBase } from './test-case-component-base'


@Component({
    moduleId: module.id,
    selector: 'test-case-parameter-mapping',
    templateUrl: 'test-case-parameter-mapping.component.html'
})
export class TestCaseParameterMapping extends TestCaseComponentBase  {

    constructor(dataService: SpecmateDataService) {
        super(dataService);
    }
}