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

    public onElementResolved(element: IContainer): Promise<void> {
        return super.onElementResolved(element)
            .then(() => Type.is(element, TestSpecification) ? Promise.resolve() : Promise.reject('Not a test specification'))
            .then(() => this.testSpecification = element as TestSpecification)
            .then(() => Promise.resolve());
    }

    /** getter for the input parameters */
    private get inputParameters(): TestParameter[] {
        return this.testParameters.filter((testParameter: TestParameter) => testParameter.type === 'INPUT');
    }

    /** getter for the output parameters */
    private get outputParameters(): TestParameter[] {
        return this.testParameters.filter((testParameter: TestParameter) => testParameter.type === 'OUTPUT');
    }

    /** getter for all parameters */
    private get testParameters(): TestParameter[] {
        return this.contents.filter((element: IContainer) => Type.is(element, TestParameter)).map((element: IContainer) => element as TestParameter);
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
        return true;
    }
}