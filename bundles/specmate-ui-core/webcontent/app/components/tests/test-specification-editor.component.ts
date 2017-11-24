import { ConfirmationModal } from '../../services/notification/confirmation-modal.service';
import { NavigatorService } from '../../services/navigation/navigator.service';
import { ActivatedRoute, Params } from '@angular/router';
import { TestCaseRow } from './test-case-row.component';
import { Proxy } from '../../model/support/proxy';
import { ParameterAssignment } from '../../model/ParameterAssignment';
import { FormGroup } from '@angular/forms';
import { Id } from '../../util/Id';
import { Config } from '../../config/config';
import { GenericForm } from '../forms/generic-form.component';
import { Type } from '../../util/Type';
import { TestParameter } from '../../model/TestParameter';
import { TestCase } from '../../model/TestCase';
import { IContentElement } from '../../model/IContentElement';
import { TestSpecification } from '../../model/TestSpecification';
import { Url } from '../../util/Url';
import { SpecmateDataService } from '../../services/data/specmate-data.service';
import { IContainer } from '../../model/IContainer';
import { Requirement } from '../../model/Requirement';
import { QueryList, ViewChildren, ViewChild, OnInit, Component } from '@angular/core';
import { EditorCommonControlService } from '../../services/common-controls/editor-common-control.service';
import { SpecmateViewBase } from '../core/views/specmate-view-base';
import { Sort } from "../../util/Sort";
import { DraggableSupportingViewBase } from "../core/views/draggable-supporting-view-base";
import { IPositionable } from "../../model/IPositionable";
import { Process } from '../../model/Process';
import { CEGModel } from '../../model/CEGModel';
import { DragulaService } from 'ng2-dragula';

@Component({
    moduleId: module.id,
    selector: 'test-specification-editor',
    templateUrl: 'test-specification-editor.component.html',
    styleUrls: ['test-specification-editor.component.css']
})
export class TestSpecificationEditor extends DraggableSupportingViewBase {

    /** The test specification to be shown */
    private testSpecification: TestSpecification;

    protected get relevantElements(): (IContentElement & IPositionable)[] {
        return this.contents.filter((element: IContentElement & IPositionable) => Type.is(element, TestCase)) as TestCase[];
    }

    /** Input parameters */
    private _inputParameters: IContentElement[];

    /** Output parameters */
    private _outputParameters: IContentElement[];

    /** All parameters */
    private _allParameters: IContentElement[];

    /** The CEG model this test specification is linked to */
    private parentModel: IContainer;

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

    private get specificationEditorHeight(): number {
        return Config.GRAPHICAL_EDITOR_HEIGHT;
    }

    /** Constructor */
    constructor(
        dataService: SpecmateDataService,
        navigator: NavigatorService,
        route: ActivatedRoute,
        modal: ConfirmationModal,
        editorCommonControlService: EditorCommonControlService,
        dragulaService: DragulaService
    ) {
        super(dataService, navigator, route, modal, editorCommonControlService, dragulaService);
    }

    onElementResolved(element: IContainer): void {
        super.onElementResolved(element);
        this.testSpecification = element as TestSpecification;
        this.readParents();
    }

    /** getter for the input parameters */
    private get inputParameters(): TestParameter[] {
        return this.contents.filter(c => {
            return Type.is(c, TestParameter) && (<TestParameter>c).type === "INPUT";
        }) as TestParameter[];
    }

    /** getter for the output parameters */
    private get outputParameters(): TestParameter[] {
        return this.contents.filter(c => {
            return Type.is(c, TestParameter) && (<TestParameter>c).type === "OUTPUT";
        }) as TestParameter[];
    }

    /** getter for all parameters */
    private get allParameters(): TestParameter[] {
        return this.inputParameters.concat(this.outputParameters);
    }

    /** Reads the CEG and requirements parents of the test specficiation */
    private readParents(): void {
        if (this.testSpecification) {
            this.dataService.readElement(Url.parent(this.testSpecification.url)).then((
                element: IContainer) => {
                if (Type.is(element, CEGModel) || Type.is(element, Process)) {
                    this.parentModel = element;
                    this.readModelParent();
                } else if (Type.is(element, Requirement)) {
                    this.requirement = <Requirement>element;
                }
            });
        }
    }

    /** Reads the requirement parent of the CEG model */
    private readModelParent(): void {
        if (this.parentModel) {
            this.dataService.readElement(Url.parent(this.parentModel.url)).then((
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
        parameter.assignments = [];
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
        this.relevantElements.forEach((testCase: IContentElement) => {
            createParameterAssignmentTask = createParameterAssignmentTask.then(() => {
                return this.createNewParameterAssignment(testCase as TestCase, parameter, compoundId).then(() => {
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
        testCase.position = this.relevantElements.length;
        let compoundId: string = Id.uuid;
        this.dataService.createElement(testCase, true, compoundId).then(() => {
            let createParameterAssignmentTask: Promise<void> = Promise.resolve();
            for(let i = 0; i < this.allParameters.length; i++) {
                createParameterAssignmentTask = createParameterAssignmentTask.then(() => {
                    return this.createNewParameterAssignment(testCase, this.allParameters[i], compoundId);
                });
            }
        });
    }

    /** Creates a new Parameter Assignment and stores it virtually. */
    private createNewParameterAssignment(testCase: TestCase, parameter: TestParameter, compoundId: string): Promise<void> {
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
        let assignmentProxy = new Proxy();
        assignmentProxy.url = parameterAssignment.url;
        parameter.assignments.push(assignmentProxy);
        
        return this.dataService.createElement(parameterAssignment, true, compoundId);
    }

    /** Returns true if the element is a TestCase - Important in UI. */
    public isTestCase(element: IContainer): boolean {
        return Type.is(element, TestCase);
    }

    /** Return true if all user inputs are valid  */
    protected get isValid(): boolean {
        if (!this.genericForm) {
            return true;
        }
        return this.genericForm.isValid;
    }
}