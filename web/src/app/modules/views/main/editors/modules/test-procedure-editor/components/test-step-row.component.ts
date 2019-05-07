import { Component, Input } from '@angular/core';
import { IContainer } from '../../../../../../../model/IContainer';
import { ParameterAssignment } from '../../../../../../../model/ParameterAssignment';
import { Proxy } from '../../../../../../../model/support/proxy';
import { TestParameter } from '../../../../../../../model/TestParameter';
import { TestStep } from '../../../../../../../model/TestStep';
import { Id } from '../../../../../../../util/id';
import { Type } from '../../../../../../../util/type';
import { Url } from '../../../../../../../util/url';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { SimpleInputFormBase } from '../../../../../../forms/modules/generic-form/base/simple-input-form-base';

@Component({
    moduleId: module.id.toString(),
    selector: '[test-step-row]',
    templateUrl: 'test-step-row.component.html'
})
export class TestStepRow extends SimpleInputFormBase {

    private testSpecificationContents: IContainer[];
    private testProcedureContents: IContainer[];

    @Input()
    public set testStep(testStep: TestStep) {
        let testStepUrl: string = testStep.url;
        let testProcedureUrl: string = Url.parent(testStepUrl);
        let testCaseUrl: string = Url.parent(testProcedureUrl);
        let testSpecificationUrl: string = Url.parent(testCaseUrl);
        this.dataService.readContents(testSpecificationUrl)
            .then((contents: IContainer[]) => this.testSpecificationContents = contents)
            .then(() => this.dataService.readContents(testProcedureUrl))
            .then((contents: IContainer[]) => this.testProcedureContents = contents)
            .then(() => this.modelElement = testStep);
    }

    protected get fields(): string[] {
        return ['description', 'expectedOutcome'];
    }

    public get testStep(): TestStep {
        return this.modelElement as TestStep;
    }

    public get testSteps(): TestStep[] {
        if (!this.testProcedureContents) {
            return undefined;
        }
        return this.testProcedureContents
            .filter((element: IContainer) => Type.is(element, TestStep))
            .map((element: IContainer) => element as TestStep);
    }

    public get testParameters(): TestParameter[] {
        if (!this.testSpecificationContents) {
            return undefined;
        }
        return  this.testSpecificationContents
            .filter((element: IContainer) => Type.is(element, TestParameter))
            .map((element: IContainer) => element as TestParameter);
    }

    public get parameterAssignments(): ParameterAssignment[] {
        if (!this.testSpecificationContents) {
            return undefined;
        }
        return this.testSpecificationContents
            .filter((element: IContainer) => Type.is(element, ParameterAssignment))
            .map((element: IContainer) => element as ParameterAssignment);
    }


    public get selectedTestParameter(): TestParameter {
        if (!this.testStep || !this.testStep.referencedTestParameters || this.testStep.referencedTestParameters.length <= 0) {
            return undefined;
        }
        return this.testParameters
            .find((testParameter: TestParameter) => testParameter.url === this.testStep.referencedTestParameters[0].url);
    }

    public set selectedTestParameter(testParameter: TestParameter) {
        if (!testParameter) {
            this.testStep.referencedTestParameters = [];
            this.dataService.updateElement(this.testStep, true, Id.uuid);
            return;
        }
        if (!this.testStep.referencedTestParameters) {
            this.testStep.referencedTestParameters = [];
        }
        this.testStep.referencedTestParameters[0] = new Proxy();
        this.testStep.referencedTestParameters[0].url = testParameter.url;
        this.dataService.updateElement(this.testStep, true, Id.uuid);
    }

    constructor(protected dataService: SpecmateDataService) {
        super();
    }

    public delete(): void {
        let compoundId: string = Id.uuid;
        this.dataService.deleteElement(this.testStep.url, true, compoundId)
            .then(() => this.dataService.sanitizeContentPositions(this.testSteps, true, compoundId));
    }

    public getTestParameter(url: string): TestParameter {
        if (!this.testParameters) {
            return undefined;
        }
        return this.testParameters.find((testParameter: TestParameter) => testParameter.url === url);
    }

    public getParameterAssignment(testParameter: TestParameter): ParameterAssignment {
        if (!this.parameterAssignments) {
            return undefined;
        }
        return this.parameterAssignments
            .find((parameterAssignment: ParameterAssignment) => parameterAssignment.parameter.url === testParameter.url);
    }

    public getPosition(testStep: TestStep): number {
        return parseInt(String(testStep.position), 10) + 1;
    }
}
