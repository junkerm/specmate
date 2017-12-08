"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var test_case_parameter_mapping_component_1 = require("./components/test-case-parameter-mapping.component");
var test_case_value_form_component_1 = require("./components/test-case-value-form.component");
var platform_browser_1 = require("@angular/platform-browser");
var forms_1 = require("@angular/forms");
var TestCaseParameterMappingModule = /** @class */ (function () {
    function TestCaseParameterMappingModule() {
    }
    TestCaseParameterMappingModule = __decorate([
        core_1.NgModule({
            imports: [
                // MODULE IMPORTS
                platform_browser_1.BrowserModule,
                forms_1.FormsModule,
                forms_1.ReactiveFormsModule
            ],
            declarations: [
                // COMPONENTS IN THIS MODULE
                test_case_parameter_mapping_component_1.TestCaseParameterMapping,
                test_case_value_form_component_1.TestCaseValueForm
            ],
            exports: [
                // THE COMPONENTS VISIBLE TO THE OUTSIDE
                test_case_parameter_mapping_component_1.TestCaseParameterMapping
            ],
            providers: [],
            bootstrap: []
        })
    ], TestCaseParameterMappingModule);
    return TestCaseParameterMappingModule;
}());
exports.TestCaseParameterMappingModule = TestCaseParameterMappingModule;
//# sourceMappingURL=test-case-parameter-mapping.module.js.map