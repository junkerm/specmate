"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var forms_1 = require("@angular/forms");
var core_module_1 = require("../../core/core.module");
var specmate_forms_module_1 = require("../../forms/specmate-forms.module");
var ceg_model_details_component_1 = require("./ceg-model-details.component");
var graphical_editor_component_1 = require("./graphical-editor.component");
var ceg_graphical_node_component_1 = require("./ceg-graphical-node.component");
var ceg_graphical_connection_component_1 = require("./ceg-graphical-connection.component");
var graphical_element_details_component_1 = require("./graphical-element-details.component");
var ceg_graphical_arc_component_1 = require("./ceg-graphical-arc.component");
var ModelEditorModule = (function () {
    function ModelEditorModule() {
    }
    ModelEditorModule = __decorate([
        core_1.NgModule({
            imports: [
                core_module_1.CoreModule,
                forms_1.FormsModule,
                specmate_forms_module_1.SpecmateFormsModule
            ],
            exports: [],
            declarations: [
                ceg_model_details_component_1.CEGModelDetails,
                graphical_editor_component_1.GraphicalEditor,
                ceg_graphical_node_component_1.CEGGraphicalNode,
                ceg_graphical_arc_component_1.CEGGraphicalArc,
                ceg_graphical_connection_component_1.CEGGraphicalConnection,
                graphical_element_details_component_1.GraphicalElementDetails
            ],
            providers: [],
        })
    ], ModelEditorModule);
    return ModelEditorModule;
}());
exports.ModelEditorModule = ModelEditorModule;
//# sourceMappingURL=model-editor.module.js.map