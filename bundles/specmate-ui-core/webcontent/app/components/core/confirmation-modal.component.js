"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var ng_bootstrap_1 = require('@ng-bootstrap/ng-bootstrap');
var core_1 = require('@angular/core');
var ConfirmationModalContent = (function () {
    function ConfirmationModalContent(activeModal) {
        this.activeModal = activeModal;
    }
    __decorate([
        core_1.Input(), 
        __metadata('design:type', String)
    ], ConfirmationModalContent.prototype, "name", void 0);
    ConfirmationModalContent = __decorate([
        core_1.Component({
            selector: 'confirmation-modal-content',
            template: "\n    <div class=\"modal-header\">\n      <h4 class=\"modal-title\">Hi there!</h4>\n      <button type=\"button\" class=\"close\" aria-label=\"Close\" (click)=\"activeModal.dismiss('Cross click')\">\n        <span aria-hidden=\"true\">&times;</span>\n      </button>\n    </div>\n    <div class=\"modal-body\">\n      <p>Hello, {{name}}!</p>\n    </div>\n    <div class=\"modal-footer\">\n      <button type=\"button\" class=\"btn btn-secondary\" (click)=\"activeModal.close('Close click')\">Close</button>\n    </div>\n  "
        }), 
        __metadata('design:paramtypes', [ng_bootstrap_1.NgbActiveModal])
    ], ConfirmationModalContent);
    return ConfirmationModalContent;
}());
exports.ConfirmationModalContent = ConfirmationModalContent;
var ConfirmationModal = (function () {
    function ConfirmationModal(modalService) {
        this.modalService = modalService;
    }
    ConfirmationModal.prototype.open = function () {
        var modalRef = this.modalService.open(ConfirmationModalContent);
        modalRef.componentInstance.name = 'World';
    };
    ConfirmationModal = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'confirmation-modal',
            template: ''
        }), 
        __metadata('design:paramtypes', [ng_bootstrap_1.NgbModal])
    ], ConfirmationModal);
    return ConfirmationModal;
}());
exports.ConfirmationModal = ConfirmationModal;
//# sourceMappingURL=confirmation-modal.component.js.map