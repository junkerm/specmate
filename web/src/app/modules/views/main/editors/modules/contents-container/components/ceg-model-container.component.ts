import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { CEGModelFactory } from '../../../../../../../factory/ceg-model-factory';
import { ModelFactoryBase } from '../../../../../../../factory/model-factory-base';
import { CEGModel } from '../../../../../../../model/CEGModel';
import { IContainer } from '../../../../../../../model/IContainer';
import { Id } from '../../../../../../../util/id';
import { Type } from '../../../../../../../util/type';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';
import { AdditionalInformationService } from '../../../../../side/modules/links-actions/services/additional-information.service';
import { ClipboardService } from '../../tool-pallette/services/clipboard-service';
import { TestSpecificationContentContainerBase } from '../base/testspecification-generatable-content-container-base';
import { ContentsContainerService } from '../services/contents-container.service';

@Component({
    moduleId: module.id.toString(),
    selector: 'ceg-model-container',
    templateUrl: 'ceg-model-container.component.html',
    styleUrls: ['ceg-model-container.component.css']
})

export class CEGModelContainer extends TestSpecificationContentContainerBase<CEGModel> {

    constructor(dataService: SpecmateDataService,
        navigator: NavigatorService,
        translate: TranslateService,
        modal: ConfirmationModal,
        clipboardService: ClipboardService,
        private contentService: ContentsContainerService,
        additionalInformationService: AdditionalInformationService) {
        super(dataService, navigator, translate, modal, clipboardService, additionalInformationService);
    }

    protected condition = (element: IContainer) => Type.is(element, CEGModel);

    public async createElement(name: string): Promise<CEGModel> {
        let factory: ModelFactoryBase = new CEGModelFactory(this.dataService);
        const element = await factory.create(this.parent, true, Id.uuid, name);
        return element as CEGModel;
    }

    public async delete(element: CEGModel,
        message: string = this.translate.instant('doYouReallyWantToDelete', { name: element.name })): Promise<void> {
        try {
            await this.modal.openOkCancel('ConfirmationRequired', message);
            await this.dataService.deleteElement(element.url, false, Id.uuid);
            await this.dataService.commit(this.translate.instant('delete'));
            await this.readContents();
            this.contentService.isDeleted();
        } catch (e) { }
    }
}
