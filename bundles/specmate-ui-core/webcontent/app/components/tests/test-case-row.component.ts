import {ConfirmationModal} from '../core/forms/confirmation-modal.service';
import { Type } from '../../util/Type';
import { ParameterAssignment } from '../../model/ParameterAssignment';
import { IContentElement } from '../../model/IContentElement';
import { TestParameter } from '../../model/TestParameter';
import { TestCase } from '../../model/TestCase';
import { SpecmateDataService } from '../../services/specmate-data.service';
import { OnInit, Component, Input } from '@angular/core';
import { Params, ActivatedRoute, Router } from '@angular/router';
import { Url } from '../../util/Url';
import { IContainer } from '../../model/IContainer';

@Component({
    moduleId: module.id,
    selector: '[test-case-row]',
    templateUrl: 'test-case-row.component.html'
})
export class TestCaseRow implements OnInit {

    /** The test case to display */
    @Input()
    private testCase: TestCase;

    private _inputParameters: TestParameter[];
    /** Input Parameters of the test specfication that should be shown*/
    @Input()
    public set inputParameters(parameters: TestParameter[]) {
        this._inputParameters = parameters;
        this.initialize(true);
    }

    public get inputParameters(): TestParameter[] {
        return this._inputParameters;
    }

    private _outputParameters: TestParameter[];
    /** Output Parameters of the test specfication that should be shown*/
    @Input()
    public set outputParameters(parameters: TestParameter[]) {
        this._outputParameters = parameters;
        this.initialize(true);
    }

    public get outputParameters(): TestParameter[] {
        return this._outputParameters;
    }

    /** The parameter assignments of this testcase */
    private assignments: ParameterAssignment[];

    /** Maps parameter url to assignments for this paraemter */
    private assignmentMap: { [key: string]: ParameterAssignment };

    constructor(private dataService: SpecmateDataService, private router: Router, private route: ActivatedRoute, private modal: ConfirmationModal) { }

    ngOnInit() {
        this.initialize(false);
    }

    /** We initialize the assignments here. */
    private initialize(virtual: boolean): void {
        this.dataService.readContents(this.testCase.url, virtual).then((
            contents: IContainer[]) => {
            this.assignments = contents.filter(c => Type.is(c, ParameterAssignment)).map(c => <ParameterAssignment>c);
            this.assignmentMap = this.deriveAssignmentMap(this.assignments);
            console.log("INIT DONE -> " + virtual);
        });
    }

    /** Derives the parameter assignments matching to the display parameters in the right order */
    private deriveAssignmentMap(assignments: ParameterAssignment[]): { [key: string]: ParameterAssignment } {
        let assignmentMap = {};
        for (let assignment of this.assignments) {
            assignmentMap[assignment.parameter.url] = assignment;
        }
        return assignmentMap;
    }

    /** Deletes the test case. */
    delete(): void {
        this.modal.open("Do you really want to delete " + this.testCase.name + "?")
            .then(() => this.dataService.deleteElement(this.testCase.url, true))
            .then(() => this.dataService.commit('Delete'))
            .catch(() => { });
    }
}