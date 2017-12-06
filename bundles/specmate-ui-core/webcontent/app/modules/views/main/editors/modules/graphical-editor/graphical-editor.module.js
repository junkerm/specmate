"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var graphical_editor_component_1 = require("./components/graphical-editor.component");
var maximize_button_module_1 = require("../maximize-button/maximize-button.module");
var platform_browser_1 = require("@angular/platform-browser");
var tool_pallette_module_1 = require("../tool-pallette/tool-pallette.module");
var ceg_graphical_connection_component_1 = require("./components/ceg/ceg-graphical-connection.component");
var ceg_graphical_node_component_1 = require("./components/ceg/ceg-graphical-node.component");
var process_graphical_connection_component_1 = require("./components/process/process-graphical-connection.component");
var process_start_graphical_node_component_1 = require("./components/process/process-start-graphical-node.component");
var process_end_graphical_node_component_1 = require("./components/process/process-end-graphical-node.component");
var process_step_graphical_node_component_1 = require("./components/process/process-step-graphical-node.component");
var process_decision_graphical_node_component_1 = require("./components/process/process-decision-graphical-node.component");
var ceg_graphical_arc_component_1 = require("./components/ceg/ceg-graphical-arc.component");
var connection_line_component_1 = require("./components/common/connection-line.component");
var arrow_tip_component_1 = require("./components/common/arrow-tip.component");
var truncated_text_component_1 = require("./components/common/truncated-text.component");
var GraphicalEditorModule = (function () {
    function GraphicalEditorModule() {
    }
    GraphicalEditorModule = __decorate([
        core_1.NgModule({
            imports: [
                // MODULE IMPORTS
                maximize_button_module_1.MaximizeButtonModule,
                tool_pallette_module_1.ToolPalletteModule,
                platform_browser_1.BrowserModule
            ],
            declarations: [
                // COMPONENTS IN THIS MODULE
                graphical_editor_component_1.GraphicalEditor,
                ceg_graphical_connection_component_1.CEGGraphicalConnection,
                ceg_graphical_node_component_1.CEGGraphicalNode,
                ceg_graphical_arc_component_1.CEGGraphicalArc,
                process_graphical_connection_component_1.ProcessGraphicalConnection,
                process_start_graphical_node_component_1.ProcessStartGraphicalNode,
                process_end_graphical_node_component_1.ProcessEndGraphicalNode,
                process_step_graphical_node_component_1.ProcessStepGraphicalNode,
                process_decision_graphical_node_component_1.ProcessDecisionGraphicalNode,
                connection_line_component_1.ConnectionLine,
                arrow_tip_component_1.ArrowTip,
                truncated_text_component_1.TruncatedText
            ],
            exports: [
                // THE COMPONENTS VISIBLE TO THE OUTSIDE
                graphical_editor_component_1.GraphicalEditor
            ],
            providers: [],
            bootstrap: []
        })
    ], GraphicalEditorModule);
    return GraphicalEditorModule;
}());
exports.GraphicalEditorModule = GraphicalEditorModule;
//# sourceMappingURL=graphical-editor.module.js.map