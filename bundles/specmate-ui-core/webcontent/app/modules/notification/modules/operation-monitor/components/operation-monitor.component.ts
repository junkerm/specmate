import { Component } from '@angular/core';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { ViewControllerService } from '../../../../views/controller/modules/view-controller/services/view-controller.service';

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
