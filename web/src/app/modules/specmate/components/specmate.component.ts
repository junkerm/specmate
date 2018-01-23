import { Component, ComponentFactoryResolver, ViewContainerRef, ViewChild, AfterViewInit } from '@angular/core';
import { ViewControllerService } from '../../views/controller/modules/view-controller/services/view-controller.service';
import { ErrorNotificationModalService } from '../../notification/modules/modals/services/error-notification-modal.service';
import { TranslateService } from '@ngx-translate/core';
import { ViewPane } from '../../views/base/view-pane';
import { PaneItem } from '../../views/base/pane-item';

/**
 * This is the Specmate main component
 */
@Component({
    selector: 'specmate',
    moduleId: module.id.toString(),
    templateUrl: 'specmate.component.html',
    styleUrls: ['specmate.component.css']
})
export class SpecmateComponent implements AfterViewInit {

    @ViewChild('right', { read: ViewContainerRef })
    right: ViewContainerRef;

    private _leftWidth = 20;
    private _rightWidth = 20;

    public ngAfterViewInit() {
        this.loadRightComponents();
    }

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

    public get tracingLinksShown(): boolean {
        return this.viewController.tracingLinksShown;
    }

    public get rightShown(): boolean {
        return this.propertiesShown || this.linksActionsShown || this.tracingLinksShown;
    }

    public get leftShown(): boolean {
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

    public loadRightComponents(): ViewPane[] {
        return this.viewController.paneItems.map((paneItem: PaneItem) => {
            let componentFactory = this.componentFactoryResolver.resolveComponentFactory(paneItem.component);
            let componentRef = this.right.createComponent(componentFactory);
            return componentRef.instance;
        });
    }

    constructor(private viewController: ViewControllerService,
        private errorNotificationService: ErrorNotificationModalService,
        private componentFactoryResolver: ComponentFactoryResolver) { }
}
