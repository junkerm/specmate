import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

export abstract class ModalBase {

    public abstract get message(): string;

    constructor(protected activeModal: NgbActiveModal) { }
}
