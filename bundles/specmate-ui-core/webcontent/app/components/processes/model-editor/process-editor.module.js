"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var forms_1 = require("@angular/forms");
var core_module_1 = require("../../core/core.module");
var specmate_forms_module_1 = require("../../forms/specmate-forms.module");
var process_editor_component_1 = require("./process-editor.component");
var platform_browser_1 = require("@angular/platform-browser");
var ProcessEditorModule = (function () {
    function ProcessEditorModule() {
    }
    ProcessEditorModule = __decorate([
        core_1.NgModule({
            imports: [
                core_module_1.CoreModule,
                forms_1.FormsModule,
                platform_browser_1.BrowserModule,
                specmate_forms_module_1.SpecmateFormsModule
            ],
            exports: [
                process_editor_component_1.ProcessEditor
            ],
            declarations: [
                process_editor_component_1.ProcessEditor
            ],
            providers: []
        })
    ], ProcessEditorModule);
    return ProcessEditorModule;
}());
exports.ProcessEditorModule = ProcessEditorModule;
//# sourceMappingURL=process-editor.module.js.map