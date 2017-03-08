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
var forms_1 = require('@angular/forms');
var core_module_1 = require('../../core/core.module');
var ceg_editor_component_1 = require('./ceg-editor.component');
var ceg_graphical_node_component_1 = require('./ceg-graphical-node.component');
var ceg_graphical_connection_component_1 = require('./ceg-graphical-connection.component');
var ceg_node_details_component_1 = require('./ceg-node-details.component');
var d3_ng2_service_1 = require('d3-ng2-service');
var CEGEditorModule = (function () {
    function CEGEditorModule() {
    }
    CEGEditorModule = __decorate([
        core_1.NgModule({
            imports: [
                core_module_1.CoreModule,
                forms_1.FormsModule,
                forms_1.ReactiveFormsModule
            ],
            exports: [],
            declarations: [
                ceg_editor_component_1.CEGEditor,
                ceg_graphical_node_component_1.CEGGraphicalNode,
                ceg_graphical_connection_component_1.CEGGraphicalConnection,
                ceg_node_details_component_1.CEGNodeDetails
            ],
            providers: [d3_ng2_service_1.D3Service],
        }), 
        __metadata('design:paramtypes', [])
    ], CEGEditorModule);
    return CEGEditorModule;
}());
exports.CEGEditorModule = CEGEditorModule;
//# sourceMappingURL=ceg-editor.module.js.map