import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Component, Input } from '@angular/core';
import { ModalBase } from '../base/modal-base';
import { ViewControllerService } from '../../../../views/controller/modules/view-controller/services/view-controller.service';
import { NavigatorService } from '../../../../navigation/modules/navigator/services/navigator.service';

@Component({
    moduleId: module.id,
    selector: 'error-modal-content',
    templateUrl: 'error-modal-content.component.html'
})
export class ErrorModalContent extends ModalBase {
    @Input()
    public message: string;

    constructor(protected activeModal: NgbActiveModal, private viewControllerService: ViewControllerService, public navigator: NavigatorService) {
        super(activeModal);
    }

    public openLog(): void {
        this.activeModal.dismiss();
        this.viewControllerService.showLoggingOutput();
    }

    public navigateForward(): void {
        this.navigator.forward();
        this.activeModal.dismiss();
    }

    public navigateBack(): void {
        this.navigator.back();
        this.activeModal.dismiss();
    }

    public get isForwardShown(): boolean {
        return this.navigator.hasNext;
    }

    public get isBackShown(): boolean {
        return this.navigator.hasPrevious;
    }
}
