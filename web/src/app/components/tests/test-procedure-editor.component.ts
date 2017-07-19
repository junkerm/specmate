import {GenericForm} from '../core/forms/generic-form.component';
import {UUID} from 'angular2-uuid';
import {Config} from '../../config/config';
import {TestStep} from '../../model/TestStep';
import { IContentElement } from '../../model/IContentElement';
import { TestParameter } from '../../model/TestParameter';
import { TestSpecification } from '../../model/TestSpecification';
import { Type } from '../../util/Type';
import { TestCase } from '../../model/TestCase';
import { Url } from '../../util/Url';
import { IContainer } from '../../model/IContainer';
import { TestProcedure } from '../../model/TestProcedure';
import { Requirement } from '../../model/Requirement';
import { CEGModel } from '../../model/CEGModel';
import { Params, ActivatedRoute } from '@angular/router';
import { EditorCommonControlService } from '../../services/editor-common-control.service';
import { SpecmateDataService } from '../../services/specmate-data.service';
import { OnInit, Component, ViewChild } from '@angular/core';


@Component({
    moduleId: module.id,
    selector: 'test-procedure-editor',
    templateUrl: 'test-procedure-editor.component.html',
    //styleUrls: ['test-procedure-editor.component.css']
})
export class TestProcedureEditor implements OnInit {

    /** The test procedure being edited */
    testProcedure: TestProcedure;

    /** The contents of the test procedure */
    contents: IContainer[];

    /** The parent test case*/
    testCase: TestCase;

    /** The  parent test specification*/
    testSpecification: TestSpecification;

    /** The contents of the parent test specification */
    testSpecContents: IContainer[];

    /** The  parent requirement*/
    requirement: Requirement;

        /** The generic form used in this component */
    @ViewChild(GenericForm)
    private genericForm: GenericForm;

    /** getter for the input parameters of the parent test specification */
    get inputParameters(): IContentElement[] {
        return this.testSpecContents.filter(c => {
            return Type.is(c, TestParameter) && (<TestParameter>c).type === "INPUT";
        });
    }

    /** getter for the output parameters of the parent test specification */
    get outputParameters(): IContentElement[] {
        return this.testSpecContents.filter(c => {
            return Type.is(c, TestParameter) && (<TestParameter>c).type === "OUTPUT";
        });
    }


    /** Constructor */
    constructor(
        private dataService: SpecmateDataService,
        private route: ActivatedRoute,
        private editorCommonControlService: EditorCommonControlService
    ) { }

    ngOnInit() {
        this.editorCommonControlService.showCommonControls = true;
        this.dataService.clearCommits();
        this.route.params
            .switchMap((params: Params) => this.dataService.readElement(Url.fromParams(params)))
            .subscribe((testProcedure: IContainer) => {
                this.testProcedure = testProcedure as TestProcedure;
                this.readContents();
                this.readParents();
            });
    }

    ngDoCheck(args: any) {
        this.editorCommonControlService.isCurrentEditorValid = this.isValid;
    }

    /** Rads to the contents of the test specification  */
    private readContents(): void {
        if (this.testProcedure) {
            this.dataService.readContents(this.testProcedure.url).then((
                contents: IContainer[]) => {
                this.contents = contents;
            });
        }
    }

    /** Reads the parents of this test procedure */
    private readParents(): void {
        let testCaseUrl = Url.parent(this.testProcedure.url);
        let testSpecUrl = Url.parent(testCaseUrl);
        let testSpecParentUrl = Url.parent(testSpecUrl);
        this.readParentTestCase(testCaseUrl);
        this.readParentTestSpec(testSpecUrl);
        this.readParentRequirement(testSpecParentUrl);
    }

    /** Reads the parent test case */
    private readParentTestCase(testCaseUrl: string) {
        this.dataService.readElement(testCaseUrl).then(
            (element: IContainer) => {
                if (Type.is(element, TestCase)) {
                    this.testCase = <TestCase>element;
                }
            }
        )
    }

    /** Reads the parent test specification */
    private readParentTestSpec(testSpecUrl: string): void {
        if (this.testProcedure) {
            this.dataService.readElement(testSpecUrl).then((
                element: IContainer) => {
                if (Type.is(element, TestSpecification)) {
                    this.testSpecification = <TestSpecification>element;
                }
            });
            this.dataService.readContents(testSpecUrl).then(
                (elements: IContainer[]) => {
                    this.testSpecContents = elements;
                });
        }
    }

    /** Reads the parent requirement */
    private readParentRequirement(testSpecParentUrl: string): void {
        this.dataService.readElement(testSpecParentUrl).then((
            element: IContainer) => {
            if (Type.is(element, Requirement)) {
                this.requirement = <Requirement>element;
            } else if (Type.is(element, CEGModel)) {
                let cegUrl: string = Url.parent(testSpecParentUrl);
                this.readParentRequirementFromCEG(cegUrl);
            }
        });
    }


    /** Reads the parent requirement using the parent CEG */
    private readParentRequirementFromCEG(cegUrl: string): void {
        this.dataService.readElement(cegUrl).then((
            element: IContainer) => {
            if (Type.is(element, Requirement)) {
                this.requirement = <Requirement>element;
            }
        });
    }

    
    /** Creates a new test case */
    private createNewTestStep() {
        let id= this.getNewTestStepId();
        let url: string = Url.build([this.testProcedure.url, id]);
        let testStep: TestStep = new TestStep();
        testStep.name = Config.TESTSTEP_NAME;
        testStep.id = id;
        testStep.url = url;
        this.dataService.createElement(testStep, true);
    
    }

    /** Creates a new ID for a test step */
    getNewTestStepId(): string {
        return UUID.UUID();
    }


        /** Return true if all user inputs are valid  */
    private get isValid(): boolean {
        if (!this.genericForm) {
            return true;
        }
        return this.genericForm.isValid;
    }
}