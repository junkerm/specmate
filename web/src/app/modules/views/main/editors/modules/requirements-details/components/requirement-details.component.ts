import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { CEGModelFactory } from '../../../../../../../factory/ceg-model-factory';
import { ModelFactoryBase } from '../../../../../../../factory/model-factory-base';
import { ProcessFactory } from '../../../../../../../factory/process-factory';
import { TestSpecificationFactory } from '../../../../../../../factory/test-specification-factory';
import { CEGModel } from '../../../../../../../model/CEGModel';
import { IContainer } from '../../../../../../../model/IContainer';
import { IContentElement } from '../../../../../../../model/IContentElement';
import { Process } from '../../../../../../../model/Process';
import { Requirement } from '../../../../../../../model/Requirement';
import { TestCase } from '../../../../../../../model/TestCase';
import { TestSpecification } from '../../../../../../../model/TestSpecification';
import { Id } from '../../../../../../../util/id';
import { Sort } from '../../../../../../../util/sort';
import { Type } from '../../../../../../../util/type';
import { Url } from '../../../../../../../util/url';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';
import { SpecmateViewBase } from '../../../base/specmate-view-base';

@Component({
    moduleId: module.id.toString(),
    selector: 'requirements-details',
    templateUrl: 'requirement-details.component.html',
    styleUrls: ['requirement-details.component.css']
})

export class RequirementsDetails extends SpecmateViewBase {


    cegModelType = CEGModel;
    processModelType = Process;
    requirement: Requirement;
    contents: IContainer[];
    testSpecifications: IContainer[];
    relatedRequirements: IContainer[];

    /** Constructor */
    constructor(
        dataService: SpecmateDataService,
        navigator: NavigatorService,
        route: ActivatedRoute,
        modal: ConfirmationModal,
        translate: TranslateService) {
        super(dataService, navigator, route, modal, translate);
    }

    protected onElementResolved(element: IContainer): void {
        this.requirement = element as Requirement;
        this.dataService.readContents(this.requirement.url).then((contents: IContainer[]) => this.contents = contents);
        this.readTestSpecifications();
        this.readRelatedRequirements();
    }

    private readTestSpecifications(): void {
        this.dataService.performQuery(this.requirement.url, 'listRecursive', { class: TestSpecification.className })
            .then((testSpecifications: TestSpecification[]) => this.testSpecifications = Sort.sortArray(testSpecifications));
    }

    private readRelatedRequirements(): void {
        this.dataService.performQuery(this.requirement.url, 'related', { })
            .then((related: IContainer[]) => this.relatedRequirements = Sort.sortArray(related));
    }

    public delete(element: IContentElement): void {
        let msgCommon: string = this.translate.instant('doYouReallyWantToDelete', {name: element.name});
        let msgPromise: Promise<string>;

        if (Type.is(element, TestSpecification)) {
            msgPromise = this.dataService.readContents(element.url, true)
            .then((contents: IContainer[]) => contents.filter((elem: IContainer) => Type.is(elem, TestCase)).length)
            .then((numberOfChildren: number) => msgCommon + ' ' +
                this.translate.instant('attentionNumberOfTestCasesWillBeDeleted', {num: numberOfChildren}));
        } else {
            msgPromise = Promise.resolve(msgCommon);
        }
        msgPromise.then((msg: string) => this.modal.openOkCancel('ConfirmationRequired', msg)
            .then(() => this.dataService.deleteElement(element.url, true, Id.uuid))
            .then(() => this.dataService.commit(this.translate.instant('delete')))
            .then(() => this.dataService.readContents(this.requirement.url, true))
            .then((contents: IContainer[]) => this.contents = contents)
            .then(() => this.readTestSpecifications())
            .catch(() => {}));
    }

    public duplicate(element: IContentElement): void {
        this.dataService.performOperations(element.url, 'duplicate')
            .then(() => this.dataService.commit(this.translate.instant('duplicate')))
            .then(() => this.dataService.readContents(this.requirement.url, false))
            .then((contents: IContainer[]) => this.contents = contents)
            .then(() => this.readTestSpecifications())
            .catch(() => {});
    }

    public createModel(): void {
        let factory: ModelFactoryBase = new CEGModelFactory(this.dataService);
        factory.create(this.requirement, true).then((element: IContainer) => this.navigator.navigate(element));
    }

    public createProcess(): void {
        let factory: ModelFactoryBase = new ProcessFactory(this.dataService);
        factory.create(this.requirement, true).then((element: IContainer) => this.navigator.navigate(element));
    }

    private createElement(element: IContainer, name: string, description: string): void {
        if (!this.contents) {
            return;
        }

        let factory: ModelFactoryBase;

        element.id = Id.uuid;
        element.url = Url.build([this.requirement.url, element.id]);
        element.name = name;
        element.description = description;

        this.dataService.createElement(element, true, Id.uuid)
            .then(() => this.dataService.commit(this.translate.instant('create')))
            .then(() => this.dataService.readContents(Url.parent(element.url), true))
            .then((contents: IContainer[]) => this.contents = contents)
            .then(() => this.navigator.navigate(element));
    }

    public createTestSpecification(): void {
        let factory: TestSpecificationFactory = new TestSpecificationFactory(this.dataService);
        factory.create(this.requirement, true).then((testSpec: TestSpecification) => this.navigator.navigate(testSpec));
    }

    public get cegModels(): CEGModel[] {
        if (!this.contents) {
            return [];
        }
        return <CEGModel[]>this.contents.filter((element: IContainer) => Type.is(element, this.cegModelType));
    }

    public get processModels(): Process[] {
        if (!this.contents) {
            return [];
        }
        return this.contents.filter((element: IContainer) => Type.is(element, this.processModelType));
    }

    protected get isValid(): boolean {
        return true;
    }
}
