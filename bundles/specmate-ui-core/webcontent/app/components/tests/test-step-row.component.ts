import { ConfirmationModal } from '../core/forms/confirmation-modal.service';
import { SpecmateDataService } from '../../services/specmate-data.service';
import { TestStep } from '../../model/TestStep';
import { OnInit, Component, Input, Inject, forwardRef } from '@angular/core';
import { Validators, FormControl, FormGroup } from '@angular/forms';

@Component({
    moduleId: module.id,
    selector: '[test-step-row]',
    templateUrl: 'test-step-row.component.html'
})
export class TestStepRow {
    /** The test step */
    private _testStep: TestStep;

    /** The form group */
    formGroup: FormGroup;

    @Input()
    set testStep(testStep:TestStep) {
        this._testStep = testStep;
        this.buildFormGroup();
    }

    @Input()
    stepNumber: number;

    @Input()
    testSteps: TestStep[];

    ngDoCheck(args: any) {
        this.updateFormGroup();
    }

    get testStep(): TestStep {
        return this._testStep;
    }

    constructor(private dataService: SpecmateDataService) {
        this.formGroup = new FormGroup({});
    }

    private buildFormGroup(): void {
        this.formGroup = new FormGroup({
            'description': new FormControl(this._testStep.description, Validators.required),
            'expectedOutcome': new FormControl(this._testStep.expectedOutcome, Validators.required)
        });
        this.formGroup.valueChanges.subscribe(() => {
                this._testStep.description = this.formGroup.controls["description"].value;
                this._testStep.expectedOutcome = this.formGroup.controls["expectedOutcome"].value;
                this.dataService.updateElement(this._testStep, true);
            }
        );
    }

    private updateFormGroup(): void {
        let formBuilderObject: any = {};
        formBuilderObject.description = this._testStep.description;
        formBuilderObject.expectedOutcome = this._testStep.expectedOutcome;
        this.formGroup.setValue(formBuilderObject);
    }

    public delete(): void {
        this.dataService.deleteElement(this.testStep.url, true);
        this.testSteps.forEach((testStep: TestStep, index: number) => {
            testStep.position = index;
            this.dataService.updateElement(testStep, true);
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
        this.dataService.updateElement(this.testStep, true);
        this.dataService.updateElement(otherTestStep, true);
    }

    private get nextTestStep(): TestStep {
        let nextPosition: number = this.testStep.position + 1;
        if(this.testSteps.length > nextPosition) {
            return this.testSteps[nextPosition];
        }
        return undefined;
    }

    private get prevTestStep(): TestStep {
        let prevPosition: number = this.testStep.position - 1;
        if(prevPosition >= 0) {
            return this.testSteps[prevPosition];
        }
        return undefined;
    }
}