import { ParameterAssignment } from '../../model/ParameterAssignment';
import { Proxy } from '../../model/support/proxy';
import { TestParameter } from '../../model/TestParameter';
import { Type } from '../../util/Type';
import { IContainer } from '../../model/IContainer';
import { TestStep } from '../../model/TestStep';
import { SpecmateDataService } from '../../services/data/specmate-data.service';
import { Component, Input } from '@angular/core';
import { TestCaseComponentBase } from './test-case-component-base'
import { TestCase } from '../../model/TestCase';
import { TestProcedure } from '../../model/TestProcedure';
import { TestSpecification } from '../../model/TestSpecification';
import { Url } from '../../util/Url';


@Component({
    moduleId: module.id,
    selector: 'test-case-parameter-mapping',
    templateUrl: 'test-case-parameter-mapping.component.html'
})
export class TestCaseParameterMapping {
    
    private testCase: TestCase;
    private testSpecification: TestSpecification;
    private testSpecificationContents: IContainer[];
    private testCaseContents: IContainer[];
    private testProcedureContents: IContainer[];

    private _testProcedure: TestProcedure;

    public get testProcedure(): TestProcedure {
        return this._testProcedure;
    }

    @Input()
    public set testProcedure(testProcedure: TestProcedure) {
        if(!testProcedure) {
            return;
        }
        this._testProcedure = testProcedure;
        this.initContents();
    }

    constructor(private dataService: SpecmateDataService) { }

    private initContents(): void {
        this.dataService.readContents(this.testProcedure.url)
        .then((contents: IContainer[]) => this.testProcedureContents = contents)
        .then(() => this.dataService.readElement(Url.parent(this.testProcedure.url)))
        .then((element: IContainer) => this.testCase = element as TestCase)
        .then(() => this.dataService.readContents(this.testCase.url))
        .then((contents: IContainer[]) => this.testCaseContents = contents)
        .then(() => this.dataService.readElement(Url.parent(this.testCase.url)))
        .then((element: IContainer) => this.testSpecification = element as TestSpecification)
        .then(() => this.dataService.readContents(this.testSpecification.url))
        .then((contents: IContainer[]) => this.testSpecificationContents = contents);
    }

    private getTestParametersOfType(type: string): TestParameter[] {
        if(!this.testParameters) {
            return undefined;
        }
        return this.filterEmptyParameterAssignments(this.testParameters.filter((testParameter: TestParameter) => testParameter.type === type));
    }

    public get inputParameters(): TestParameter[] {
        return this.getTestParametersOfType('INPUT');
    }

    public get outputParameters(): TestParameter[] {
        return this.getTestParametersOfType('OUTPUT');
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

    private get testParameters(): TestParameter[] {
        if(!this.testSpecificationContents) {
            return undefined
        }
        return this.testSpecificationContents.filter((element: IContainer) => Type.is(element, TestParameter)).map((element:IContainer) => element as TestParameter);
    }

    private get assignments(): ParameterAssignment[] {
        if(!this.testCaseContents) {
            return undefined;
        }
        return this.testCaseContents.filter((element: IContainer) => Type.is(element, ParameterAssignment)).map((element: IContainer) => element as ParameterAssignment);
    }

    private getAssignment(testParameter: TestParameter): ParameterAssignment {
        if(!this.assignments) {
            return undefined;
        }
        return this.assignments.find((paramAssignment: ParameterAssignment) => paramAssignment.parameter.url === testParameter.url);
    }

    public getStepNumber(testStep: TestStep): number {
        return parseInt(String(testStep.position)) + 1;
    }
}