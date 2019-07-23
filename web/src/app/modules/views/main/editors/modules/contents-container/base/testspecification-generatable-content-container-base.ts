import { TranslateService } from '@ngx-translate/core';
import { IContainer } from '../../../../../../../model/IContainer';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';
import { AdditionalInformationService } from '../../../../../side/modules/links-actions/services/additional-information.service';
import { ClipboardService } from '../../tool-pallette/services/clipboard-service';
import { ContentContainerBase } from './contents-container-base';
import { ContentsContainerService } from '../services/content-container.service';
import { Id } from '../../../../../../../util/id';

export abstract class TestSpecificationContentContainerBase<T extends IContainer> extends ContentContainerBase<T> {

    constructor(
        dataService: SpecmateDataService,
        navigator: NavigatorService,
        translate: TranslateService,
        modal: ConfirmationModal,
        clipboardService: ClipboardService,
        private contentService: ContentsContainerService,
        protected additionalInformationService: AdditionalInformationService) {
        super(dataService, navigator, translate, modal, clipboardService);
    }

    public get canGenerateTestSpecification(): boolean {
        return this.additionalInformationService.canGenerateTestSpecifications;
    }

    public async delete(element: T,
        message: string = this.translate.instant('doYouReallyWantToDelete', { name: element.name })): Promise<void> {
        await super.delete(element, message);
        await this.contentService.isDeleted();
    }

}
