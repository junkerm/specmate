import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { DragulaService } from 'ng2-dragula';
import { TestCaseFactory } from '../../../../../../../factory/test-case-factory';
import { TestInputParameterFactory } from '../../../../../../../factory/test-input-parameter-factory';
import { TestOutputParameterFactory } from '../../../../../../../factory/test-output-parameter-factory';
import { TestParameterFactory } from '../../../../../../../factory/test-parameter-factory';
import { IContainer } from '../../../../../../../model/IContainer';
import { IContentElement } from '../../../../../../../model/IContentElement';
import { IPositionable } from '../../../../../../../model/IPositionable';
import { TestCase } from '../../../../../../../model/TestCase';
import { TestParameter } from '../../../../../../../model/TestParameter';
import { TestSpecification } from '../../../../../../../model/TestSpecification';
import { Type } from '../../../../../../../util/type';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';
import { DraggableSupportingViewBase } from '../../../base/draggable-supporting-view-base';

@Component({
    moduleId: module.id.toString(),
    selector: 'test-specification-editor',
    templateUrl: 'test-specification-editor.component.html',
    styleUrls: ['test-specification-editor.component.css']
})
export class TestSpecificationEditor extends DraggableSupportingViewBase {

    /** The test specification to be shown */
    public testSpecification: TestSpecification;

    protected get relevantElements(): (IContentElement & IPositionable)[] {
        return this.contents.filter((element: IContentElement & IPositionable) => Type.is(element, TestCase)) as TestCase[];
    }

    /** Constructor */
    constructor(
        dataService: SpecmateDataService,
        navigator: NavigatorService,
        route: ActivatedRoute,
        modal: ConfirmationModal,
        dragulaService: DragulaService,
        translate: TranslateService
    ) {
        super(dataService, navigator, route, modal, dragulaService, translate);
    }

    public onElementResolved(element: IContainer): Promise<void> {
        return super.onElementResolved(element)
            .then(() => Type.is(element, TestSpecification) ? Promise.resolve() : Promise.reject('Not a test specification'))
            .then(() => this.testSpecification = element as TestSpecification)
            .then(() => Promise.resolve());
    }

    /** getter for the input parameters */
    public get inputParameters(): TestParameter[] {
        return this.testParameters.filter((testParameter: TestParameter) => testParameter.type === 'INPUT');
    }

    /** getter for the output parameters */
    public get outputParameters(): TestParameter[] {
        return this.testParameters.filter((testParameter: TestParameter) => testParameter.type === 'OUTPUT');
    }

    /** getter for all parameters */
    private get testParameters(): TestParameter[] {
        return this.contents
            .filter((element: IContainer) => Type.is(element, TestParameter))
            .map((element: IContainer) => element as TestParameter);
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
