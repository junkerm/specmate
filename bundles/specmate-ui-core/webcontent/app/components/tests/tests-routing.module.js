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
var tests_perspective_component_1 = require('./tests-perspective.component');
var test_specification_editor_component_1 = require('./test-specification-editor.component');
var test_procedure_editor_component_1 = require('./test-procedure-editor.component');
var testRoutes = [
    {
        path: 'tests',
        component: tests_perspective_component_1.TestsPerspective,
        children: [{
                path: ':url/tpe',
                component: test_procedure_editor_component_1.TestProcedureEditor,
                outlet: 'main',
            }, {
                path: ':url',
                component: test_specification_editor_component_1.TestSpecificationEditor,
                outlet: 'main'
            },
            {
                path: '',
                component: project_explorer_component_1.ProjectExplorer,
                outlet: 'left'
            }]
    }
];
var TestsRoutingModule = (function () {
    function TestsRoutingModule() {
    }
    TestsRoutingModule = __decorate([
        core_1.NgModule({
            imports: [router_1.RouterModule.forChild(testRoutes)],
            exports: [router_1.RouterModule],
        }), 
        __metadata('design:paramtypes', [])
    ], TestsRoutingModule);
    return TestsRoutingModule;
}());
exports.TestsRoutingModule = TestsRoutingModule;
//# sourceMappingURL=tests-routing.module.js.map