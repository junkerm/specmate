"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var ng_bootstrap_1 = require("@ng-bootstrap/ng-bootstrap");
var confirmation_modal_service_1 = require("./services/confirmation-modal.service");
var error_notification_modal_service_1 = require("./services/error-notification-modal.service");
var confirmation_modal_content_component_1 = require("./components/confirmation-modal-content.component");
var error_modal_content_component_1 = require("./components/error-modal-content.component");
var platform_browser_1 = require("@angular/platform-browser");
var ModalsModule = /** @class */ (function () {
    function ModalsModule() {
    }
    ModalsModule = __decorate([
        core_1.NgModule({
            imports: [
                // MODULE IMPORTS
                ng_bootstrap_1.NgbModule.forRoot(),
                platform_browser_1.BrowserModule
            ],
            declarations: [
                // COMPONENTS IN THIS MODULE
                confirmation_modal_content_component_1.ConfirmationModalContent,
                error_modal_content_component_1.ErrorModalContent
            ],
            exports: [],
            providers: [
                // SERVICES
                confirmation_modal_service_1.ConfirmationModal,
                error_notification_modal_service_1.ErrorNotificationModalService
            ],
            bootstrap: [],
            entryComponents: [
                // ENTRY POINTS
                confirmation_modal_content_component_1.ConfirmationModalContent, error_modal_content_component_1.ErrorModalContent
            ]
        })
    ], ModalsModule);
    return ModalsModule;
}());
exports.ModalsModule = ModalsModule;
//# sourceMappingURL=modals.module.js.map