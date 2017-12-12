"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var process_details_component_1 = require("./components/process-details.component");
var graphical_editor_module_1 = require("../graphical-editor/graphical-editor.module");
var platform_browser_1 = require("@angular/platform-browser");
var tool_pallette_module_1 = require("../tool-pallette/tool-pallette.module");
var validation_module_1 = require("../../../../../forms/modules/validation/validation.module");
var ProcessEditorModule = /** @class */ (function () {
    function ProcessEditorModule() {
    }
    ProcessEditorModule = __decorate([
        core_1.NgModule({
            imports: [
                // MODULE IMPORTS
                graphical_editor_module_1.GraphicalEditorModule,
                platform_browser_1.BrowserModule,
                tool_pallette_module_1.ToolPalletteModule,
                validation_module_1.ValidationModule
            ],
            declarations: [
                // COMPONENTS IN THIS MODULE
                process_details_component_1.ProcessDetails
            ],
            exports: [
                // THE COMPONENTS VISIBLE TO THE OUTSIDE
                process_details_component_1.ProcessDetails
            ],
            providers: [],
            bootstrap: []
        })
    ], ProcessEditorModule);
    return ProcessEditorModule;
}());
exports.ProcessEditorModule = ProcessEditorModule;
//# sourceMappingURL=process-editor.module.js.map