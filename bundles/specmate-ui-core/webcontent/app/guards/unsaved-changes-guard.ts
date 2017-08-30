import { Config } from '../config/config';
import { ConfirmationModal } from '../components/core/forms/confirmation-modal.service';
import { SpecmateDataService } from '../services/data/specmate-data.service';
import { CanDeactivate } from '@angular/router';
import { Injectable } from '@angular/core';
import { SpecmateViewBase } from '../components/core/views/specmate-view-base';

@Injectable()
export class UnsavedChangesGuard implements CanDeactivate<SpecmateViewBase> {

    constructor(private dataService: SpecmateDataService, private modal: ConfirmationModal) { }

    canDeactivate(component: SpecmateViewBase) {
        if(this.dataService.hasCommits) {
            return this.modal.open(Config.NAVIGATION_CONFIRMATION).then(() => true).catch(() => false);
        } else {
            return true;
        }
    }
}