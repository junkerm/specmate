import { Component, Input } from '@angular/core';
import { ContentContainerBase } from '../base/contents-container-base';
import { IContainer } from '../../../../../../../model/IContainer';
import { Type } from '../../../../../../../util/type';
import { TestSpecification } from '../../../../../../../model/TestSpecification';
import { TestSpecificationFactory } from '../../../../../../../factory/test-specification-factory';
import { ElementFactoryBase } from '../../../../../../../factory/element-factory-base';
import { Sort } from '../../../../../../../util/sort';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';
import { Id } from '../../../../../../../util/id';
import { TestCase } from '../../../../../../../model/TestCase';
import { ClipboardService } from '../../tool-pallette/services/clipboard-service';
import { Url } from '../../../../../../../util/url';

@Component({
    moduleId: module.id.toString(),
    selector: 'test-specification-container',
    templateUrl: 'test-specification-container.component.html',
    styleUrls: ['test-specification-container.component.css']
})

export class TestSpecificationContainer extends ContentContainerBase<TestSpecification> {

    constructor(dataService: SpecmateDataService,
        navigator: NavigatorService,
        translate: TranslateService,
        modal: ConfirmationModal,
        clipboardService: ClipboardService) {
        super(dataService, navigator, translate, modal, clipboardService);
    }

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
        await this.dataService.readContents(Url.parent(element.url), false);
    }

    protected async readContents(): Promise<void> {
        const contents = await this.dataService
            .performQuery(this.parent.url, 'listRecursive', { class: TestSpecification.className });
        this.contents = Sort.sortArray(contents);
    }
}
