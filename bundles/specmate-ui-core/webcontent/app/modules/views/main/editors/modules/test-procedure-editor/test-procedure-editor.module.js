"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var test_procedure_editor_component_1 = require("./components/test-procedure-editor.component");
var angular_split_1 = require("angular-split");
var maximize_button_module_1 = require("../maximize-button/maximize-button.module");
var ng2_dragula_1 = require("ng2-dragula");
var test_step_row_component_1 = require("./components/test-step-row.component");
var platform_browser_1 = require("@angular/platform-browser");
var test_case_parameter_mapping_module_1 = require("../test-case-parameter-mapping/test-case-parameter-mapping.module");
var forms_1 = require("@angular/forms");
var TestProcedureEditorModule = (function () {
    function TestProcedureEditorModule() {
    }
    TestProcedureEditorModule = __decorate([
        core_1.NgModule({
            imports: [
                // MODULE IMPORTS
                platform_browser_1.BrowserModule,
                angular_split_1.AngularSplitModule,
                maximize_button_module_1.MaximizeButtonModule,
                ng2_dragula_1.DragulaModule,
                test_case_parameter_mapping_module_1.TestCaseParameterMappingModule,
                forms_1.FormsModule,
                forms_1.ReactiveFormsModule
            ],
            declarations: [
                // COMPONENTS IN THIS MODULE
                test_procedure_editor_component_1.TestProcedureEditor,
                test_step_row_component_1.TestStepRow
            ],
            exports: [
                // THE COMPONENTS VISIBLE TO THE OUTSIDE
                test_procedure_editor_component_1.TestProcedureEditor
            ],
            providers: [],
            bootstrap: []
        })
    ], TestProcedureEditorModule);
    return TestProcedureEditorModule;
}());
exports.TestProcedureEditorModule = TestProcedureEditorModule;
//# sourceMappingURL=test-procedure-editor.module.js.map