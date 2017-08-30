import { Injectable } from '@angular/core';
import { ConfirmationModal } from "../../components/core/forms/confirmation-modal.service";

@Injectable()
export class LoggingService {
    public log: string[];

    constructor(private modal: ConfirmationModal) { }
}
