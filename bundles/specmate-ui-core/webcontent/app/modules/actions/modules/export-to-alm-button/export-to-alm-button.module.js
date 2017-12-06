"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var export_to_alm_button_component_1 = require("./components/export-to-alm-button.component");
var platform_browser_1 = require("@angular/platform-browser");
var ExportToALMButtonModule = (function () {
    function ExportToALMButtonModule() {
    }
    ExportToALMButtonModule = __decorate([
        core_1.NgModule({
            imports: [
                // MODULE IMPORTS
                platform_browser_1.BrowserModule
            ],
            declarations: [
                // COMPONENTS IN THIS MODULE
                export_to_alm_button_component_1.ExportToALMButton
            ],
            exports: [
                // THE COMPONENTS VISIBLE TO THE OUTSIDE
                export_to_alm_button_component_1.ExportToALMButton
            ],
            providers: [],
            bootstrap: []
        })
    ], ExportToALMButtonModule);
    return ExportToALMButtonModule;
}());
exports.ExportToALMButtonModule = ExportToALMButtonModule;
//# sourceMappingURL=export-to-alm-button.module.js.map