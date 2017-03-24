import { NgbModal, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Component, Input } from '@angular/core';

@Component({
    selector: 'confirmation-modal-content',
    template: `
    <div class="modal-header">
      <h4 class="modal-title">Hi there!</h4>
      <button type="button" class="close" aria-label="Close" (click)="activeModal.dismiss('Cross click')">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div class="modal-body">
      <p>Hello, {{name}}!</p>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-secondary" (click)="activeModal.close('Close click')">Close</button>
    </div>
  `
})
export class ConfirmationModalContent {
    @Input()
    name: string;

    constructor(public activeModal: NgbActiveModal) { }
}

@Component({
    moduleId: module.id,
    selector: 'confirmation-modal',
    template: ''
})
export class ConfirmationModal {
    constructor(private modalService: NgbModal) { }

    open() {
        const modalRef = this.modalService.open(ConfirmationModalContent);
        modalRef.componentInstance.name = 'World';
    }
}