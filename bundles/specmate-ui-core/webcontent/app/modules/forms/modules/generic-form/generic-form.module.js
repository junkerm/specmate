"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var generic_form_component_1 = require("./components/generic-form.component");
var forms_1 = require("@angular/forms");
var form_text_input_component_1 = require("./components/form-text-input.component");
var form_checkbox_input_component_1 = require("./components/form-checkbox-input.component");
var form_long_text_input_component_1 = require("./components/form-long-text-input.component");
var form_single_selection_input_component_1 = require("./components/form-single-selection-input.component");
var platform_browser_1 = require("@angular/platform-browser");
var GenericFormModule = (function () {
    function GenericFormModule() {
    }
    GenericFormModule = __decorate([
        core_1.NgModule({
            imports: [
                // MODULE IMPORTS
                platform_browser_1.BrowserModule,
                forms_1.FormsModule,
                forms_1.ReactiveFormsModule
            ],
            declarations: [
                // COMPONENTS IN THIS MODULE
                generic_form_component_1.GenericForm,
                form_text_input_component_1.FormTextInput,
                form_checkbox_input_component_1.FormCheckboxInput,
                form_long_text_input_component_1.FormLongTextInput,
                form_single_selection_input_component_1.FormSingleSelectionInput
            ],
            exports: [
                // THE COMPONENTS VISIBLE TO THE OUTSIDE
                generic_form_component_1.GenericForm
            ],
            providers: [],
            bootstrap: []
        })
    ], GenericFormModule);
    return GenericFormModule;
}());
exports.GenericFormModule = GenericFormModule;
//# sourceMappingURL=generic-form.module.js.map