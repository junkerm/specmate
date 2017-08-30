import { Config } from '../../../config/config';
import { SpecmateDataService } from '../../../services/data/specmate-data.service';
import { NgbModal, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Injectable, Component, Input } from '@angular/core';
import { ConfirmationModalContent } from './confirmation-modal-content.component';

@Injectable()
export class ConfirmationModal {
    constructor(private modalService: NgbModal, private dataService: SpecmateDataService) { }

    public open(message: string): Promise<any> {
        const modalRef = this.modalService.open(ConfirmationModalContent);
        modalRef.componentInstance.message = message;
        return modalRef.result;
    }

    public confirmSave(message?: string): Promise<void> {
        if(this.dataService.hasCommits) {
            return this.open(message || Config.CONFIRM_SAVE_MESSAGE);
        }
        return Promise.resolve();
    }
}