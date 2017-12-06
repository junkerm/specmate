"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var project_explorer_component_1 = require("./components/project-explorer.component");
var platform_browser_1 = require("@angular/platform-browser");
var element_tree_component_1 = require("./components/element-tree.component");
var navigator_module_1 = require("../navigator/navigator.module");
var ProjectExplorerModule = /** @class */ (function () {
    function ProjectExplorerModule() {
    }
    ProjectExplorerModule = __decorate([
        core_1.NgModule({
            imports: [
                // MODULE IMPORTS
                platform_browser_1.BrowserModule,
                navigator_module_1.NavigatorModule
            ],
            declarations: [
                // COMPONENTS IN THIS MODULE
                project_explorer_component_1.ProjectExplorer,
                element_tree_component_1.ElementTree
            ],
            exports: [
                // THE COMPONENTS VISIBLE TO THE OUTSIDE
                project_explorer_component_1.ProjectExplorer
            ],
            providers: [],
            bootstrap: []
        })
    ], ProjectExplorerModule);
    return ProjectExplorerModule;
}());
exports.ProjectExplorerModule = ProjectExplorerModule;
//# sourceMappingURL=project-explorer.module.js.map