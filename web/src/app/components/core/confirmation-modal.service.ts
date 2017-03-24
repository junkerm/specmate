import { NgbModal, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Injectable, Component, Input } from '@angular/core';
import { ConfirmationModalContent } from './confirmation-modal-content.component';

@Injectable()
export class ConfirmationModal {
    constructor(private modalService: NgbModal) { }

    open(message: string): Promise<any> {
        const modalRef = this.modalService.open(ConfirmationModalContent);
        modalRef.componentInstance.name = message;
        return modalRef.result;
    }
}