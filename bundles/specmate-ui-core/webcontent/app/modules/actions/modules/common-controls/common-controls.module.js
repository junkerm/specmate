"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var common_controls_component_1 = require("./components/common-controls.component");
var platform_browser_1 = require("@angular/platform-browser");
var common_control_service_1 = require("./services/common-control.service");
var CommonControlsModule = /** @class */ (function () {
    function CommonControlsModule() {
    }
    CommonControlsModule = __decorate([
        core_1.NgModule({
            imports: [
                // MODULE IMPORTS
                platform_browser_1.BrowserModule
            ],
            declarations: [
                // COMPONENTS IN THIS MODULE
                common_controls_component_1.CommonControls
            ],
            exports: [
                // THE COMPONENTS VISIBLE TO THE OUTSIDE
                common_controls_component_1.CommonControls
            ],
            providers: [
                // SERVICES
                common_control_service_1.EditorCommonControlService
            ],
            bootstrap: []
        })
    ], CommonControlsModule);
    return CommonControlsModule;
}());
exports.CommonControlsModule = CommonControlsModule;
//# sourceMappingURL=common-controls.module.js.map