import { Injectable } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { ConfirmationModalContent } from '../components/confirmation-modal-content.component';
import { Config } from '../../../../../config/config';
import { TranslateService } from '@ngx-translate/core';

@Injectable()
export class ConfirmationModal {
    constructor(private modalService: NgbModal, private dataService: SpecmateDataService, private translate: TranslateService) { }

    public open(message: string, withCancel = true): Promise<any> {
        const modalRef = this.modalService.open(ConfirmationModalContent);
        modalRef.componentInstance.message = message;
        modalRef.componentInstance.withCancel = withCancel;
        return modalRef.result;
    }

    public confirmSave(message?: string): Promise<void> {
        if (this.dataService.hasCommits) {
            return this.open(message || this.translate.instant('confirmSave'));
        }
        return Promise.resolve();
    }
}
