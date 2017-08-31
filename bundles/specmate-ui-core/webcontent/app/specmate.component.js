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
var error_notification_modal_service_1 = require("./services/notification/error-notification-modal.service");
var view_controller_service_1 = require("./services/view/view-controller.service");
/**
 * This is the Specmate main component
 */
var SpecmateComponent = (function () {
    function SpecmateComponent(viewController, errorNotificationService) {
        this.viewController = viewController;
        this.errorNotificationService = errorNotificationService;
    }
    Object.defineProperty(SpecmateComponent.prototype, "loggingShown", {
        get: function () {
            return this.viewController.loggingOutputShown;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(SpecmateComponent.prototype, "explorerShown", {
        get: function () {
            return this.viewController.projectExplorerShown;
        },
        enumerable: true,
        configurable: true
    });
    SpecmateComponent = __decorate([
        core_1.Component({
            selector: 'specmate',
            moduleId: module.id,
            templateUrl: 'specmate.component.html',
            styleUrls: ['specmate.component.css']
        }),
        __metadata("design:paramtypes", [view_controller_service_1.ViewControllerService, error_notification_modal_service_1.ErrorNotificationModalService])
    ], SpecmateComponent);
    return SpecmateComponent;
}());
exports.SpecmateComponent = SpecmateComponent;
//# sourceMappingURL=specmate.component.js.map