"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var forms_1 = require("@angular/forms");
var test_case_row_component_1 = require("./test-case-row.component");
var core_1 = require("@angular/core");
var core_module_1 = require("../core/core.module");
var specmate_forms_module_1 = require("../forms/specmate-forms.module");
var test_specification_editor_component_1 = require("./test-specification-editor.component");
var test_parameter_form_component_1 = require("./test-parameter-form.component");
var test_case_condition_form_component_1 = require("./test-case-condition-form.component");
var test_case_value_form_component_1 = require("./test-case-value-form.component");
var test_case_name_form_component_1 = require("./test-case-name-form.component");
var test_procedure_editor_component_1 = require("./test-procedure-editor.component");
var test_case_parameter_mapping_component_1 = require("./test-case-parameter-mapping.component");
var test_step_row_component_1 = require("./test-step-row.component");
var ng2_dnd_1 = require("ng2-dnd");
var TestsModule = (function () {
    function TestsModule() {
    }
    TestsModule = __decorate([
        core_1.NgModule({
            imports: [
                core_module_1.CoreModule,
                forms_1.FormsModule,
                forms_1.ReactiveFormsModule,
                specmate_forms_module_1.SpecmateFormsModule,
                ng2_dnd_1.DndModule
            ],
            declarations: [
                test_specification_editor_component_1.TestSpecificationEditor,
                test_case_row_component_1.TestCaseRow,
                test_parameter_form_component_1.TestParameterForm,
                test_case_condition_form_component_1.TestCaseConditionForm,
                test_case_value_form_component_1.TestCaseValueForm,
                test_case_name_form_component_1.TestCaseNameForm,
                test_procedure_editor_component_1.TestProcedureEditor,
                test_case_parameter_mapping_component_1.TestCaseParameterMapping,
                test_step_row_component_1.TestStepRow
            ],
            providers: [],
            exports: [],
        })
    ], TestsModule);
    return TestsModule;
}());
exports.TestsModule = TestsModule;
//# sourceMappingURL=tests.module.js.map