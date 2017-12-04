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
import { Process } from '../../model/Process';
import { DragulaService } from 'ng2-dragula';
import { TestStepFactory } from '../../factory/test-step-factory';

@Component({
    moduleId: module.id,
    selector: 'test-procedure-editor',
    templateUrl: 'test-procedure-editor.component.html',
    styleUrls: ['test-procedure-editor.component.css']
})
export class TestProcedureEditor extends DraggableSupportingViewBase {

    /** The test procedure being edited */
    public testProcedure: TestProcedure;

    get relevantElements(): (IContentElement & IPositionable)[] {
        return this.contents as (IContentElement & IPositionable)[];
    }

    /** The contents of the parent test specification */
    private testSpecContents: IContainer[];

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

    /** Constructor */
    constructor(dataService: SpecmateDataService, navigator: NavigatorService, route: ActivatedRoute, modal: ConfirmationModal, editorCommonControlService: EditorCommonControlService, dragulaService: DragulaService) {
        super(dataService, navigator, route, modal, editorCommonControlService, dragulaService);
    }

    public onElementResolved(element: IContainer): void {
        super.onElementResolved(element);
        this.testProcedure = element as TestProcedure;
        this.readParentTestSpec();
    }

    /** Reads the parent test specification */
    private readParentTestSpec(): void {
        if (this.testProcedure) {
            let testSpecificationUrl: string = Url.parent(this.testProcedure.url);
            this.dataService.readContents(testSpecificationUrl).then((elements: IContainer[]) => this.testSpecContents = elements);
        }
    }

    /** Creates a new test case */
    private createNewTestStep() {
        let factory: TestStepFactory = new TestStepFactory(this.dataService);
        factory.create(this.testProcedure, false);
    }

    /** Return true if all user inputs are valid  */
    protected get isValid(): boolean {
        return true;
    }
}