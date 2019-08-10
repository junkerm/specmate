import { Injectable } from '@angular/core';
import { NgbModal, NgbModalRef, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { LogElement } from '../../../../views/side/modules/log-list/services/log-element';
import { LoggingService } from '../../../../views/side/modules/log-list/services/logging.service';
import { TypedModalContent } from '../components/typed-modal-content.component';
import { Dialogtype } from '../modal-dialog-type';
import { LoadingModalContent } from '../components/loading-modal-content.component';

@Injectable()
export class LoadingModalService {

    private isOpen = false;
    private modalRef: NgbModalRef;

    constructor(private modalService: NgbModal, private logger: LoggingService) {
    }

    public open() {
        var x: NgbModalConfig;
        if (!this.isOpen) {
            this.isOpen = true;
            this.modalRef = this.modalService.open(LoadingModalContent, { 
                // small size 
                size: "sm",
                // don't allow to hide the modal by clicking the background
                backdrop: "static", 
                // don't allow to hide the modal by hitting ESC
                keyboard: false, 
                // center modal vertically
                centered: true });

            this.modalRef.result.then((result) => {
                this.isOpen = false;
            }).catch((result) => {
                this.isOpen = false;
            });
        }
    }

    public close() {
        if (this.isOpen) {
            this.modalRef.close();
        }
    }
}
