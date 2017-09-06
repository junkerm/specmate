"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var requirement_details_component_1 = require("./requirement-details.component");
var core_module_1 = require("../core/core.module");
var specmate_forms_module_1 = require("../forms/specmate-forms.module");
var model_editor_module_1 = require("./model-editor/model-editor.module");
var RequirementsModule = (function () {
    function RequirementsModule() {
    }
    RequirementsModule = __decorate([
        core_1.NgModule({
            imports: [
                core_module_1.CoreModule,
                model_editor_module_1.ModelEditorModule,
                specmate_forms_module_1.SpecmateFormsModule
            ],
            declarations: [
                requirement_details_component_1.RequirementsDetails
            ],
            providers: [],
            exports: [],
        })
    ], RequirementsModule);
    return RequirementsModule;
}());
exports.RequirementsModule = RequirementsModule;
//# sourceMappingURL=requirements.module.js.map