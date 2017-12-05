import {IContainer} from '../../model/IContainer';
import { Component } from "@angular/core";
import { DraggableSupportingViewBase } from "../core/views/draggable-supporting-view-base";
import { TestProcedure } from "../../model/TestProcedure";
import { IContentElement } from "../../model/IContentElement";
import { IPositionable } from "../../model/IPositionable";
import { TestParameter } from '../../model/TestParameter';
import { SpecmateDataService } from '../../services/data/specmate-data.service';
import { NavigatorService } from '../../services/navigation/navigator.service';
import { Type } from '../../util/Type';
import { ConfirmationModal } from '../../services/notification/confirmation-modal.service';
import { ActivatedRoute } from '@angular/router';
import { Url } from '../../util/Url';
import { EditorCommonControlService } from '../../services/common-controls/editor-common-control.service';
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

    public get relevantElements(): (IContentElement & IPositionable)[] {
        return this.contents as (IContentElement & IPositionable)[];
    }

    /** The contents of the parent test specification */
    private testSpecContents: IContainer[];

    /** getter for the input parameters of the parent test specification */
    public get inputParameters(): IContentElement[] {
        return this.allParameters.filter((param: TestParameter) => param.type === 'INPUT');
    }

    /** getter for the output parameters of the parent test specification */
    public get outputParameters(): IContentElement[] {
        return this.allParameters.filter((param: TestParameter) => param.type === 'OUTPUT');
    }

    /** getter for all test parameters */
    private get allParameters(): IContentElement[] {
        if(!this.testSpecContents) {
            return [];
        }
        return this.testSpecContents.filter((element: IContainer) => Type.is(element, TestParameter));
    }

    /** Constructor */
    constructor(dataService: SpecmateDataService, navigator: NavigatorService, route: ActivatedRoute, modal: ConfirmationModal, editorCommonControlService: EditorCommonControlService, dragulaService: DragulaService) {
        super(dataService, navigator, route, modal, editorCommonControlService, dragulaService);
    }

    public onElementResolved(element: IContainer): Promise<void> {
        return super.onElementResolved(element)
            .then(() => Type.is(element, TestProcedure) ? Promise.resolve() : Promise.reject('Not a test procedure'))
            .then(() => this.readParentTestSpec(element as TestProcedure))
            .then(() => this.testProcedure = element as TestProcedure)
            .then(() => Promise.resolve());
    }

    /** Reads the parent test specification */
    private readParentTestSpec(testProcedure: TestProcedure): Promise<void> {
        let testSpecificationUrl: string = Url.parent(testProcedure.url);
        return this.dataService.readContents(testSpecificationUrl)
            .then((contents: IContainer[]) => this.testSpecContents = contents)
            .then((contents: IContainer[]) => this.sanitizeContentPositions(true))
            .then(() => Promise.resolve());
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