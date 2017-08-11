import {ConfirmationModal} from '../core/forms/confirmation-modal.service';
import {NavigatorService} from '../../services/navigator.service';
import { ActivatedRoute, Params } from '@angular/router';
import { TestCaseRow } from './test-case-row.component';
import { Proxy } from '../../model/support/proxy';
import { ParameterAssignment } from '../../model/ParameterAssignment';
import { FormGroup } from '@angular/forms';
import { Id } from '../../util/Id';
import { Config } from '../../config/config';
import { GenericForm } from '../core/forms/generic-form.component';
import { CEGModel } from '../../model/CEGModel';
import { Type } from '../../util/Type';
import { TestParameter } from '../../model/TestParameter';
import { TestCase } from '../../model/TestCase';
import { IContentElement } from '../../model/IContentElement';
import { TestSpecification } from '../../model/TestSpecification';
import { Url } from '../../util/Url';
import { SpecmateDataService } from '../../services/specmate-data.service';
import { IContainer } from '../../model/IContainer';
import { Requirement } from '../../model/Requirement';
import { QueryList, ViewChildren, ViewChild, OnInit, Component } from '@angular/core';
import { EditorCommonControlService } from '../../services/editor-common-control.service';
import { SpecmateViewBase } from '../core/views/specmate-view-base';

@Component({
    moduleId: module.id,
    selector: 'test-specification-editor',
    templateUrl: 'test-specification-editor.component.html',
    styleUrls: ['test-specification-editor.component.css']
})
export class TestSpecificationEditor extends SpecmateViewBase {

    public static get modelElementClass(): { className: string } {
        return TestSpecification;
    }

    /** The test specification to be shown */
    private testSpecification: TestSpecification;

    /** All contents of the test specification */
    private contents: IContentElement[];

    /** Input parameters */
    private _inputParameters: IContentElement[];

    /** Output parameters */
    private _outputParameters: IContentElement[];

    /** All parameters */
    private _allParameters: IContentElement[];

    /** Test cases */
    private _testCases: IContentElement[];

    /** The CEG model this test specification is linked to */
    private cegModel: CEGModel;

    /** The requirement this test specification is linked to */
    private requirement: Requirement;

    /** The type of a test case (used for filtering) */
    private testCaseType = TestCase;

    /** The type of a test parameter (used for filtering) */
    private parameterType = TestParameter;

    /** The generic form used in this component */
    @ViewChild(GenericForm)
    private genericForm: GenericForm;

    /** The rows displayed in the editor */
    @ViewChildren(TestCaseRow) testCaseRows: QueryList<TestCaseRow>;

    /** Constructor */
    constructor(
        dataService: SpecmateDataService,
        navigator: NavigatorService,
        route: ActivatedRoute,
        modal: ConfirmationModal,
        editorCommonControlService: EditorCommonControlService
    ) {
        super(dataService, navigator, route, modal, editorCommonControlService);
    }

    onElementResolved(element: IContainer): void {
        this.testSpecification = element as TestSpecification;
        this.readContents();
        this.readParents();
    }

    /** getter for the input parameters */
    get inputParameters(): IContentElement[] {
        return this.contents.filter(c => {
            return Type.is(c, TestParameter) && (<TestParameter>c).type === "INPUT";
        });
    }

    /** getter for the output parameters */
    get outputParameters(): IContentElement[] {
        return this.contents.filter(c => {
            return Type.is(c, TestParameter) && (<TestParameter>c).type === "OUTPUT";
        });
    }

    /** getter for all parameters */
    get allParameters(): IContentElement[] {
        return this.inputParameters.concat(this.outputParameters);
    }

    /** getter for the test cases */
    get testCases(): IContentElement[] {
        return this.contents.filter(c => {
            return Type.is(c, TestCase);
        });
    }

    /** Reads to the contents of the test specification  */
    private readContents(): void {
        if (this.testSpecification) {
            this.dataService.readContents(this.testSpecification.url).then((
                contents: IContainer[]) => {
                this.contents = contents;
            });
        }
    }

