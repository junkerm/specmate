import { ChangeDetectorRef, Component, OnDestroy } from '@angular/core';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { ViewControllerService } from '../../../../views/controller/modules/view-controller/services/view-controller.service';

@Component({
    moduleId: module.id.toString(),
    selector: 'operation-monitor',
    templateUrl: 'operation-monitor.component.html'
})
export class OperationMonitor implements OnDestroy {

    ngOnDestroy(): void {
        this.dataServiceSubscription.unsubscribe();
        if (this.changeDetectorRef) {
            this.changeDetectorRef.detach();
        }
    }

    public isLoading: boolean;
    private dataServiceSubscription: any;

    public get taskName(): string {
        return this.dataService.currentTaskName;
    }

    constructor(
        private dataService:
        SpecmateDataService,
        private viewController: ViewControllerService,
        private changeDetectorRef: ChangeDetectorRef) {

        this.isLoading = this.dataService.isLoading;
        this.dataServiceSubscription = this.dataService.stateChanged.subscribe(() => {
            this.changeDetectorRef.detectChanges();
            this.isLoading = this.dataService.isLoading;
            this.changeDetectorRef.detectChanges();
        });
    }

    public toggleLoggingView(): void {
        this.viewController.loggingOutputShown = !this.viewController.loggingOutputShown;
    }
}
