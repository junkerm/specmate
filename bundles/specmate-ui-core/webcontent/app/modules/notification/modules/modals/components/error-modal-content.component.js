"use strict";
var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
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
var ng_bootstrap_1 = require("@ng-bootstrap/ng-bootstrap");
var core_1 = require("@angular/core");
var modal_base_1 = require("../base/modal-base");
var view_controller_service_1 = require("../../../../views/controller/modules/view-controller/services/view-controller.service");
var navigator_service_1 = require("../../../../navigation/modules/navigator/services/navigator.service");
var ErrorModalContent = /** @class */ (function (_super) {
    __extends(ErrorModalContent, _super);
    function ErrorModalContent(activeModal, viewControllerService, navigator) {
        var _this = _super.call(this, activeModal) || this;
        _this.activeModal = activeModal;
        _this.viewControllerService = viewControllerService;
        _this.navigator = navigator;
        return _this;
    }
    ErrorModalContent.prototype.openLog = function () {
        this.activeModal.dismiss();
        this.viewControllerService.showLoggingOutput();
    };
    ErrorModalContent.prototype.navigateForward = function () {
        this.navigator.forward();
        this.activeModal.dismiss();
    };
    ErrorModalContent.prototype.navigateBack = function () {
        this.navigator.back();
        this.activeModal.dismiss();
    };
    Object.defineProperty(ErrorModalContent.prototype, "isForwardShown", {
        get: function () {
            return this.navigator.hasNext;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ErrorModalContent.prototype, "isBackShown", {
        get: function () {
            return this.navigator.hasPrevious;
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        core_1.Input(),
        __metadata("design:type", String)
    ], ErrorModalContent.prototype, "message", void 0);
    ErrorModalContent = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'error-modal-content',
            templateUrl: 'error-modal-content.component.html'
        }),
        __metadata("design:paramtypes", [ng_bootstrap_1.NgbActiveModal,
            view_controller_service_1.ViewControllerService,
            navigator_service_1.NavigatorService])
    ], ErrorModalContent);
    return ErrorModalContent;
}(modal_base_1.ModalBase));
exports.ErrorModalContent = ErrorModalContent;
//# sourceMappingURL=error-modal-content.component.js.map