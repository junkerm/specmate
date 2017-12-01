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
import { TestParameterFactory } from '../../factory/test-parameter-factory';
import { TestInputParameterFactory } from '../../factory/test-input-parameter-factory';
import { TestOutputParameterFactory } from '../../factory/test-output-parameter-factory';
import { TestCaseFactory } from '../../factory/test-case-factory';

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

    public onElementResolved(element: IContainer): void {
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

    /** Adds a new test case (row) */
    public addTestCaseRow(): void {
        let factory: TestCaseFactory = new TestCaseFactory(this.dataService, true);
        factory.create(this.testSpecification, false);
    }

    /** Adds a new input column */
    public addInputColumn(): void {
        let factory: TestParameterFactory = new TestInputParameterFactory(this.dataService);
        factory.create(this.testSpecification, false);
    }

    /** Adds a new output column  */
    public addOutputColumn(): void {
        let factory: TestParameterFactory = new TestOutputParameterFactory(this.dataService);
        factory.create(this.testSpecification, false);
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