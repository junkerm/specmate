import { Component, Input } from '@angular/core';
import { IContainer } from '../../../../../model/IContainer';
import { CEGModel } from '../../../../../model/CEGModel';
import { Process } from '../../../../../model/Process';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { ConfirmationModal } from '../../../../notification/modules/modals/services/confirmation-modal.service';
import { NavigatorService } from '../../../../navigation/modules/navigator/services/navigator.service';
import { TestSpecification } from '../../../../../model/TestSpecification';
import { Id } from '../../../../../util/id';
import { Url } from '../../../../../util/url';
import { Config } from '../../../../../config/config';
import { Type } from '../../../../../util/type';
import { CEGNode } from '../../../../../model/CEGNode';
import { ProcessStart } from '../../../../../model/ProcessStart';
import { ProcessEnd } from '../../../../../model/ProcessEnd';
import { IModelNode } from '../../../../../model/IModelNode';
import { ProcessStep } from '../../../../../model/ProcessStep';
import { ProcessConnection } from '../../../../../model/ProcessConnection';
import { ProcessDecision } from '../../../../../model/ProcessDecision';
import { ValidationService } from '../../../../forms/modules/validation/services/validation.service';
import { ValidationResult } from '../../../../../validation/validation-result';

@Component({
    moduleId: module.id.toString(),
    selector: 'test-specification-generator-button',
    templateUrl: 'test-specification-generator-button.component.html',
    styleUrls: ['test-specification-generator-button.component.css']
})

export class TestSpecificationGeneratorButton {

    private contents: IContainer[];

    private _model: CEGModel | Process;

    private static isCEGNode(element: IContainer): boolean {
        return (Type.is(element, CEGNode));
    }

    public get model(): CEGModel | Process {
        return this._model;
    }

    @Input()
    public set model(model: CEGModel | Process) {
        if (!model) {
            return;
        }
        this._model = model;
        this.dataService.readContents(model.url).then((contents: IContainer[]) => {
            this.contents = contents;
        });
    }

    constructor(private dataService: SpecmateDataService,
        private modal: ConfirmationModal,
        private navigator: NavigatorService,
        private validator: ValidationService) { }

    public generate(): void {
        this.generateTestSpecification(this.model);
    }

    public get enabled(): boolean {
        return this.validator.isValid(this.model, this.contents);
    }

    public get errors(): string[] {
        return this.validator.validate(this.model, this.contents)
            .map((validationResult: ValidationResult) => validationResult.message);
    }

    public generateTestSpecification(model: CEGModel | Process): void {
        if (!this.canCreateTestSpecification(model)) {
            return;
        }
        let testSpec: TestSpecification = new TestSpecification();
        testSpec.id = Id.uuid;
        testSpec.url = Url.build([model.url, testSpec.id]);
        testSpec.name = Config.TESTSPEC_NAME;
        testSpec.description = Config.TESTSPEC_DESCRIPTION;
        this.modal.confirmSave()
            .then(() => this.dataService.createElement(testSpec, true, Id.uuid))
            .then(() => this.dataService.commit('Save'))
            .then(() => this.dataService.performOperations(testSpec.url, 'generateTests'))
            .then(() => this.dataService.readContents(testSpec.url))
            .then(() => this.navigator.navigate(testSpec))
            .catch(() => { });
    }

    public canCreateTestSpecification(model: CEGModel | Process): boolean {
        if (!model) {
            return false;
        }
        return this.validator.isValid(this.model, this.contents);
    }

    public getErrors(model: CEGModel | Process): string[] {
        if (!model) {
            return undefined;
        }
        return this.validator.validate(model)
            .map((validationResult: ValidationResult) => validationResult.message);
    }
}
