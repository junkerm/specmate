import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Component, Input } from '@angular/core';
import { ModalBase } from "./modal-base";

@Component({
    moduleId: module.id,
    selector: 'confirmation-modal-content',
    templateUrl: 'confirmation-modal-content.component.html'
})
export class ConfirmationModalContent extends ModalBase {
    @Input()
    public message: string;

    @Input()
    public withCancel: boolean = true;

    constructor(protected activeModal: NgbActiveModal) {
        super(activeModal);
    }
}
