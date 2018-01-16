import { Component } from '@angular/core';
import { ViewControllerService } from '../../views/controller/modules/view-controller/services/view-controller.service';
import { ErrorNotificationModalService } from '../../notification/modules/modals/services/error-notification-modal.service';
import { TranslateService } from '@ngx-translate/core';

/**
 * This is the Specmate main component
 */
@Component({
    selector: 'specmate',
    moduleId: module.id.toString(),
    templateUrl: 'specmate.component.html',
    styleUrls: ['specmate.component.css']
})

export class SpecmateComponent {

    private _leftWidth = 20;
    private _rightWidth = 20;

    public get loggingShown(): boolean {
        return this.viewController.loggingOutputShown;
    }

    public get explorerShown(): boolean {
        return this.viewController.projectExplorerShown;
    }

    public get propertiesShown(): boolean {
        return this.viewController.propertiesShown;
    }

    public get linksActionsShown(): boolean {
        return this.viewController.linksActionsShown;
    }

    private get rightShown(): boolean {
        return this.propertiesShown || this.linksActionsShown;
    }

    private get leftShown(): boolean {
        return !this.viewController.isEditorMaximized && this.viewController.projectExplorerShown;
    }

    public get leftWidth(): number {
        return this.leftShown ? this._leftWidth : 0;
    }

    public set leftWidth(width: number) {
        this._leftWidth = width;
    }

    public get midWidth(): number {
        return 100 - (this.rightWidth + this.leftWidth);
    }

    public get rightWidth(): number {
        return this.rightShown ? this._rightWidth : 0;
    }

    public set rightWidth(width: number) {
        this._rightWidth = width;
    }

    constructor(private viewController: ViewControllerService, private errorNotificationService: ErrorNotificationModalService) { }
}
