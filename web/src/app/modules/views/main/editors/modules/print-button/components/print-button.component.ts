import { Component } from '@angular/core';
import { ViewControllerService } from '../../../../../controller/modules/view-controller/services/view-controller.service';

/**
 * This is the Specmate print component
 */
@Component({
    moduleId: module.id.toString(),
    selector: 'print-button',
    templateUrl: 'print-button.component.html',
    styleUrls: ['print-button.component.css']
})

export class PrintButton {
    constructor(private viewController: ViewControllerService) { }

    public print(): void {
        this.viewController.printEditor();
    }

    public unprint(): void {
        this.viewController.unprintEditor();
    }

    public get isPrint(): boolean {
        return this.viewController.isEditorPrint;
    }

}
