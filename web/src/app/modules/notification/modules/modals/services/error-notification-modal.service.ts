import { Injectable } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { LoggingService } from '../../../../views/side/modules/log-list/services/logging.service';
import { LogElement } from '../../../../views/side/modules/log-list/services/log-element';
import { ErrorModalContent } from '../components/error-modal-content.component';

@Injectable()
export class ErrorNotificationModalService {

    private isOpen: boolean = false;

    constructor(private modalService: NgbModal, private logger: LoggingService) {
        this.logger.logObservable.switchMap((logElement: LogElement) => {
            if (logElement.isError) {
                return this.open(logElement.message).catch(() => { /* We catch the original error here. */ });
            }
            return Promise.resolve();
        }).subscribe(/* Activate the observable. Without this, the above does nothing. */);
    }

    public open(message: string): Promise<any> {
        if (!this.isOpen) {
            this.isOpen = true;
            const modalRef = this.modalService.open(ErrorModalContent);
            modalRef.componentInstance.message = message;
            return modalRef.result.then((result) => {
                this.isOpen = false;
                return Promise.resolve(result);
            }).catch((result) => {
                this.isOpen = false;
                return Promise.reject(result);
            });
        }
        return Promise.reject('Modal already open.');
    }
}
