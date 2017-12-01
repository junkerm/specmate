import { Id } from '../../util/Id';
import { SimpleInputFormBase } from '../forms/simple-input-form-base';
import { SpecmateDataService } from '../../services/data/specmate-data.service';
import { Validators, FormControl, FormGroup } from '@angular/forms';
import { TestParameter } from '../../model/TestParameter';
import { Input, OnInit, Component } from '@angular/core';
import { IContainer } from '../../model/IContainer';
import { Url } from '../../util/Url';
import { TestCase } from '../../model/TestCase';
import { Type } from '../../util/Type';
import { ParameterAssignment } from '../../model/ParameterAssignment';

@Component({
    moduleId: module.id,
    selector: 'test-parameter-form',
    templateUrl: 'test-parameter-form.component.html',
    styleUrls: ['test-parameter-form.component.css']
})
export class TestParameterForm extends SimpleInputFormBase {

    private testSpecificationContents: IContainer[];
    private parameterAssignments: ParameterAssignment[];

    @Input()
    public set testParameter(testParameter: TestParameter) {
        this.modelElement = testParameter;
        this.dataService.readContents(Url.parent(this.modelElement.url))
            .then((contents: IContainer[]) => this.testSpecificationContents = contents)
            .then(() => this.loadParameterAssignments());
    }

    public get testParameter(): TestParameter {
        return this.modelElement as TestParameter;
    }

    public get fields(): string[] {
        return ['name'];
    }

    constructor(protected dataService: SpecmateDataService) {
        super();
    }

    public deleteParameter(): void {
        if(!this.deleteColumnEnabled) {
            return;
        }
        let compoundId: string = Id.uuid;
        let deleteParameterAssignmentsTask: Promise<void> = Promise.resolve();
        for(let i = 0; i < this.parameterAssignments.length; i++) {
            deleteParameterAssignmentsTask = deleteParameterAssignmentsTask.then(() => {
                return this.dataService.deleteElement(this.parameterAssignments[i].url, true, compoundId);
            });
        }
        deleteParameterAssignmentsTask.then(() => this.dataService.deleteElement(this.testParameter.url, true, compoundId));
    }

    private get testCases(): TestCase[] {
        return this.testSpecificationContents.filter((element: IContainer) => Type.is(element, TestCase)).map((element: IContainer) => element as TestCase);
    }

    private loadParameterAssignments(): Promise<void> {
        let testCases: TestCase[] = this.testCases;
        this.parameterAssignments = [];
        let loadParameterAssignmentsTask: Promise<void> = Promise.resolve();
        for(let i = 0; i < testCases.length; i++) {
            let currentTestCase: TestCase = testCases[i];
            loadParameterAssignmentsTask = loadParameterAssignmentsTask.then(() => {
                return this.dataService.readContents(currentTestCase.url)
                    .then((contents: IContainer[]) => contents.forEach((element: IContainer) => this.parameterAssignments.push(element as ParameterAssignment)));
            });
        }
        return loadParameterAssignmentsTask;
    }

    private get testParameters(): TestParameter[] {
        if(!this.testSpecificationContents) {
            return undefined;
        }
        return this.testSpecificationContents.filter((element: IContainer) => Type.is(element, TestParameter)).map((element: IContainer) => element as TestParameter);
    }

    /** returns true if deletion of columns is allowed */
    public get deleteColumnEnabled(): boolean {
        if(!this.testParameters) {
            return false;
        }
        return this.testParameters.length > 1;
    }

}