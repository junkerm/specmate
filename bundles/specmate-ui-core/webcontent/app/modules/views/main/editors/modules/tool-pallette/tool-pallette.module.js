"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var tool_pallette_component_1 = require("./components/tool-pallette.component");
var platform_browser_1 = require("@angular/platform-browser");
var editor_tools_service_1 = require("./services/editor-tools.service");
var ToolPalletteModule = /** @class */ (function () {
    function ToolPalletteModule() {
    }
    ToolPalletteModule = __decorate([
        core_1.NgModule({
            imports: [
                // MODULE IMPORTS
                platform_browser_1.BrowserModule
            ],
            declarations: [
                // COMPONENTS IN THIS MODULE
                tool_pallette_component_1.ToolPallette
            ],
            exports: [
                // THE COMPONENTS VISIBLE TO THE OUTSIDE
                tool_pallette_component_1.ToolPallette
            ],
            providers: [
                // SERVICES
                editor_tools_service_1.EditorToolsService
            ],
            bootstrap: []
        })
    ], ToolPalletteModule);
    return ToolPalletteModule;
}());
exports.ToolPalletteModule = ToolPalletteModule;
//# sourceMappingURL=tool-pallette.module.js.map