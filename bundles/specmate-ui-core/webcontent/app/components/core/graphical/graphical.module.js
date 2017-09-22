"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var graphical_editor_component_1 = require("./graphical-editor.component");
var graphical_element_details_component_1 = require("./graphical-element-details.component");
var ceg_graphical_node_component_1 = require("./elements/ceg/ceg-graphical-node.component");
var ceg_graphical_arc_component_1 = require("./elements/ceg/ceg-graphical-arc.component");
var ceg_graphical_connection_component_1 = require("./elements/ceg/ceg-graphical-connection.component");
var platform_browser_1 = require("@angular/platform-browser");
var pipe_module_1 = require("../../../pipes/pipe.module");
var specmate_forms_module_1 = require("../../forms/specmate-forms.module");
var process_step_graphical_node_component_1 = require("./elements/process/process-step-graphical-node.component");
var process_graphical_connection_component_1 = require("./elements/process/process-graphical-connection.component");
var process_decision_graphical_node_component_1 = require("./elements/process/process-decision-graphical-node.component");
var process_start_graphical_node_component_1 = require("./elements/process/process-start-graphical-node.component");
var process_end_graphical_node_component_1 = require("./elements/process/process-end-graphical-node.component");
var connection_line_component_1 = require("./elements/common/connection-line.component");
var arrow_tip_component_1 = require("./elements/common/arrow-tip.component");
var GraphicalModule = (function () {
    function GraphicalModule() {
    }
    GraphicalModule = __decorate([
        core_1.NgModule({
            imports: [
                platform_browser_1.BrowserModule,
                pipe_module_1.PipeModule,
                specmate_forms_module_1.SpecmateFormsModule
            ],
            declarations: [
                graphical_editor_component_1.GraphicalEditor,
                graphical_element_details_component_1.GraphicalElementDetails,
                connection_line_component_1.ConnectionLine,
                arrow_tip_component_1.ArrowTip,
                ceg_graphical_node_component_1.CEGGraphicalNode,
                ceg_graphical_arc_component_1.CEGGraphicalArc,
                ceg_graphical_connection_component_1.CEGGraphicalConnection,
                process_step_graphical_node_component_1.ProcessStepGraphicalNode,
                process_decision_graphical_node_component_1.ProcessDecisionGraphicalNode,
                process_start_graphical_node_component_1.ProcessStartGraphicalNode,
                process_end_graphical_node_component_1.ProcessEndGraphicalNode,
                process_graphical_connection_component_1.ProcessGraphicalConnection
            ],
            providers: [],
            bootstrap: [],
            exports: [
                graphical_editor_component_1.GraphicalEditor
            ],
            entryComponents: []
        })
    ], GraphicalModule);
    return GraphicalModule;
}());
exports.GraphicalModule = GraphicalModule;
//# sourceMappingURL=graphical.module.js.map