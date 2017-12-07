import { OnInit, Input } from '@angular/core';
import { TestCase } from '../../../../../../../model/TestCase';
import { Type } from '../../../../../../../util/type';
import { TestParameter } from '../../../../../../../model/TestParameter';
import { IContentElement } from '../../../../../../../model/IContentElement';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { IContainer } from '../../../../../../../model/IContainer';
import { ParameterAssignment } from '../../../../../../../model/ParameterAssignment';

export class TestCaseComponentBase implements OnInit {

    private _testCase: TestCase;

    /** The test case to display */
    @Input()
    set testCase(testCase: TestCase) {
        this._testCase = testCase;
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

    /** constructor */
    constructor(protected dataService: SpecmateDataService) { }

    ngOnInit(): void {
        this.loadContents(true);
    }

    /** We initialize the assignments here. */
    public loadContents(virtual?: boolean): void {
        if (!Type.is(this.testCase, TestCase)) {
            return;
        }
        this.dataService.readContents(this.testCase.url, virtual).then((
            contents: IContainer[]) => {
                if (!Type.is(this.testCase, TestCase) || !contents || contents.length === 0) {
                    return;
                }
                this.contents = contents;
        });
    }

    private get assignments(): ParameterAssignment[] {
        if (!this.contents) {
            return undefined;
        }
        return this.contents
            .filter((element: IContainer) => Type.is(element, ParameterAssignment))
            .map((element: IContainer) => element as ParameterAssignment);
    }

    public getAssignment(testParameter: TestParameter): ParameterAssignment {
        if (!this.assignments) {
            return undefined;
        }
        return this.assignments.find((paramAssignment: ParameterAssignment) => paramAssignment.parameter.url === testParameter.url);
    }
}
