import { NgbModal, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Injectable, Component, Input } from '@angular/core';

@Component({
    moduleId: module.id,
    selector: 'confirmation-modal-content',
    templateUrl: 'confirmation-modal-content.component.html'
})
export class ConfirmationModalContent {
    @Input()
    message: string;

    constructor(public activeModal: NgbActiveModal) { }
}
