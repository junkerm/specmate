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
var modal_base_1 = require("./modal-base");
var ConfirmationModalContent = (function (_super) {
    __extends(ConfirmationModalContent, _super);
    function ConfirmationModalContent(activeModal) {
        var _this = _super.call(this, activeModal) || this;
        _this.activeModal = activeModal;
        return _this;
    }
    __decorate([
        core_1.Input(),
        __metadata("design:type", String)
    ], ConfirmationModalContent.prototype, "message", void 0);
    ConfirmationModalContent = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'confirmation-modal-content',
            templateUrl: 'confirmation-modal-content.component.html'
        }),
        __metadata("design:paramtypes", [ng_bootstrap_1.NgbActiveModal])
    ], ConfirmationModalContent);
    return ConfirmationModalContent;
}(modal_base_1.ModalBase));
exports.ConfirmationModalContent = ConfirmationModalContent;
//# sourceMappingURL=confirmation-modal-content.component.js.map