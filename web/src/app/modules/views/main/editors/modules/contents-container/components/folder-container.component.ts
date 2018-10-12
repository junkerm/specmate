import { Component, Input } from '@angular/core';
import { ContentContainerBase } from '../base/contents-container-base';
import { IContainer } from '../../../../../../../model/IContainer';
import { Type } from '../../../../../../../util/type';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';
import { Id } from '../../../../../../../util/id';
import { Folder } from '../../../../../../../model/Folder';
import { FolderFactory } from '../../../../../../../factory/folder-factory';

@Component({
    moduleId: module.id.toString(),
    selector: 'folder-container',
    templateUrl: 'folder-container.component.html',
    styleUrls: ['folder-container.component.css']
})

export class FolderContainer extends ContentContainerBase<Folder> {

    constructor(dataService: SpecmateDataService, navigator: NavigatorService, translate: TranslateService, modal: ConfirmationModal) {
        super(dataService, navigator, translate, modal);
    }

    private _parent: IContainer;

    protected condition = (element: IContainer) => Type.is(element, Folder);

    protected get parent(): IContainer {
        return this._parent;
    }

    @Input()
    protected set parent(parent: IContainer) {
        this._parent = parent;
        this.readContents();
    }

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
