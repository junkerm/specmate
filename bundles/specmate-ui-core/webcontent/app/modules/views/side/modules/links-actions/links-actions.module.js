"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var links_actions_component_1 = require("./components/links-actions.component");
var platform_browser_1 = require("@angular/platform-browser");
var navigator_module_1 = require("../../../../navigation/modules/navigator/navigator.module");
var test_specification_generator_button_module_1 = require("../../../../actions/modules/test-specification-generator-button/test-specification-generator-button.module");
var export_to_alm_button_module_1 = require("../../../../actions/modules/export-to-alm-button/export-to-alm-button.module");
var additional_information_service_1 = require("./services/additional-information.service");
var LinksActionsModule = /** @class */ (function () {
    function LinksActionsModule() {
    }
    LinksActionsModule = __decorate([
        core_1.NgModule({
            imports: [
                // MODULE IMPORTS
                platform_browser_1.BrowserModule,
                navigator_module_1.NavigatorModule,
                test_specification_generator_button_module_1.TestSpecificationGeneratorButtonModule,
                export_to_alm_button_module_1.ExportToALMButtonModule
            ],
            declarations: [
                // COMPONENTS IN THIS MODULE
                links_actions_component_1.LinksActions
            ],
            exports: [
                // THE COMPONENTS VISIBLE TO THE OUTSIDE
                links_actions_component_1.LinksActions
            ],
            providers: [
                // SERVICES
                additional_information_service_1.AdditionalInformationService
            ],
            bootstrap: []
        })
    ], LinksActionsModule);
    return LinksActionsModule;
}());
exports.LinksActionsModule = LinksActionsModule;
//# sourceMappingURL=links-actions.module.js.map