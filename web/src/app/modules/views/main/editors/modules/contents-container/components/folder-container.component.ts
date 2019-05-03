import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { FolderFactory } from '../../../../../../../factory/folder-factory';
import { Folder } from '../../../../../../../model/Folder';
import { IContainer } from '../../../../../../../model/IContainer';
import { Id } from '../../../../../../../util/id';
import { Type } from '../../../../../../../util/type';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';
import { ClipboardService } from '../../tool-pallette/services/clipboard-service';
import { ContentContainerBase } from '../base/contents-container-base';

@Component({
    moduleId: module.id.toString(),
    selector: 'folder-container',
    templateUrl: 'folder-container.component.html',
    styleUrls: ['folder-container.component.css']
})

export class FolderContainer extends ContentContainerBase<Folder> {

    constructor(dataService: SpecmateDataService,
        navigator: NavigatorService,
        translate: TranslateService,
        modal: ConfirmationModal,
        clipboardService: ClipboardService) {
        super(dataService, navigator, translate, modal, clipboardService);
    }

    protected condition = (element: IContainer) => Type.is(element, Folder);

    public async createElement(name: string): Promise<Folder> {
        const factory = new FolderFactory(this.dataService);
        const element = factory.create(this.parent, true, Id.uuid, name);
        return element;
    }

    public async delete(element: Folder): Promise<void> {
        const message = this.translate.instant('doYouReallyWantToDeleteFolder', { name: element.name });
        await super.delete(element, message);
    }
}
