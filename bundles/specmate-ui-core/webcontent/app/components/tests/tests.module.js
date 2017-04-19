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
var tests_perspective_component_1 = require('./tests-perspective.component');
//import { TestSpecificationEditor } from './test-specification-editor.component';
var core_module_1 = require('../core/core.module');
var tests_routing_module_1 = require('./tests-routing.module');
var TestsModule = (function () {
    function TestsModule() {
    }
    TestsModule = __decorate([
        core_1.NgModule({
            imports: [
                core_module_1.CoreModule,
                tests_routing_module_1.TestsRoutingModule,
            ],
            declarations: [
                tests_perspective_component_1.TestsPerspective,
            ],
            providers: [],
            exports: [],
        }), 
        __metadata('design:paramtypes', [])
    ], TestsModule);
    return TestsModule;
}());
exports.TestsModule = TestsModule;
//# sourceMappingURL=tests.module.js.map