import { Component, Input } from '@angular/core';
import { ContentContainerBase } from '../base/contents-container-base';
import { IContainer } from '../../../../../../../model/IContainer';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';
import { Requirement } from '../../../../../../../model/Requirement';
import { Sort } from '../../../../../../../util/sort';
import { ClipboardService } from '../../tool-pallette/services/clipboard-service';

@Component({
    moduleId: module.id.toString(),
    selector: 'related-requirements-container',
    templateUrl: 'related-requirements-container.component.html',
    styleUrls: ['related-requirements-container.component.css']
})

export class RelatedRequirementsContainer extends ContentContainerBase<Requirement> {

    constructor(dataService: SpecmateDataService,
        navigator: NavigatorService,
        translate: TranslateService,
        modal: ConfirmationModal,
        clipboardService: ClipboardService) {
        super(dataService, navigator, translate, modal, clipboardService);
    }

    protected condition = (element: IContainer) => true;

    public createElement(name: string): Promise<Requirement> {
        throw new Error('Method not implemented.');
    }

    public async readContents(): Promise<void> {
        const contents = await this.dataService.performQuery(this.parent.url, 'related', { });
        this.contents = Sort.sortArray(contents);
    }

}
