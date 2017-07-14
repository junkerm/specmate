"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var router_1 = require("@angular/router");
var project_explorer_component_1 = require("../core/project-explorer.component");
var requirements_perspective_component_1 = require("./requirements-perspective.component");
var requirement_details_component_1 = require("./requirement-details.component");
var model_editor_component_1 = require("./model-editor/model-editor.component");
var requirementsRoutes = [
    {
        path: 'requirements',
        component: requirements_perspective_component_1.RequirementsPerspective,
        children: [{
                path: ':url/ceg',
                component: model_editor_component_1.ModelEditor,
                outlet: 'main',
            }, {
                path: ':url/new-ceg',
                component: model_editor_component_1.ModelEditor,
                outlet: 'main'
            }, {
                path: ':url',
                component: requirement_details_component_1.RequirementsDetails,
                outlet: 'main'
            },
            {
                path: '',
                component: project_explorer_component_1.ProjectExplorer,
                outlet: 'left'
            }]
    }
];
var RequirementsRoutingModule = (function () {
    function RequirementsRoutingModule() {
    }
    RequirementsRoutingModule = __decorate([
        core_1.NgModule({
            imports: [router_1.RouterModule.forChild(requirementsRoutes)],
            exports: [router_1.RouterModule],
        })
    ], RequirementsRoutingModule);
    return RequirementsRoutingModule;
}());
exports.RequirementsRoutingModule = RequirementsRoutingModule;
//# sourceMappingURL=requirements-routing.module.js.map