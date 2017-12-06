"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var router_1 = require("@angular/router");
var url_1 = require("../../../util/url");
var CEGModel_1 = require("../../../model/CEGModel");
var ceg_model_details_component_1 = require("../../views/main/editors/modules/ceg-model-editor/components/ceg-model-details.component");
var unsaved_changes_guard_1 = require("../guards/unsaved-changes-guard");
var Requirement_1 = require("../../../model/Requirement");
var requirement_details_component_1 = require("../../views/main/editors/modules/requirements-details/components/requirement-details.component");
var TestProcedure_1 = require("../../../model/TestProcedure");
var test_procedure_editor_component_1 = require("../../views/main/editors/modules/test-procedure-editor/components/test-procedure-editor.component");
var TestSpecification_1 = require("../../../model/TestSpecification");
var test_specification_editor_component_1 = require("../../views/main/editors/modules/test-specification-editor/components/test-specification-editor.component");
var Process_1 = require("../../../model/Process");
var process_details_component_1 = require("../../views/main/editors/modules/process-model-editor/components/process-details.component");
var welcome_component_1 = require("../../views/main/static/modules/welcome-page/components/welcome.component");
var page_not_found_component_1 = require("../../views/main/static/modules/page-not-found/components/page-not-found.component");
var core_1 = require("@angular/core");
var ceg_model_editor_module_1 = require("../../views/main/editors/modules/ceg-model-editor/ceg-model-editor.module");
var requirements_details_module_1 = require("../../views/main/editors/modules/requirements-details/requirements-details.module");
var test_procedure_editor_module_1 = require("../../views/main/editors/modules/test-procedure-editor/test-procedure-editor.module");
var test_specification_editor_module_1 = require("../../views/main/editors/modules/test-specification-editor/test-specification-editor.module");
var process_editor_module_1 = require("../../views/main/editors/modules/process-model-editor/process-editor.module");
var welcome_page_module_1 = require("../../views/main/static/modules/welcome-page/welcome-page.module");
var page_not_found_module_1 = require("../../views/main/static/modules/page-not-found/page-not-found.module");
var routes = [
    {
        path: url_1.Url.basePath(CEGModel_1.CEGModel) + '/:url',
        component: ceg_model_details_component_1.CEGModelDetails,
        canDeactivate: [unsaved_changes_guard_1.UnsavedChangesGuard]
    }, {
        path: url_1.Url.basePath(Requirement_1.Requirement) + '/:url',
        component: requirement_details_component_1.RequirementsDetails,
        canDeactivate: [unsaved_changes_guard_1.UnsavedChangesGuard]
    }, {
        path: url_1.Url.basePath(TestProcedure_1.TestProcedure) + '/:url',
        component: test_procedure_editor_component_1.TestProcedureEditor,
        canDeactivate: [unsaved_changes_guard_1.UnsavedChangesGuard]
    }, {
        path: url_1.Url.basePath(TestSpecification_1.TestSpecification) + '/:url',
        component: test_specification_editor_component_1.TestSpecificationEditor,
        canDeactivate: [unsaved_changes_guard_1.UnsavedChangesGuard]
    }, {
        path: url_1.Url.basePath(Process_1.Process) + '/:url',
        component: process_details_component_1.ProcessDetails,
        canDeactivate: [unsaved_changes_guard_1.UnsavedChangesGuard]
    },
    { path: '', component: welcome_component_1.Welcome },
    { path: '**', component: page_not_found_component_1.PageNotFound }
];
var SpecmateRoutingModule = (function () {
    function SpecmateRoutingModule() {
    }
    SpecmateRoutingModule = __decorate([
        core_1.NgModule({
            imports: [
                router_1.RouterModule.forRoot(routes),
                ceg_model_editor_module_1.CEGModelEditorModule,
                process_editor_module_1.ProcessEditorModule,
                requirements_details_module_1.RequirementsDetailsModule,
                test_procedure_editor_module_1.TestProcedureEditorModule,
                test_specification_editor_module_1.TestSpecificationEditorModule,
                welcome_page_module_1.WelcomePageModule,
                page_not_found_module_1.PageNotFoundModule
            ],
            declarations: [],
            providers: [
                unsaved_changes_guard_1.UnsavedChangesGuard
            ],
            exports: [router_1.RouterModule]
        })
    ], SpecmateRoutingModule);
    return SpecmateRoutingModule;
}());
exports.SpecmateRoutingModule = SpecmateRoutingModule;
//# sourceMappingURL=specmate-routing.module.js.map