import { Injectable } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
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
        if (!this.isOpen) {
            this.isOpen = true;
            this.modalRef = this.modalService.open(LoadingModalContent, {size:"sm"});
            this.modalRef.result.then((result) => {
                this.isOpen = false;
            }).catch((result) => {
                this.isOpen = false;
            });
        }
    }

    public close(){
        if(this.isOpen){
            this.modalRef.close();
        }
    }
}
