import { LogElement } from '../logging/log-element';
import { LoggingService } from '../logging/logging.service';
import { Config } from '../../config/config';
import { SpecmateDataService } from '../data/specmate-data.service';
import { NgbModal, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Injectable, Component, Input } from '@angular/core';
import { ErrorModalContent } from '../../components/core/notification/error-modal-content.component';

@Injectable()
export class ErrorNotificationModalService {
    constructor(private modalService: NgbModal, private logger: LoggingService) {
        this.logger.logObservable.switchMap((logElement: LogElement) => {
            if(logElement.isError) {
                return this.open(logElement.message).catch(() => { /* We catch the original error here. */ });
            }
            return Promise.resolve();
        }).subscribe(/* Activate the observable. Without this, the above does nothing. */);
    }

    public open(message: string): Promise<any> {
        const modalRef = this.modalService.open(ErrorModalContent);
        modalRef.componentInstance.message = message;
        return modalRef.result;
    }
}