import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { ServerConnectionService } from '../../../../common/modules/connection/services/server-connection-service';
import { UISafe } from '../../../../common/modules/ui/ui-safe-decorator';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { ValidationService } from '../../../../forms/modules/validation/services/validation.service';
import { NavigatorService } from '../../../../navigation/modules/navigator/services/navigator.service';
import { ValidationErrorSeverity } from '../../../../../validation/validation-error-severity';

@Component({
    moduleId: module.id.toString(),
    selector: 'common-controls',
    templateUrl: 'common-controls.component.html',
    styleUrls: ['common-controls.component.css']
})
export class CommonControls {

    public get connected(): boolean {
        return this.connection.isConnected;
    }

    constructor(
            private dataService: SpecmateDataService,
            private connection: ServerConnectionService,
            private validator: ValidationService,
            private navigator: NavigatorService,
            private translate: TranslateService) {
    }

    public save(): void {
        if (this.isSaveEnabled) {
            this.validator.validateCurrent();
            if (this.isSaveEnabled) {
                this.dataService.commit(this.translate.instant('save'));
            }
        }
    }

    public close(): void {
        this.back();
    }

    public undo(): void {
        if (this.isUndoEnabled) {
            this.dataService.undo();
            this.validator.validateCurrent();
        }
    }

    public forward(): void {
        if (this.isForwardEnabled) {
            this.navigator.forward();
        }
    }

    public back(): void {
        if (this.isBackEnabled) {
            this.navigator.back();
        }
    }

    public cancelEvent(event: Event): void {
        event.preventDefault();
        event.stopPropagation();
    }

    public get isSaveEnabled(): boolean {
        const hasSaveDisablingError =
            this.validator.currentSeverities.find(severity => severity === ValidationErrorSeverity.SAVE_DISABLED) !== undefined;
        return this.isEnabled && this.hasCommits && !hasSaveDisablingError;
    }

    public get isUndoEnabled(): boolean {
        return this.isEnabled && this.hasCommits;
    }

    @UISafe()
    private get hasCommits(): boolean {
        return this.dataService.hasCommits;
    }

    public get isBackEnabled(): boolean {
        return this.navigator.hasPrevious;
    }

    public get isForwardEnabled(): boolean {
        return this.navigator.hasNext;
    }

    public get isEnabled(): boolean {
        return true;
    }
}
