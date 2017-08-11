"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var Url_1 = require("./util/Url");
var test_specification_editor_component_1 = require("./components/tests/test-specification-editor.component");
var test_procedure_editor_component_1 = require("./components/tests/test-procedure-editor.component");
var requirement_details_component_1 = require("./components/requirements/requirement-details.component");
var model_editor_component_1 = require("./components/requirements/model-editor/model-editor.component");
var core_1 = require("@angular/core");
var router_1 = require("@angular/router");
var page_not_found_component_1 = require("./components/page-not-found.component");
var CEGModel_1 = require("./model/CEGModel");
var Requirement_1 = require("./model/Requirement");
var TestProcedure_1 = require("./model/TestProcedure");
var TestSpecification_1 = require("./model/TestSpecification");
var unsaved_changes_guard_1 = require("./guards/unsaved-changes-guard");
var routes = [
    {
        path: Url_1.Url.basePath(CEGModel_1.CEGModel) + '/:url',
        component: model_editor_component_1.ModelEditor,
        canDeactivate: [unsaved_changes_guard_1.UnsavedChangesGuard]
    }, {
        path: Url_1.Url.basePath(Requirement_1.Requirement) + '/:url',
        component: requirement_details_component_1.RequirementsDetails,
        canDeactivate: [unsaved_changes_guard_1.UnsavedChangesGuard]
    }, {
        path: Url_1.Url.basePath(TestProcedure_1.TestProcedure) + '/:url',
        component: test_procedure_editor_component_1.TestProcedureEditor,
        canDeactivate: [unsaved_changes_guard_1.UnsavedChangesGuard]
    }, {
        path: Url_1.Url.basePath(TestSpecification_1.TestSpecification) + '/:url',
        component: test_specification_editor_component_1.TestSpecificationEditor,
        canDeactivate: [unsaved_changes_guard_1.UnsavedChangesGuard]
    },
    { path: '**', component: page_not_found_component_1.PageNotFound }
];
var SpecmateRoutingModule = (function () {
    function SpecmateRoutingModule() {
    }
    SpecmateRoutingModule = __decorate([
        core_1.NgModule({
            imports: [router_1.RouterModule.forRoot(routes)],
            exports: [router_1.RouterModule]
        })
    ], SpecmateRoutingModule);
    return SpecmateRoutingModule;
}());
exports.SpecmateRoutingModule = SpecmateRoutingModule;
//# sourceMappingURL=specmate-routing.module.js.map