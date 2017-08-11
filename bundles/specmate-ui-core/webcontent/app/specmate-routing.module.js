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
var unsaved_changes_guard_1 = require("./guards/unsaved-changes-guard");
var views = [model_editor_component_1.ModelEditor, requirement_details_component_1.RequirementsDetails, test_procedure_editor_component_1.TestProcedureEditor, test_specification_editor_component_1.TestSpecificationEditor];
var routes = views.map(function (view) {
    return {
        path: Url_1.Url.basePath(view.modelElementClass),
        component: view,
        canDeactivate: [unsaved_changes_guard_1.UnsavedChangesGuard]
    };
});
/*
const routes: Routes = [
  {
    path: Url.basePath(ModelEditor.modelElementClass) + '/:url',
    component: ModelEditor,
    canDeactivate: [UnsavedChangesGuard]
  }, {
    path: Url.basePath(RequirementsDetails.modelElementClass) + '/:url',
    component: RequirementsDetails,
    canDeactivate: [UnsavedChangesGuard]
  }, {
    path: Url.basePath(TestProcedureEditor.modelElementClass) + '/:url',
    component: TestProcedureEditor,
    canDeactivate: [UnsavedChangesGuard]
  }, {
    path: Url.basePath(TestSpecificationEditor.modelElementClass) + '/:url',
    component: TestSpecificationEditor,
    canDeactivate: [UnsavedChangesGuard]
  },
  { path: '**', component: PageNotFound }
];
*/
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