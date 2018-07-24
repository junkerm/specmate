import { Component } from '@angular/core';
import { AuthenticationService } from '../../auth/services/authentication.service';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';

@Component({
    moduleId: module.id.toString(),
    selector: 'logout',
    templateUrl: 'logout.component.html',
    styleUrls: ['logout.component.css']
})
export class Logout {
    constructor(private auth: AuthenticationService,
        private dataService: SpecmateDataService,
        private translate: TranslateService,
        private modal: ConfirmationModal) { }

    public async logout(): Promise<void> {

        if (this.dataService.hasCommits) {
            try {
                await this.modal.open(this.translate.instant('discardUnsavedChangesConfirmation'));
                this.auth.deauthenticate();
            } catch (e) { }
        } else {
            this.auth.deauthenticate();
        }
    }
}
