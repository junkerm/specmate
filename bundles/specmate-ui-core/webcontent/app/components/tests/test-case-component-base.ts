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

export class TestCaseComponentBase {

    private _testCase: TestCase;

    /** The test case to display */
    @Input()
    set testCase(testCase: TestCase) {
        this._testCase = testCase;
        this.loadContents();
    }

    get testCase(): TestCase {
        return this._testCase;
    }

    public get isVisible(): boolean {
        return this.testCase && Type.is(this.testCase, TestCase);
    }

    /** Input Parameters of the test specfication that should be shown*/
    @Input()
    inputParameters: TestParameter[];

    /** Output Parameters of the test specfication that should be shown*/
    @Input()
    outputParameters: TestParameter[];

    /** All contents of the test case */
    protected contents: IContentElement[];

    /** The parameter assignments of this testcase */
    protected assignments: ParameterAssignment[];

    /** constructor */
    constructor(protected dataService: SpecmateDataService) { }

    /** We initialize the assignments here. */
    public loadContents(virtual?: boolean): void {
        this.dataService.readContents(this.testCase.url, virtual).then((
            contents: IContainer[]) => {
                if(!Type.is(this.testCase, TestCase) || !contents || contents.length == 0) {
                    return;
                }
                this.contents = contents;
                this.assignments = contents.filter((element: IContainer) => Type.is(element, ParameterAssignment)).map((element: IContainer) => element as ParameterAssignment);
        });
    }

    public getAssignment(testParameter: TestParameter): ParameterAssignment {
        return this.assignments.find((paramAssignment: ParameterAssignment) => paramAssignment.parameter.url === testParameter.url);
    }
}