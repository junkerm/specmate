"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var navigation_bar_component_1 = require("./components/navigation-bar.component");
var common_controls_module_1 = require("../../../actions/modules/common-controls/common-controls.module");
var operation_monitor_module_1 = require("../../../notification/modules/operation-monitor/operation-monitor.module");
var NavigationBarModule = (function () {
    function NavigationBarModule() {
    }
    NavigationBarModule = __decorate([
        core_1.NgModule({
            imports: [
                // MODULE IMPORTS
                common_controls_module_1.CommonControlsModule,
                operation_monitor_module_1.OperationMonitorModule
            ],
            declarations: [
                // COMPONENTS IN THIS MODULE
                navigation_bar_component_1.NavigationBar
            ],
            exports: [
                // THE COMPONENTS VISIBLE TO THE OUTSIDE
                navigation_bar_component_1.NavigationBar
            ],
            providers: [],
            bootstrap: []
        })
    ], NavigationBarModule);
    return NavigationBarModule;
}());
exports.NavigationBarModule = NavigationBarModule;
//# sourceMappingURL=navigation-bar.module.js.map