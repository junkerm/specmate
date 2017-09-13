import { Config } from '../../config/config';
import { SpecmateDataService } from '../data/specmate-data.service';
import { NgbModal, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Injectable, Component, Input } from '@angular/core';
import { ConfirmationModalContent } from '../../components/core/notification/confirmation-modal-content.component';

@Injectable()
export class ConfirmationModal {
    constructor(private modalService: NgbModal, private dataService: SpecmateDataService) { }

    public open(message: string, withCancel:boolean=true): Promise<any> {
        const modalRef = this.modalService.open(ConfirmationModalContent);
        modalRef.componentInstance.message = message;
        modalRef.componentInstance.withCancel = withCancel;
        return modalRef.result;
    }

    public confirmSave(message?: string): Promise<void> {
        if(this.dataService.hasCommits) {
            return this.open(message || Config.CONFIRM_SAVE_MESSAGE);
        }
        return Promise.resolve();
    }
}