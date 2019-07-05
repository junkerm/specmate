import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { ModelFactoryBase } from '../../../../../../../factory/model-factory-base';
import { ProcessFactory } from '../../../../../../../factory/process-factory';
import { IContainer } from '../../../../../../../model/IContainer';
import { Process } from '../../../../../../../model/Process';
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
    selector: 'process-model-container',
    templateUrl: 'process-model-container.component.html',
    styleUrls: ['process-model-container.component.css']
})

export class ProcessModelContainer extends TestSpecificationContentContainerBase<Process> {

    constructor(dataService: SpecmateDataService,
        navigator: NavigatorService,
        translate: TranslateService,
        modal: ConfirmationModal,
        additionalInformationService: AdditionalInformationService,
        private contentService: ContentsContainerService,
        clipboardService: ClipboardService) {
        super(dataService, navigator, translate, modal, clipboardService, additionalInformationService);
    }

    protected condition = (element: IContainer) => Type.is(element, Process);

    public async createElement(name: string): Promise<Process> {
        let factory: ModelFactoryBase = new ProcessFactory(this.dataService);
        const element = await factory.create(this.parent, true, Id.uuid, name);
        return element as Process;
    }

    public async delete(element: Process,
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
