import { Id } from '../../util/Id';
import { Config } from '../../config/config';
import { ConfirmationModal } from '../core/forms/confirmation-modal.service';
import { Type } from '../../util/Type';
import { ParameterAssignment } from '../../model/ParameterAssignment';
import { IContentElement } from '../../model/IContentElement';
import { TestParameter } from '../../model/TestParameter';
import { TestCase } from '../../model/TestCase';
import { TestProcedure } from '../../model/TestProcedure';
import { SpecmateDataService } from '../../services/specmate-data.service';
import { OnInit, Component, Input } from '@angular/core';
import { Params, ActivatedRoute, Router } from '@angular/router';
import { Url } from '../../util/Url';
import { IContainer } from '../../model/IContainer';
import { UUID } from 'angular2-uuid';
import { TestCaseComponentBase } from './test-case-component-base'

@Component({
    moduleId: module.id,
    selector: '[test-case-row]',
    templateUrl: 'test-case-row.component.html'
})
export class TestCaseRow extends TestCaseComponentBase implements OnInit {

    /** constructor */
    constructor(dataService: SpecmateDataService, private router: Router, private route: ActivatedRoute, private modal: ConfirmationModal) {
        super(dataService);
    }

    /** Retrieves a test procedure from the test case contents, if no exists, returns undefined */
    get testProcedure(): TestProcedure {
        return this.contents.find(c => Type.is(c, TestProcedure));
    }

    /** Deletes the test case. */
    delete(): void {
        this.modal.open("Do you really want to delete " + this.testCase.name + "?")
            .then(() => this.dataService.deleteElement(this.testCase.url, true))
            .catch(() => { });
    }

    /** Asks for confirmation to save all change, creates a new test procedure and then navigates to it. */
    createTestProcedure(): void {
        if (this.dataService.hasCommits) {
            this.modal.open("To create a new test procedure, the test specification has to saved. " +
                "Do you want to save now and create a new test procedure, or do you want to abort?")
                .then(() => this.dataService.commit("Save Test Specification"))
                .then(() => this.doCreateTestProcedure());
        } else {
            this.doCreateTestProcedure();
        }
    }

    /** Creates a new test procedure and navigates to the new test procedure. */
    doCreateTestProcedure(): void {
        let id = this.getNewTestProcedureId();
        let url: string = Url.build([this.testCase.url, id]);
        let testProcedure: TestProcedure = new TestProcedure();
        testProcedure.name = Config.TESTPROCEDURE_NAME;
        testProcedure.id = id;
        testProcedure.url = url;

        this.dataService.createElement(testProcedure, true).then(() => {
            this.dataService.commit("new Test Procedure").then(() =>
                this.router.navigate(['/tests', { outlets: { 'main': [url, 'tpe'] } }])
            );
        });
    }

    /** Creates a new ID for a test procedure */
    getNewTestProcedureId(): string {
        return UUID.UUID();
    }
}