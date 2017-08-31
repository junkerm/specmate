"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var forms_1 = require("@angular/forms");
var http_1 = require("@angular/http");
var ng_bootstrap_1 = require("@ng-bootstrap/ng-bootstrap");
var angular_split_1 = require("angular-split");
var specmate_data_service_1 = require("./services/data/specmate-data.service");
var editor_common_control_service_1 = require("./services/common-controls/editor-common-control.service");
var navigator_service_1 = require("./services/navigation/navigator.service");
var logging_service_1 = require("./services/logging/logging.service");
var confirmation_modal_service_1 = require("./services/notification/confirmation-modal.service");
var error_notification_modal_service_1 = require("./services/notification/error-notification-modal.service");
var view_controller_service_1 = require("./services/view/view-controller.service");
var specmate_component_1 = require("./specmate.component");
var pages_module_1 = require("./components/pages/pages.module");
var core_module_1 = require("./components/core/core.module");
var specmate_forms_module_1 = require("./components/forms/specmate-forms.module");
var requirements_module_1 = require("./components/requirements/requirements.module");
var specmate_routing_module_1 = require("./specmate-routing.module");
var tests_module_1 = require("./components/tests/tests.module");
var unsaved_changes_guard_1 = require("./guards/unsaved-changes-guard");
var SpecmateModule = (function () {
    function SpecmateModule() {
    }
    SpecmateModule = __decorate([
        core_1.NgModule({
            imports: [
                forms_1.FormsModule,
                http_1.HttpModule,
                pages_module_1.PagesModule,
                core_module_1.CoreModule,
                specmate_forms_module_1.SpecmateFormsModule,
                requirements_module_1.RequirementsModule,
                tests_module_1.TestsModule,
                specmate_routing_module_1.SpecmateRoutingModule,
                angular_split_1.AngularSplitModule,
                ng_bootstrap_1.NgbModule.forRoot()
            ],
            declarations: [
                specmate_component_1.SpecmateComponent
            ],
            providers: [
                specmate_data_service_1.SpecmateDataService,
                editor_common_control_service_1.EditorCommonControlService,
                navigator_service_1.NavigatorService,
                logging_service_1.LoggingService,
                confirmation_modal_service_1.ConfirmationModal,
                error_notification_modal_service_1.ErrorNotificationModalService,
                view_controller_service_1.ViewControllerService,
                unsaved_changes_guard_1.UnsavedChangesGuard
            ],
            bootstrap: [
                specmate_component_1.SpecmateComponent
            ]
        })
    ], SpecmateModule);
    return SpecmateModule;
}());
exports.SpecmateModule = SpecmateModule;
//# sourceMappingURL=specmate.module.js.map