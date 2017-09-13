import { Proxy } from '../../model/support/proxy';
import { TestParameter } from '../../model/TestParameter';
import { Type } from '../../util/Type';
import { IContainer } from '../../model/IContainer';
import { TestStep } from '../../model/TestStep';
import { SpecmateDataService } from '../../services/data/specmate-data.service';
import { Component, Input } from '@angular/core';
import { TestCaseComponentBase } from './test-case-component-base'


@Component({
    moduleId: module.id,
    selector: 'test-case-parameter-mapping',
    templateUrl: 'test-case-parameter-mapping.component.html'
})
export class TestCaseParameterMapping extends TestCaseComponentBase  {
    
    @Input()
    public testProcedureContents: IContainer[];

    constructor(dataService: SpecmateDataService) {
        super(dataService);

    }

    public referencingTestSteps(testParameter: TestParameter): TestStep[] {
        if(!this.testProcedureContents) {
            return [];
        }
        return this.testProcedureContents
            .filter((element: IContainer) => Type.is(element, TestStep))
            .filter((testStep: TestStep) => testStep.referencedTestParameters.findIndex((proxy: Proxy) => proxy.url === testParameter.url) >= 0) as TestStep[];
    }
}