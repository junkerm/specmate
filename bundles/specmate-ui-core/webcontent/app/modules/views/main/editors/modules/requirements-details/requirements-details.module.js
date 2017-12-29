"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var requirement_details_component_1 = require("./components/requirement-details.component");
var platform_browser_1 = require("@angular/platform-browser");
var navigator_module_1 = require("../../../../../navigation/modules/navigator/navigator.module");
var truncate_module_1 = require("../../../../../common/modules/truncate/truncate.module");
var test_specification_generator_button_module_1 = require("../../../../../actions/modules/test-specification-generator-button/test-specification-generator-button.module");
var icons_module_1 = require("../../../../../common/modules/icons/icons.module");
var RequirementsDetailsModule = /** @class */ (function () {
    function RequirementsDetailsModule() {
    }
    RequirementsDetailsModule = __decorate([
        core_1.NgModule({
            imports: [
                // MODULE IMPORTS
                platform_browser_1.BrowserModule,
                navigator_module_1.NavigatorModule,
                truncate_module_1.TruncateModule,
                test_specification_generator_button_module_1.TestSpecificationGeneratorButtonModule,
                icons_module_1.IconsModule
            ],
            declarations: [
                // COMPONENTS IN THIS MODULE
                requirement_details_component_1.RequirementsDetails
            ],
            exports: [
                // THE COMPONENTS VISIBLE TO THE OUTSIDE
                requirement_details_component_1.RequirementsDetails
            ],
            providers: [],
            bootstrap: []
        })
    ], RequirementsDetailsModule);
    return RequirementsDetailsModule;
}());
exports.RequirementsDetailsModule = RequirementsDetailsModule;
//# sourceMappingURL=requirements-details.module.js.map