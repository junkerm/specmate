"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require('@angular/core');
var platform_browser_1 = require('@angular/platform-browser');
var router_1 = require('@angular/router');
var navigation_bar_component_1 = require('./navigation-bar.component');
var operation_monitor_component_1 = require('./operation-monitor.component');
var element_tree_component_1 = require('./element-tree.component');
var project_explorer_component_1 = require('./project-explorer.component');
var url_breadcrumb_component_1 = require('./url-breadcrumb.component');
var pipe_module_1 = require('../../util/pipe.module');
var forms_module_1 = require("./forms/forms.module");
var CoreModule = (function () {
    function CoreModule() {
    }
    CoreModule = __decorate([
        core_1.NgModule({
            imports: [
                platform_browser_1.BrowserModule,
                router_1.RouterModule,
                pipe_module_1.PipeModule,
                forms_module_1.FormsModule
            ],
            declarations: [
                navigation_bar_component_1.NavigationBar,
                project_explorer_component_1.ProjectExplorer,
                element_tree_component_1.ElementTree,
                url_breadcrumb_component_1.UrlBreadcrumb,
                operation_monitor_component_1.OperationMonitor
            ],
            providers: [],
            bootstrap: [],
            exports: [
                platform_browser_1.BrowserModule,
                router_1.RouterModule,
                project_explorer_component_1.ProjectExplorer,
                navigation_bar_component_1.NavigationBar,
                operation_monitor_component_1.OperationMonitor,
                url_breadcrumb_component_1.UrlBreadcrumb,
                forms_module_1.FormsModule,
                pipe_module_1.PipeModule
            ],
            entryComponents: []
        }), 
        __metadata('design:paramtypes', [])
    ], CoreModule);
    return CoreModule;
}());
exports.CoreModule = CoreModule;
//# sourceMappingURL=core.module.js.map