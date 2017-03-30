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
var ceg_node_details_component_1 = require('./ceg-node-details.component');
var core_1 = require('@angular/core');
var router_1 = require('@angular/router');
var CEGModel_1 = require('../../../model/CEGModel');
var CEGNode_1 = require('../../../model/CEGNode');
var CEGCauseNode_1 = require('../../../model/CEGCauseNode');
var CEGEffectNode_1 = require('../../../model/CEGEffectNode');
var CEGConnection_1 = require('../../../model/CEGConnection');
var delete_tool_1 = require('./tools/delete-tool');
var connection_tool_1 = require('./tools/connection-tool');
var move_tool_1 = require('./tools/move-tool');
var node_tool_1 = require('./tools/node-tool');
var specmate_data_service_1 = require("../../../services/specmate-data.service");
var CEGEditor = (function () {
    function CEGEditor(dataService, router, route) {
        this.dataService = dataService;
        this.router = router;
        this.route = route;
        this.editorHeight = (isNaN(window.innerHeight) ? window['clientHeight'] : window.innerHeight) * 0.75;
        this.causeNodeType = CEGCauseNode_1.CEGCauseNode;
        this.nodeType = CEGNode_1.CEGNode;
        this.effectNodeType = CEGEffectNode_1.CEGEffectNode;
        this.connectionType = CEGConnection_1.CEGConnection;
    }
    CEGEditor.prototype.ngOnInit = function () {
        this.tools = [
            new move_tool_1.MoveTool(),
            // new CauseNodeTool(this.container, this.contents, this.dataService),
            // new EffectNodeTool(this.container, this.contents, this.dataService),
            new node_tool_1.NodeTool(this.model, this.dataService),
            new connection_tool_1.ConnectionTool(this.model, this.dataService),
            new delete_tool_1.DeleteTool(this.model, this.dataService)
        ];
        this.activate(this.tools[0]);
    };
    CEGEditor.prototype.activate = function (tool) {
        if (!tool) {
            return;
        }
        if (this.activeTool) {
            this.activeTool.deactivate();
        }
        this.activeTool = tool;
        this.activeTool.activate();
    };
    CEGEditor.prototype.isActive = function (tool) {
        return this.activeTool === tool;
    };
    Object.defineProperty(CEGEditor.prototype, "selectedNodes", {
        get: function () {
            if (this.activeTool) {
                return this.activeTool.selectedElements;
            }
            return [];
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGEditor.prototype, "selectedNode", {
        get: function () {
            var selectedNodes = this.selectedNodes;
            if (selectedNodes.length > 0) {
                return selectedNodes[selectedNodes.length - 1];
            }
            return undefined;
        },
        enumerable: true,
        configurable: true
    });
    CEGEditor.prototype.isSelected = function (element) {
        return this.selectedNodes.indexOf(element) >= 0;
    };
    CEGEditor.prototype.select = function (element) {
        if (this.activeTool) {
            this.activeTool.select(element);
        }
    };
    CEGEditor.prototype.click = function (evt) {
        if (this.activeTool) {
            this.activeTool.click(evt);
        }
    };
    CEGEditor.prototype.update = function () {
        this.nodeDetails.update();
    };
    CEGEditor.prototype.reset = function () {
        if (this.activeTool) {
            this.activeTool.deactivate();
            this.activeTool.activate();
        }
        this.update();
    };
    __decorate([
        core_1.ViewChild(ceg_node_details_component_1.CEGNodeDetails), 
        __metadata('design:type', ceg_node_details_component_1.CEGNodeDetails)
    ], CEGEditor.prototype, "nodeDetails", void 0);
    __decorate([
        core_1.Input(), 
        __metadata('design:type', CEGModel_1.CEGModel)
    ], CEGEditor.prototype, "model", void 0);
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Array)
    ], CEGEditor.prototype, "contents", void 0);
    CEGEditor = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'ceg-editor',
            templateUrl: 'ceg-editor.component.html'
        }), 
        __metadata('design:paramtypes', [specmate_data_service_1.SpecmateDataService, router_1.Router, router_1.ActivatedRoute])
    ], CEGEditor);
    return CEGEditor;
}());
exports.CEGEditor = CEGEditor;
//# sourceMappingURL=ceg-editor.component.js.map