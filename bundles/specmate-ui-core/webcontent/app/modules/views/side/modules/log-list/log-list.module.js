"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var log_list_component_1 = require("./components/log-list.component");
var forms_1 = require("@angular/forms");
var platform_browser_1 = require("@angular/platform-browser");
var log_entry_component_1 = require("./components/log-entry.component");
var logging_service_1 = require("./services/logging.service");
var LogListModule = (function () {
    function LogListModule() {
    }
    LogListModule = __decorate([
        core_1.NgModule({
            imports: [
                // MODULE IMPORTS
                platform_browser_1.BrowserModule,
                forms_1.FormsModule,
                forms_1.ReactiveFormsModule
            ],
            declarations: [
                // COMPONENTS IN THIS MODULE
                log_list_component_1.LogList,
                log_entry_component_1.LogEntry
            ],
            exports: [
                // THE COMPONENTS VISIBLE TO THE OUTSIDE
                log_list_component_1.LogList
            ],
            providers: [
                // SERVICES
                logging_service_1.LoggingService
            ],
            bootstrap: []
        })
    ], LogListModule);
    return LogListModule;
}());
exports.LogListModule = LogListModule;
//# sourceMappingURL=log-list.module.js.map