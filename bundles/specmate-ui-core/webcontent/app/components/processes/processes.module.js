"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var specmate_forms_module_1 = require("../forms/specmate-forms.module");
var process_details_component_1 = require("./process-details.component");
var platform_browser_1 = require("@angular/platform-browser");
var graphical_module_1 = require("../core/graphical/graphical.module");
var ProcessesModule = (function () {
    function ProcessesModule() {
    }
    ProcessesModule = __decorate([
        core_1.NgModule({
            imports: [
                platform_browser_1.BrowserModule,
                specmate_forms_module_1.SpecmateFormsModule,
                graphical_module_1.GraphicalModule
            ],
            declarations: [
                process_details_component_1.ProcessDetails
            ],
            providers: [],
            exports: [],
        })
    ], ProcessesModule);
    return ProcessesModule;
}());
exports.ProcessesModule = ProcessesModule;
//# sourceMappingURL=processes.module.js.map