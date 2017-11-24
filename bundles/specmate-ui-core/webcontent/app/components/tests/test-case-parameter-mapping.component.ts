import {ParameterAssignment} from '../../model/ParameterAssignment';
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
export class TestCaseParameterMapping extends TestCaseComponentBase {
    
    @Input()
    public testProcedureContents: IContainer[];

    constructor(dataService: SpecmateDataService) {
        super(dataService);

    }

    private _inputParameters: TestParameter[];
    /** Input Parameters of the test specfication that should be shown*/
    @Input()
    public set inputParameters(params: TestParameter[]) {
        this._inputParameters = params;
    }

    public get inputParameters(): TestParameter[] {
        return this.filterEmptyParameterAssignments(this._inputParameters);
    }

    private _outputParameters: TestParameter[];
    /** Output Parameters of the test specfication that should be shown*/
    @Input()
    public set outputParameters(params: TestParameter[]) {
        this._outputParameters = params;
    }

    public get outputParameters(): TestParameter[] {
        return this.filterEmptyParameterAssignments(this._outputParameters);
    }

    private filterEmptyParameterAssignments(params: TestParameter[]): TestParameter[] {
        return params.filter((param: TestParameter) => {
            let paramAssignment: ParameterAssignment = this.getAssignment(param);
            return paramAssignment && paramAssignment.condition && paramAssignment.condition !== '';
        });
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