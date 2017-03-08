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
var core_1 = require('@angular/core');
var router_1 = require('@angular/router');
var project_explorer_component_1 = require('../core/project-explorer.component');
var requirements_perspective_component_1 = require('./requirements-perspective.component');
var requirement_details_component_1 = require('./requirement-details.component');
var ceg_editor_component_1 = require('./ceg-editor/ceg-editor.component');
var ceg_node_details_component_1 = require('./ceg-editor/ceg-node-details.component');
var requirementsRoutes = [
    {
        path: 'requirements',
        component: requirements_perspective_component_1.RequirementsPerspective,
        children: [{
                path: ':url/ceg',
                component: ceg_editor_component_1.CEGEditor,
                outlet: 'main',
                children: [{
                        path: ':url/ceg-node-details',
                        component: ceg_node_details_component_1.CEGNodeDetails,
                        outlet: 'ceg-node-details'
                    }]
            }, {
                path: ':url/new-ceg',
                component: ceg_editor_component_1.CEGEditor,
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
        }), 
        __metadata('design:paramtypes', [])
    ], RequirementsRoutingModule);
    return RequirementsRoutingModule;
}());
exports.RequirementsRoutingModule = RequirementsRoutingModule;
//# sourceMappingURL=requirements-routing.module.js.map