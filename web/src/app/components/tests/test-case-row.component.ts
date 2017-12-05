import { Router } from '@angular/router';
import { Id } from '../../util/Id';
import { Config } from '../../config/config';
import { ConfirmationModal } from '../../services/notification/confirmation-modal.service';
import { Type } from '../../util/Type';
import { ParameterAssignment } from '../../model/ParameterAssignment';
import { IContentElement } from '../../model/IContentElement';
import { TestParameter } from '../../model/TestParameter';
import { TestCase } from '../../model/TestCase';
import { TestProcedure } from '../../model/TestProcedure';
import { SpecmateDataService } from '../../services/data/specmate-data.service';
import { OnInit, Component, Input } from '@angular/core';
import { Url } from '../../util/Url';
import { IContainer } from '../../model/IContainer';
import { TestCaseComponentBase } from './test-case-component-base'
import { NavigatorService } from "../../services/navigation/navigator.service";
import { TestProcedureFactory } from '../../factory/test-procedure-factory';
import { containsTree } from '@angular/router/src/url_tree';

@Component({
    moduleId: module.id,
    selector: '[test-case-row]',
    templateUrl: 'test-case-row.component.html'
})
export class TestCaseRow {

    private _testCase: TestCase;
    
    public get testCase(): TestCase {
        return this._testCase;
    }

    @Input()
    public set testCase(testCase: TestCase) {
        if(!Type.is(testCase, TestCase)) {
            return;
        }
        this._testCase = testCase;
        this.dataService.readContents(this.testCase.url)
            .then((contents: IContainer[]) => this.contents = contents)
            .then(() => this.dataService.readContents(Url.parent(this.testCase.url)))
            .then((contents: IContainer[]) => this.testSpecificationContents = contents);
    }

    private contents: IContainer[];

    private testSpecificationContents: IContainer[];

    constructor(private dataService: SpecmateDataService, private modal: ConfirmationModal, private navigator: NavigatorService) { }

    /** Retrieves a test procedure from the test case contents, if none exists, returns undefined */
    public get testProcedure(): TestProcedure {
        if(!this.contents) {
            return undefined;
        }
        return this.contents.find((element: IContainer) => Type.is(element, TestProcedure)) as TestProcedure;
    }

    /** Deletes the test case. */
    public delete(): void {
        this.modal.open("Do you really want to delete " + this.testCase.name + "?")
            .then(() => this.dataService.deleteElement(this.testCase.url, true, Id.uuid))
            .catch(() => {});
    }

    /** Asks for confirmation to save all change, creates a new test procedure and then navigates to it. */
    public createTestProcedure(): void {
        let factory: TestProcedureFactory = new TestProcedureFactory(this.dataService);
        this.modal.confirmSave()
            .then(() => factory.create(this.testCase, true))
            .then((testProcedure: TestProcedure) => this.navigator.navigate(testProcedure))
            .catch(() => {});
    }

    private get testParameters(): TestParameter[] {
        if(!this.testSpecificationContents) {
            return undefined;
        }
        return this.testSpecificationContents
            .filter((element: IContainer) => Type.is(element, TestParameter))
            .map((element: IContainer) => element as TestParameter)
    }

    public get inputParameters(): TestParameter[] {
        if(!this.testParameters) {
            return undefined;
        }
        let parameters: TestParameter[] = this.testParameters.filter((element: TestParameter) => element.type === 'INPUT');
        return parameters;
    }

    public get outputParameters(): TestParameter[] {
        if(!this.testParameters) {
            return undefined;
        }
        let parameters: TestParameter[] = this.testParameters.filter((element: TestParameter) => element.type === 'OUTPUT');
        return parameters;
    }

    private get assignments(): ParameterAssignment[] {
        if(!this.contents) {
            return undefined;
        }
        return this.contents.filter((element: IContainer) => Type.is(element, ParameterAssignment)).map((element: IContainer) => element as ParameterAssignment);
    }

    private getAssignment(testParameter: TestParameter): ParameterAssignment {
        if(!this.assignments) {
            return undefined;
        }
        return this.assignments.find((paramAssignment: ParameterAssignment) => paramAssignment.parameter.url === testParameter.url);
    }

    public get isVisible(): boolean {
        return Type.is(this.testCase, TestCase);
    }
    
}