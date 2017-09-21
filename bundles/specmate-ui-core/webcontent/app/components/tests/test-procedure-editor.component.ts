import { ConfirmationModal } from '../../services/notification/confirmation-modal.service';
import { NavigatorService } from '../../services/navigation/navigator.service';
import { Id } from '../../util/Id';
import { GenericForm } from '../forms/generic-form.component';
import { Config } from '../../config/config';
import { TestStep } from '../../model/TestStep';
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
import { EditorCommonControlService } from '../../services/common-controls/editor-common-control.service';
import { SpecmateDataService } from '../../services/data/specmate-data.service';
import { OnInit, Component, ViewChild } from '@angular/core';
import { SpecmateViewBase } from '../core/views/specmate-view-base';
import { Sort } from "../../util/Sort";
import { DraggableSupportingViewBase } from "../core/views/draggable-supporting-view-base";
import { IPositionable } from "../../model/IPositionable";


@Component({
    moduleId: module.id,
    selector: 'test-procedure-editor',
    templateUrl: 'test-procedure-editor.component.html',
    styleUrls: ['test-procedure-editor.component.css']
})
export class TestProcedureEditor extends DraggableSupportingViewBase {

    /** The test procedure being edited */
    testProcedure: TestProcedure;

    /** The parent test case*/
    testCase: TestCase;

    get relevantElements(): (IContentElement & IPositionable)[] {
        return this.contents as (IContentElement & IPositionable)[];
    }

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
        return this.allParameters.filter((param: TestParameter) => param.type === 'INPUT');
    }

    /** getter for the output parameters of the parent test specification */
    get outputParameters(): IContentElement[] {
        return this.allParameters.filter((param: TestParameter) => param.type === 'OUTPUT');
    }

    /** getter for all test parameters */
    get allParameters(): IContentElement[] {
        if(!this.testSpecContents) {
            return [];
        }
        return this.testSpecContents.filter((element: IContainer) => Type.is(element, TestParameter));
    }

    private get procedureEditorHeight(): number {
        return Config.GRAPHICAL_EDITOR_HEIGHT;
    }

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
        super.onElementResolved(element);
        this.testProcedure = element as TestProcedure;
        this.readParents();
    }

    /** Reads the parents of this test procedure */
    private readParents(): void {
        if(!this.testProcedure) {
            return;
        }
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
        this.modal.confirmSave().then(() => this.dataService.commit('Save')).then(() => {
            let id = Id.uuid;
            let url: string = Url.build([this.testProcedure.url, id]);
            let position: number = this.contents ? this.contents.length : 0;
            let testStep: TestStep = new TestStep();
            testStep.name = Config.TESTSTEP_NAME;
            testStep.description = Config.TESTSTEP_ACTION;
            testStep.expectedOutcome = Config.TESTSTEP_EXPECTED_OUTCOME;
            testStep.id = id;
            testStep.url = url;
            testStep.position = position;
            testStep.referencedTestParameters = [];
            return this.dataService.createElement(testStep, true, Id.uuid);
        });
    }

    /** Pushes or updates a test procedure to HP ALM */
    pushTestProcedure() : void {
        this.dataService.performOperations(this.testProcedure.url, "syncalm")
        .then((result) => {
                if(result){ 
                    this.modal.open("Procedure exported successfully",false);
                }
            }
        );
    }

    /** Return true if all user inputs are valid  */
    protected get isValid(): boolean {
        if (!this.genericForm) {
            return true;
        }
        return this.genericForm.isValid;
    }
}