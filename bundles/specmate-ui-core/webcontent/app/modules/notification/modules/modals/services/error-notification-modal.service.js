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
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var ng_bootstrap_1 = require("@ng-bootstrap/ng-bootstrap");
var logging_service_1 = require("../../../../views/side/modules/log-list/services/logging.service");
var error_modal_content_component_1 = require("../components/error-modal-content.component");
var ErrorNotificationModalService = /** @class */ (function () {
    function ErrorNotificationModalService(modalService, logger) {
        var _this = this;
        this.modalService = modalService;
        this.logger = logger;
        this.isOpen = false;
        this.logger.logObservable.switchMap(function (logElement) {
            if (logElement.isError) {
                return _this.open(logElement.message).catch(function () { });
            }
            return Promise.resolve();
        }).subscribe();
    }
    ErrorNotificationModalService.prototype.open = function (message) {
        var _this = this;
        if (!this.isOpen) {
            this.isOpen = true;
            var modalRef = this.modalService.open(error_modal_content_component_1.ErrorModalContent);
            modalRef.componentInstance.message = message;
            return modalRef.result.then(function (result) {
                _this.isOpen = false;
                return Promise.resolve(result);
            }).catch(function (result) {
                _this.isOpen = false;
                return Promise.reject(result);
            });
        }
        return Promise.reject('Modal already open.');
    };
    ErrorNotificationModalService = __decorate([
        core_1.Injectable(),
        __metadata("design:paramtypes", [ng_bootstrap_1.NgbModal, logging_service_1.LoggingService])
    ], ErrorNotificationModalService);
    return ErrorNotificationModalService;
}());
exports.ErrorNotificationModalService = ErrorNotificationModalService;
//# sourceMappingURL=error-notification-modal.service.js.map