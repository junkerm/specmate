import { Injectable } from '@angular/core';
import { CanDeactivate } from '@angular/router';
import { SpecmateViewBase } from '../../views/main/editors/base/specmate-view-base';
import { SpecmateDataService } from '../../data/modules/data-service/services/specmate-data.service';
import { ConfirmationModal } from '../../notification/modules/modals/services/confirmation-modal.service';
import { Config } from '../../../config/config';
import { TranslateService } from '@ngx-translate/core';

@Injectable()
export class UnsavedChangesGuard implements CanDeactivate<SpecmateViewBase> {

    constructor(private dataService: SpecmateDataService, private modal: ConfirmationModal, private translate: TranslateService) { }

    canDeactivate(component: SpecmateViewBase) {
        if (this.dataService.hasCommits) {
            return this.modal.open(this.translate.instant('discardUnsavedChangesConfirmation')).then(() => true).catch(() => false);
        } else {
            return true;
        }
    }
}
