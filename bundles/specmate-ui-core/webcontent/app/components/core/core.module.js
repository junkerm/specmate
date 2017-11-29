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
var router_1 = require("@angular/router");
var forms_1 = require("@angular/forms");
var ng_bootstrap_1 = require("@ng-bootstrap/ng-bootstrap");
var navigation_bar_component_1 = require("./common/navigation-bar.component");
var operation_monitor_component_1 = require("./common/operation-monitor.component");
var common_controls_component_1 = require("./common/common-controls.component");
var element_tree_component_1 = require("./explorer/element-tree.component");
var project_explorer_component_1 = require("./explorer/project-explorer.component");
var log_list_component_1 = require("./logging/log-list.component");
var log_entry_component_1 = require("./logging/log-entry.component");
var confirmation_modal_content_component_1 = require("./notification/confirmation-modal-content.component");
var error_modal_content_component_1 = require("./notification/error-modal-content.component");
var pipe_module_1 = require("../../pipes/pipe.module");
var navigation_target_directive_1 = require("../../directives/navigation-target.directive");
var test_specification_generator_button_component_1 = require("./common/test-specification-generator-button.component");
var graphical_module_1 = require("./graphical/graphical.module");
var properties_editor_component_1 = require("./selection/properties-editor.component");
var specmate_forms_module_1 = require("../forms/specmate-forms.module");
var CoreModule = (function () {
    function CoreModule() {
    }
    CoreModule = __decorate([
        core_1.NgModule({
            imports: [
                graphical_module_1.GraphicalModule,
                platform_browser_1.BrowserModule,
                router_1.RouterModule,
                pipe_module_1.PipeModule,
                forms_1.FormsModule,
                forms_1.ReactiveFormsModule,
                ng_bootstrap_1.NgbModule,
                specmate_forms_module_1.SpecmateFormsModule
            ],
            declarations: [
                navigation_bar_component_1.NavigationBar,
                project_explorer_component_1.ProjectExplorer,
                element_tree_component_1.ElementTree,
                operation_monitor_component_1.OperationMonitor,
                common_controls_component_1.CommonControls,
                log_list_component_1.LogList,
                log_entry_component_1.LogEntry,
                navigation_target_directive_1.NavigationTargetDirective,
                confirmation_modal_content_component_1.ConfirmationModalContent,
                error_modal_content_component_1.ErrorModalContent,
                test_specification_generator_button_component_1.TestSpecificationGeneratorButton,
                properties_editor_component_1.PropertiesEditor
            ],
            providers: [],
            bootstrap: [],
            exports: [
                platform_browser_1.BrowserModule,
                router_1.RouterModule,
                project_explorer_component_1.ProjectExplorer,
                navigation_bar_component_1.NavigationBar,
                operation_monitor_component_1.OperationMonitor,
                common_controls_component_1.CommonControls,
                log_list_component_1.LogList,
                log_entry_component_1.LogEntry,
                pipe_module_1.PipeModule,
                navigation_target_directive_1.NavigationTargetDirective,
                test_specification_generator_button_component_1.TestSpecificationGeneratorButton,
                properties_editor_component_1.PropertiesEditor
            ],
            entryComponents: [confirmation_modal_content_component_1.ConfirmationModalContent, error_modal_content_component_1.ErrorModalContent]
        })
    ], CoreModule);
    return CoreModule;
}());
exports.CoreModule = CoreModule;
//# sourceMappingURL=core.module.js.map