    /** Reads the CEG and requirements parents of the test specficiation */
    private readParents(): void {
        if (this.testSpecification) {
            this.dataService.readElement(Url.parent(this.testSpecification.url)).then((
                element: IContainer) => {
                if (Type.is(element, CEGModel)) {
                    this.cegModel = <CEGModel>element;
                    this.readCEGParent();
                } else if (Type.is(element, Requirement)) {
                    this.requirement = <Requirement>element;
                }
            });
        }
    }

    /** Reads the requirement parent of the CEG model */
    private readCEGParent(): void {
        if (this.cegModel) {
            this.dataService.readElement(Url.parent(this.cegModel.url)).then((
                element: IContainer) => {
                if (Type.is(element, Requirement)) {
                    this.requirement = <Requirement>element;
                }
            });
        }
    }

    /** Creates a new test paramter */
    private createNewTestParameter(): TestParameter {
        let id: string = Id.uuid;
        let url: string = Url.build([this.testSpecification.url, id]);
        let parameter: TestParameter = new TestParameter();
        parameter.name = Config.TESTPARAMETER_NAME;
        parameter.id = id;
        parameter.url = url;
        return parameter;
    }

    /** Adds a new input column */
    public addInputColumn(): void {
        this.addColumn("INPUT");
    }

    /** Adds a new output column  */
    public addOutputColumn(): void {
        this.addColumn("OUTPUT");
    }

    /** Adds a new Column. Values for type are 'OUTPUT' and 'INPUT'. */
    public addColumn(type: string): void {
        let compoundId: string = Id.uuid;
        let parameter: TestParameter = this.createNewTestParameter();
        parameter.type = type;
        this.dataService.createElement(parameter, true, compoundId);
        let createParameterAssignmentTask: Promise<void> = Promise.resolve();
        this.testCases.forEach((testCase: IContentElement) => {
            createParameterAssignmentTask = createParameterAssignmentTask.then(() => {
                return this.createNewParameterAssignment(testCase, parameter, compoundId).then(() => {
                    this.testCaseRows.find((testCaseRow: TestCaseRow) => testCaseRow.testCase === testCase).loadContents(true);
                });
            });
        });
        createParameterAssignmentTask.then(() => {});
    }

    /** Creates a new test case */
    private createNewTestCase(): void {
        let id: string = Id.uuid;
        let url: string = Url.build([this.testSpecification.url, id]);
        let testCase: TestCase = new TestCase();
        testCase.name = Config.TESTCASE_NAME;
        testCase.id = id;
        testCase.url = url;
        let compoundId: string = Id.uuid;
        this.dataService.createElement(testCase, true, compoundId).then(() => {
            let createParameterAssignmentTask: Promise<void> = Promise.resolve();
            for(let i = 0; i < this.allParameters.length; i++) {
                createParameterAssignmentTask = createParameterAssignmentTask.then(() => {
                    return this.createNewParameterAssignment(testCase, this.allParameters[i], compoundId);
                });
            }
            return createParameterAssignmentTask.then();
        });
    }

    /** Creates a new Parameter Assignment and stores it virtually. */
    private createNewParameterAssignment(testCase: TestCase, parameter: IContainer, compoundId: string): Promise<void> {
        let parameterAssignment: ParameterAssignment = new ParameterAssignment();
        let id: string = Id.uuid;
        let paramProxy: Proxy = new Proxy();
        paramProxy.url = parameter.url;
        parameterAssignment.parameter = paramProxy;
        parameterAssignment.condition = Config.TESTPARAMETERASSIGNMENT_DEFAULT_CONDITION;
        parameterAssignment.value = Config.TESTPARAMETERASSIGNMENT_DEFAULT_VALUE;
        parameterAssignment.name = Config.TESTPARAMETERASSIGNMENT_NAME;
        parameterAssignment.id = id;
        parameterAssignment.url = Url.build([testCase.url, id]);
        
        return this.dataService.createElement(parameterAssignment, true, compoundId);
    }

    /** Return true if all user inputs are valid  */
    protected get isValid(): boolean {
        if (!this.genericForm) {
            return true;
        }
        return this.genericForm.isValid;
    }
}