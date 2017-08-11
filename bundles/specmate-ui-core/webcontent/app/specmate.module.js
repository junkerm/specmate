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
var specmate_data_service_1 = require("./services/specmate-data.service");
var editor_common_control_service_1 = require("./services/editor-common-control.service");
var navigator_service_1 = require("./services/navigator.service");
var specmate_component_1 = require("./specmate.component");
var page_not_found_component_1 = require("./components/page-not-found.component");
var core_module_1 = require("./components/core/core.module");
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
                ng_bootstrap_1.NgbModule.forRoot(),
                core_module_1.CoreModule,
                requirements_module_1.RequirementsModule,
                tests_module_1.TestsModule,
                specmate_routing_module_1.SpecmateRoutingModule
            ],
            declarations: [
                specmate_component_1.SpecmateComponent,
                page_not_found_component_1.PageNotFound
            ],
            providers: [specmate_data_service_1.SpecmateDataService, editor_common_control_service_1.EditorCommonControlService, navigator_service_1.NavigatorService, unsaved_changes_guard_1.UnsavedChangesGuard],
            bootstrap: [specmate_component_1.SpecmateComponent]
        })
    ], SpecmateModule);
    return SpecmateModule;
}());
exports.SpecmateModule = SpecmateModule;
//# sourceMappingURL=specmate.module.js.map