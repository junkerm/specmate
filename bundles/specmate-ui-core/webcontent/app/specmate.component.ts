import { Component } from '@angular/core';
import { ErrorNotificationModalService } from './services/notification/error-notification-modal.service';
import { ViewControllerService } from './services/view/view-controller.service';

/**
 * This is the Specmate main component
 */
@Component({
    selector: 'specmate',
    moduleId: module.id,
    templateUrl: 'specmate.component.html',
    styleUrls: ['specmate.component.css']
})

export class SpecmateComponent {

    public get loggingShown(): boolean {
        return this.viewController.loggingOutputShown;
    }

    public get explorerShown(): boolean {
        return this.viewController.projectExplorerShown;
    }

    constructor(private viewController: ViewControllerService, private errorNotificationService: ErrorNotificationModalService) { }
}