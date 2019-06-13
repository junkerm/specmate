import { TranslateService } from '@ngx-translate/core';
import { IContainer } from '../../../../../../../model/IContainer';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';
import { AdditionalInformationService } from '../../../../../side/modules/links-actions/services/additional-information.service';
import { ClipboardService } from '../../tool-pallette/services/clipboard-service';
import { ContentContainerBase } from './contents-container-base';

export abstract class TestSpecificationContentContainerBase<T extends IContainer> extends ContentContainerBase<T> {

    constructor(
        dataService: SpecmateDataService,
        navigator: NavigatorService,
        translate: TranslateService,
        modal: ConfirmationModal,
        clipboardService: ClipboardService,
        protected additionalInformationService: AdditionalInformationService) {
        super(dataService, navigator, translate, modal, clipboardService);
    }

    public get canGenerateTestSpecification(): boolean {
        return this.additionalInformationService.canGenerateTestSpecifications;
    }

}
