import { SpecmateDataService } from '../../services/data/specmate-data.service';
import { SimpleInputFormBase } from '../forms/simple-input-form-base';
import { Id } from '../../util/Id';
import { TestStep } from '../../model/TestStep';
import { Component, Input, OnInit } from '@angular/core';
import { Url } from "../../util/Url";
import { IContainer } from "../../model/IContainer";
import { Type } from "../../util/Type";
import { ParameterAssignment } from "../../model/ParameterAssignment";
import { TestParameter } from "../../model/TestParameter";
import { Proxy } from '../../model/support/proxy';

@Component({
    moduleId: module.id,
    selector: '[test-step-row]',
    templateUrl: 'test-step-row.component.html'
})
export class TestStepRow extends SimpleInputFormBase implements OnInit {

    @Input()
    public set testStep(testStep: TestStep) {
        this.modelElement = testStep;
    }
    
    public get testStep(): TestStep {
        return this.modelElement as TestStep;
    }

    protected get fields(): string[] {
        return ['description', 'expectedOutcome'];
    }

    public get selectedTestParameter(): TestParameter {
        if(!this.testStep || !this.testStep.referencedTestParameters || this.testStep.referencedTestParameters.length <= 0) {
            return undefined;
        }
        return this.testParameters.find((testParameter: TestParameter) => testParameter.url === this.testStep.referencedTestParameters[0].url);
    }

    public set selectedTestParameter(testParameter: TestParameter) {
        if(!testParameter) {
            this.testStep.referencedTestParameters = [];
            this.dataService.updateElement(this.testStep, true, Id.uuid);
            return;
        }
        if(!this.testStep.referencedTestParameters) {
            this.testStep.referencedTestParameters = [];
        }
        this.testStep.referencedTestParameters[0] = new Proxy();
        this.testStep.referencedTestParameters[0].url = testParameter.url;
        this.dataService.updateElement(this.testStep, true, Id.uuid);
    }

    @Input()
    public stepNumber: number;

    @Input()
    public testSteps: TestStep[];

    @Input()
    public testParameters: TestParameter[];

    public parameterAssignments: ParameterAssignment[];

    constructor(protected dataService: SpecmateDataService) {
        super();
    }

    ngOnInit() {
        let testSpecUrl: string = Url.parent(Url.parent(this.testStep.url));
        this.dataService.readContents(testSpecUrl).then((contents: IContainer[]) => {
            this.parameterAssignments = contents.filter((element: IContainer) => Type.is(element, ParameterAssignment)).map((element: IContainer) => element as ParameterAssignment);
        });
    }

    public delete(): void {
        let compoundId: string = Id.uuid;
        this.dataService.deleteElement(this.testStep.url, true, compoundId);
    }

    public getTestParameter(url: string): TestParameter {
        if(!this.testParameters) {
            return undefined;
        }
        return this.testParameters.find((testParameter: TestParameter) => testParameter.url === url);
    }

    public getParameterAssignment(testParameter: TestParameter): ParameterAssignment {
        if(!this.parameterAssignments) {
            return undefined;
        }
        return this.parameterAssignments.find((parameterAssignment: ParameterAssignment) => parameterAssignment.parameter.url === testParameter.url);
    }
}