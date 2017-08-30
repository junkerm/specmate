"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var platform_browser_1 = require("@angular/platform-browser");
var forms_1 = require("@angular/forms");
var generic_form_component_1 = require("./generic-form.component");
var form_text_input_component_1 = require("./form-text-input.component");
var form_long_text_input_component_1 = require("./form-long-text-input.component");
var form_checkbox_input_component_1 = require("./form-checkbox-input.component");
var form_single_selection_input_component_1 = require("./form-single-selection-input.component");
var SpecmateFormsModule = (function () {
    function SpecmateFormsModule() {
    }
    SpecmateFormsModule = __decorate([
        core_1.NgModule({
            imports: [
                forms_1.ReactiveFormsModule,
                platform_browser_1.BrowserModule
            ],
            declarations: [
                generic_form_component_1.GenericForm,
                form_text_input_component_1.FormTextInput,
                form_long_text_input_component_1.FormLongTextInput,
                form_checkbox_input_component_1.FormCheckboxInput,
                form_single_selection_input_component_1.FormSingleSelectionInput
            ],
            providers: [],
            bootstrap: [],
            exports: [
                generic_form_component_1.GenericForm,
                form_text_input_component_1.FormTextInput,
                form_long_text_input_component_1.FormLongTextInput,
                form_checkbox_input_component_1.FormCheckboxInput,
                form_single_selection_input_component_1.FormSingleSelectionInput
            ]
        })
    ], SpecmateFormsModule);
    return SpecmateFormsModule;
}());
exports.SpecmateFormsModule = SpecmateFormsModule;
//# sourceMappingURL=specmate-forms.module.js.map