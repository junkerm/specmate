import { SpecmateDataService } from '../../../services/data/specmate-data.service';
import { ViewControllerService } from '../../../services/view/view-controller.service';
import { Component } from '@angular/core';

@Component({
    moduleId: module.id,
    selector: 'operation-monitor',
    templateUrl: 'operation-monitor.component.html'
})
export class OperationMonitor {

    public get busy(): boolean {
        return this.dataService.busy;
    }

    public get taskName(): string {
        return this.dataService.currentTaskName;
    }

    constructor(private dataService: SpecmateDataService, private viewController: ViewControllerService) { }

    public toggleLoggingView(): void {
        this.viewController.loggingOutputShown = !this.viewController.loggingOutputShown;
    }
}
