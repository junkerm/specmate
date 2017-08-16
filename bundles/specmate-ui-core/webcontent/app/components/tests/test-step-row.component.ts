import { SpecmateDataService } from '../../services/specmate-data.service';
import { SimpleInputFormBase } from '../core/forms/simple-input-form-base';
import { Id } from '../../util/Id';
import { TestStep } from '../../model/TestStep';
import { Component, Input, OnInit } from '@angular/core';
import { Url } from "../../util/Url";
import { IContainer } from "../../model/IContainer";
import { Type } from "../../util/Type";
import { ParameterAssignment } from "../../model/ParameterAssignment";
import { TestParameter } from "../../model/TestParameter";

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
        this.testSteps.forEach((testStep: TestStep, index: number) => {
            testStep.position = index;
            this.dataService.updateElement(testStep, true, compoundId);
        });
    }

    public moveUp(): void {
        this.swap(this.prevTestStep);
    }

    public moveDown(): void {
        this.swap(this.nextTestStep);
    }

    private swap(otherTestStep: TestStep): void {
        let originalPosition: number = this.testStep.position;
        this.testStep.position = otherTestStep.position;
        otherTestStep.position = originalPosition;
        let compoundId: string = Id.uuid;
        this.dataService.updateElement(this.testStep, true, compoundId);
        this.dataService.updateElement(otherTestStep, true, compoundId);
    }

    private get nextTestStep(): TestStep {
        let nextPosition: number = parseInt(this.testStep.position + "") + 1;
        if(this.testSteps.length > nextPosition) {
            return this.testSteps[nextPosition];
        }
        return undefined;
    }

    private get prevTestStep(): TestStep {
        let prevPosition: number = parseInt(this.testStep.position + "") - 1;
        if(prevPosition >= 0) {
            return this.testSteps[prevPosition];
        }
        return undefined;
    }

    public getTestParameter(url: string): TestParameter {
        if(!this.testParameters) {
            return undefined;
        }
        return this.testParameters.find((testParameter: TestParameter) => testParameter.url === url);
    }
}