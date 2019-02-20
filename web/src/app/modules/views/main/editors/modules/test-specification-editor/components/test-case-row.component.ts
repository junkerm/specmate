import { Component, Input } from '@angular/core';
import { TestCase } from '../../../../../../../model/TestCase';
import { Type } from '../../../../../../../util/type';
import { IContainer } from '../../../../../../../model/IContainer';
import { Url } from '../../../../../../../util/url';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { TestProcedure } from '../../../../../../../model/TestProcedure';
import { TestProcedureFactory } from '../../../../../../../factory/test-procedure-factory';
import { TestParameter } from '../../../../../../../model/TestParameter';
import { Id } from '../../../../../../../util/id';
import { ParameterAssignment } from '../../../../../../../model/ParameterAssignment';
import { TranslateService } from '@ngx-translate/core';

@Component({
    moduleId: module.id.toString(),
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
        if (!Type.is(testCase, TestCase)) {
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

    constructor(private dataService: SpecmateDataService,
        private modal: ConfirmationModal,
        private navigator: NavigatorService,
        private translate: TranslateService) { }

    /** Retrieves a test procedure from the test case contents, if none exists, returns undefined */
    public get testProcedure(): TestProcedure {
        if (!this.contents) {
            return undefined;
        }
        return this.contents.find((element: IContainer) => Type.is(element, TestProcedure)) as TestProcedure;
    }

    /** Deletes the test case. */
    public async delete(): Promise<void> {
        try {
            const message = await this.translate.get('doYouReallyWantToDelete', { name: this.testCase.name }).toPromise();
            await this.modal.confirmDelete(this.translate.instant('ConfirmationRequired'), message);
            await this.dataService.deleteElement(this.testCase.url, true, Id.uuid);
        } catch (e) { }
    }

    /** Asks for confirmation to save all change, creates a new test procedure and then navigates to it. */
    public createTestProcedure(): void {
        let factory: TestProcedureFactory = new TestProcedureFactory(this.dataService);
        this.modal.confirmSave()
            .then(() => factory.create(this.testCase, true))
            .then((testProcedure: TestProcedure) => this.navigator.navigate(testProcedure))
            .catch(() => { });
    }

    private get testParameters(): TestParameter[] {
        if (!this.testSpecificationContents) {
            return undefined;
        }
        return this.testSpecificationContents
            .filter((element: IContainer) => Type.is(element, TestParameter))
            .map((element: IContainer) => element as TestParameter);
    }

    public get inputParameters(): TestParameter[] {
        return this.getInOutParameters('INPUT');
    }

    public get outputParameters(): TestParameter[] {
        return this.getInOutParameters('OUTPUT');
    }

    private getInOutParameters(type: string): TestParameter[] {
        if (!this.testParameters) {
            return undefined;
        }
        let parameters: TestParameter[] = this.testParameters.filter((element: TestParameter) => element.type === type);
        return parameters;
    }

    private get assignments(): ParameterAssignment[] {
        if (!this.contents) {
            return undefined;
        }
        return this.contents
            .filter((element: IContainer) => Type.is(element, ParameterAssignment))
            .map((element: IContainer) => element as ParameterAssignment);
    }

    private getAssignment(testParameter: TestParameter): ParameterAssignment {
        if (!this.assignments) {
            return undefined;
        }
        return this.assignments.find((paramAssignment: ParameterAssignment) => paramAssignment.parameter.url === testParameter.url);
    }

    public get isVisible(): boolean {
        return Type.is(this.testCase, TestCase);
    }

}
