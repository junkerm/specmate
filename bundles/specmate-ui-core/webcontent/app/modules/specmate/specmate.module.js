"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var platform_browser_1 = require("@angular/platform-browser");
var angular_split_1 = require("angular-split");
var specmate_component_1 = require("./components/specmate.component");
var navigation_bar_module_1 = require("../navigation/modules/navigation-bar/navigation-bar.module");
var project_explorer_module_1 = require("../navigation/modules/project-explorer/project-explorer.module");
var properties_editor_module_1 = require("../views/side/modules/properties-editor/properties-editor.module");
var links_actions_module_1 = require("../views/side/modules/links-actions/links-actions.module");
var log_list_module_1 = require("../views/side/modules/log-list/log-list.module");
var view_controller_module_1 = require("../views/controller/modules/view-controller/view-controller.module");
var selected_element_module_1 = require("../views/side/modules/selected-element/selected-element.module");
var navigator_module_1 = require("../navigation/modules/navigator/navigator.module");
var data_service_module_1 = require("../data/modules/data-service/data-service.module");
var specmate_routing_module_1 = require("./routing/specmate-routing.module");
var modals_module_1 = require("../notification/modules/modals/modals.module");
var SpecmateModule = /** @class */ (function () {
    function SpecmateModule() {
    }
    SpecmateModule = __decorate([
        core_1.NgModule({
            imports: [
                // MODULE IMPORTS
                platform_browser_1.BrowserModule,
                specmate_routing_module_1.SpecmateRoutingModule,
                navigation_bar_module_1.NavigationBarModule,
                angular_split_1.AngularSplitModule,
                project_explorer_module_1.ProjectExplorerModule,
                properties_editor_module_1.PropertiesEditorModule,
                links_actions_module_1.LinksActionsModule,
                log_list_module_1.LogListModule,
                view_controller_module_1.ViewControllerModule,
                selected_element_module_1.SelectedElementModule,
                navigator_module_1.NavigatorModule,
                data_service_module_1.DataServiceModule,
                modals_module_1.ModalsModule
            ],
            declarations: [
                // COMPONENTS IN THIS MODULE
                specmate_component_1.SpecmateComponent
            ],
            exports: [],
            providers: [],
            bootstrap: [
                // COMPONENTS THAT ARE BOOTSTRAPPED HERE
                specmate_component_1.SpecmateComponent
            ]
        })
    ], SpecmateModule);
    return SpecmateModule;
}());
exports.SpecmateModule = SpecmateModule;
//# sourceMappingURL=specmate.module.js.map