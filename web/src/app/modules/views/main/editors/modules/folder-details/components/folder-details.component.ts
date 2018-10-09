import { Component } from '@angular/core';
import { SpecmateViewBase } from '../../../base/specmate-view-base';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';
import { TranslateService } from '@ngx-translate/core';
import { IContainer } from '../../../../../../../model/IContainer';
import { Folder } from '../../../../../../../model/Folder';
import { ViewControllerService } from '../../../../../controller/modules/view-controller/services/view-controller.service';

@Component({
    moduleId: module.id.toString(),
    selector: 'folder-details',
    templateUrl: 'folder-details.component.html',
    styleUrls: ['folder-details.component.css']
})

export class FolderDetails extends SpecmateViewBase {

    /** Constructor */
    constructor(
        dataService: SpecmateDataService,
        navigator: NavigatorService,
        route: ActivatedRoute,
        modal: ConfirmationModal,
        translate: TranslateService,
        viewController: ViewControllerService) {
        super(dataService, navigator, route, modal, translate);
        this.viewController = viewController;
    }

    private viewController: ViewControllerService;

    public folder: Folder;
    public contents: IContainer[];

    protected onElementResolved(element: IContainer): void {
        // The timeout create a macrotask to prevent unchecked update errors in Angular.

        setTimeout(() => {

            this.folder = element as Folder;
            this.dataService.readContents(this.folder.url)
                .then((contents: IContainer[]) => this.contents = contents);
            //               .then(() => this.updateTransitiveContent());
        });
    }

    protected get isValid(): boolean {
        return true;
    }

    public get showFolderProperties(): boolean {
        return !this.viewController.areFolderPropertiesEditable;
    }
}
