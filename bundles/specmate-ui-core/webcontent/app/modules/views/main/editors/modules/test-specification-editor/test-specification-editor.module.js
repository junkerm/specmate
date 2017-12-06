"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var test_specification_editor_component_1 = require("./components/test-specification-editor.component");
var maximize_button_module_1 = require("../maximize-button/maximize-button.module");
var test_case_condition_form_component_1 = require("./components/test-case-condition-form.component");
var test_case_name_form_component_1 = require("./components/test-case-name-form.component");
var test_case_row_component_1 = require("./components/test-case-row.component");
var test_parameter_form_component_1 = require("./components/test-parameter-form.component");
var platform_browser_1 = require("@angular/platform-browser");
var ng2_dragula_1 = require("ng2-dragula");
var forms_1 = require("@angular/forms");
var navigator_module_1 = require("../../../../../navigation/modules/navigator/navigator.module");
var TestSpecificationEditorModule = (function () {
    function TestSpecificationEditorModule() {
    }
    TestSpecificationEditorModule = __decorate([
        core_1.NgModule({
            imports: [
                // MODULE IMPORTS
                maximize_button_module_1.MaximizeButtonModule,
                platform_browser_1.BrowserModule,
                ng2_dragula_1.DragulaModule,
                forms_1.FormsModule,
                forms_1.ReactiveFormsModule,
                navigator_module_1.NavigatorModule
            ],
            declarations: [
                // COMPONENTS IN THIS MODULE
                test_specification_editor_component_1.TestSpecificationEditor,
                test_case_condition_form_component_1.TestCaseConditionForm,
                test_case_name_form_component_1.TestCaseNameForm,
                test_case_row_component_1.TestCaseRow,
                test_parameter_form_component_1.TestParameterForm
            ],
            exports: [
                // THE COMPONENTS VISIBLE TO THE OUTSIDE
                test_specification_editor_component_1.TestSpecificationEditor
            ],
            providers: [],
            bootstrap: []
        })
    ], TestSpecificationEditorModule);
    return TestSpecificationEditorModule;
}());
exports.TestSpecificationEditorModule = TestSpecificationEditorModule;
//# sourceMappingURL=test-specification-editor.module.js.map