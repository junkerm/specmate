import {IContainer} from '../../model/IContainer';
import {Url} from '../../util/Url';
import { SpecmateDataService } from '../../services/specmate-data.service';
import { SimpleInputFormBase } from '../core/forms/simple-input-form-base';
import { Id } from '../../util/Id';
import { TestStep } from '../../model/TestStep';
import { Component, Input } from '@angular/core';

@Component({
    moduleId: module.id,
    selector: '[test-step-row]',
    templateUrl: 'test-step-row.component.html'
})
export class TestStepRow extends SimpleInputFormBase {

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

    constructor(protected dataService: SpecmateDataService) {
        super();
    }

    ngOnInit() {
        this.dataService.readContents(Url.parent(Url.parent(this.testStep.url)), true).then((contents: IContainer[]) => {
            contents.forEach((element: IContainer) => {
                console.log(element);
            });
        })
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
}