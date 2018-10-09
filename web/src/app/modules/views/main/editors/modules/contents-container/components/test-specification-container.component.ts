import { Component, Input } from '@angular/core';
import { ContentContainerBase } from '../base/contents-container-base';
import { IContainer } from '../../../../../../../model/IContainer';
import { ModelFactoryBase } from '../../../../../../../factory/model-factory-base';
import { CEGModelFactory } from '../../../../../../../factory/ceg-model-factory';
import { Type } from '../../../../../../../util/type';
import { Process } from '../../../../../../../model/Process';
import { TestSpecification } from '../../../../../../../model/TestSpecification';
import { TestSpecificationFactory } from '../../../../../../../factory/test-specification-factory';
import { ElementFactoryBase } from '../../../../../../../factory/element-factory-base';
import { Sort } from '../../../../../../../util/sort';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';
import { UUID } from 'angular2-uuid';
import { Id } from '../../../../../../../util/id';
import { TestCase } from '../../../../../../../model/TestCase';

@Component({
    moduleId: module.id.toString(),
    selector: 'test-specification-container',
    templateUrl: 'test-specification-container.component.html',
    styleUrls: ['test-specification-container.component.css']
})

export class TestSpecificationContainer extends ContentContainerBase<TestSpecification> {

    constructor(dataService: SpecmateDataService, navigator: NavigatorService, translate: TranslateService, modal: ConfirmationModal) {
        super(dataService, navigator, translate, modal);
    }

    private _parent: IContainer;

    protected get parent(): IContainer {
        return this._parent;
    }

    @Input()
    protected set parent(parent: IContainer) {
        this._parent = parent;
        this.readContents();
    }

    public newName: string;

    protected condition = (element: IContainer) => Type.is(element, TestSpecification);

    public async createElement(name: string): Promise<TestSpecification> {
        let factory: ElementFactoryBase<TestSpecification> = new TestSpecificationFactory(this.dataService);
        return await factory.create(this.parent, true, Id.uuid, name);
    }

    public async delete(element: IContainer): Promise<void> {
        const contents = await this.dataService.readContents(element.url, true);
        const numberOfChlidren = contents.filter(element => Type.is(element, TestCase)).length;
        const additionalMessage = this.translate.instant('attentionNumberOfTestCasesWillBeDeleted', {num: numberOfChlidren});
        const baseMessage = this.translate.instant('doYouReallyWantToDelete', { name: element.name });
        await super.delete(element, baseMessage + ' ' + additionalMessage);
    }

    protected async readContents(): Promise<void> {
        const contents = await this.dataService
            .performQuery(this.parent.url, 'listRecursive', { class: TestSpecification.className });
        this.contents = Sort.sortArray(contents);
    }
}
