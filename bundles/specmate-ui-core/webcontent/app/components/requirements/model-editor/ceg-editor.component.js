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
Object.defineProperty(exports, "__esModule", { value: true });
var Id_1 = require("../../../util/Id");
var confirmation_modal_service_1 = require("../../core/forms/confirmation-modal.service");
var Type_1 = require("../../../util/Type");
var ceg_node_details_component_1 = require("./ceg-node-details.component");
var core_1 = require("@angular/core");
var config_1 = require("../../../config/config");
var CEGModel_1 = require("../../../model/CEGModel");
var CEGNode_1 = require("../../../model/CEGNode");
var CEGCauseNode_1 = require("../../../model/CEGCauseNode");
var CEGEffectNode_1 = require("../../../model/CEGEffectNode");
var CEGConnection_1 = require("../../../model/CEGConnection");
var delete_tool_1 = require("./tools/delete-tool");
var connection_tool_1 = require("./tools/connection-tool");
var move_tool_1 = require("./tools/move-tool");
var node_tool_1 = require("./tools/node-tool");
var specmate_data_service_1 = require("../../../services/specmate-data.service");
var ceg_graphical_connection_component_1 = require("./ceg-graphical-connection.component");
var CEGEditor = (function () {
    function CEGEditor(dataService, changeDetectorRef, modal) {
        this.dataService = dataService;
        this.changeDetectorRef = changeDetectorRef;
        this.modal = modal;
        this.causeNodeType = CEGCauseNode_1.CEGCauseNode;
        this.nodeType = CEGNode_1.CEGNode;
        this.effectNodeType = CEGEffectNode_1.CEGEffectNode;
        this.connectionType = CEGConnection_1.CEGConnection;
    }
    Object.defineProperty(CEGEditor.prototype, "graphicalConnections", {
        set: function (graphicalConnections) {
            this._graphicalConnections = graphicalConnections;
            this.changeDetectorRef.detectChanges();
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGEditor.prototype, "contents", {
        get: function () {
            return this._contents;
        },
        set: function (contents) {
            this._contents = contents;
            if (!this.tools) {
                return;
            }
            this.activateDefaultTool();
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGEditor.prototype, "editorDimensions", {
        get: function () {
            var dynamicWidth = config_1.Config.CEG_EDITOR_WIDTH;
            var dynamicHeight = config_1.Config.CEG_EDITOR_HEIGHT;
            var nodes = this.contents.filter(function (element) {
                return Type_1.Type.is(element, CEGNode_1.CEGNode) || Type_1.Type.is(element, CEGCauseNode_1.CEGCauseNode) || Type_1.Type.is(element, CEGEffectNode_1.CEGEffectNode);
            });
            for (var i = 0; i < nodes.length; i++) {
                var nodeX = nodes[i].x + (config_1.Config.CEG_NODE_WIDTH);
                if (dynamicWidth < nodeX) {
                    dynamicWidth = nodeX;
                }
                var nodeY = nodes[i].y + (config_1.Config.CEG_NODE_HEIGHT);
                if (dynamicHeight < nodeY) {
                    dynamicHeight = nodeY;
                }
            }
            return { width: dynamicWidth, height: dynamicHeight };
        },
        enumerable: true,
        configurable: true
    });
    CEGEditor.prototype.ngOnInit = function () {
        this.tools = [
            new move_tool_1.MoveTool(),
            // new CauseNodeTool(this.container, this.contents, this.dataService),
            // new EffectNodeTool(this.container, this.contents, this.dataService),
            new node_tool_1.NodeTool(this.model, this.dataService),
            new connection_tool_1.ConnectionTool(this.model, this.dataService),
            new delete_tool_1.DeleteTool(this.model, this.dataService)
        ];
    };
    Object.defineProperty(CEGEditor.prototype, "cursor", {
        get: function () {
            if (this.activeTool) {
                return this.activeTool.cursor;
            }
            return 'auto';
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGEditor.prototype, "isValid", {
        get: function () {
            if (!this.nodeDetails) {
                return true;
            }
            return !this.nodeDetails.some(function (details) { return !details.isValid; });
        },
        enumerable: true,
        configurable: true
    });
    CEGEditor.prototype.isValidElement = function (element) {
        var nodeDetail = this.nodeDetails.find(function (details) { return details.element === element; });
        if (!nodeDetail) {
            return true;
        }
        return nodeDetail.isValid;
    };
    CEGEditor.prototype.getGraphicalConnections = function (node) {
        if (!this._graphicalConnections) {
            return [];
        }
        return this._graphicalConnections.filter(function (connection) { return connection.connection.target.url === node.url; });
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
        var _this = this;
        if (this.activeTool) {
            this.activeTool.select(element).then(function () {
                if (_this.activeTool.done) {
                    _this.activateDefaultTool();
                }
            });
        }
    };
    CEGEditor.prototype.click = function (evt) {
        var _this = this;
        if (this.activeTool) {
            this.activeTool.click(evt).then(function () {
                if (_this.activeTool.done) {
                    _this.activateDefaultTool();
                }
            });
        }
    };
    CEGEditor.prototype.reset = function () {
        if (this.activeTool) {
            this.activeTool.deactivate();
            this.activeTool.activate();
        }
    };
    CEGEditor.prototype.activateDefaultTool = function () {
        if (this.contents && this.contents.length > 0) {
            this.activate(this.tools[0]);
        }
        else {
            this.activate(this.tools[1]);
        }
    };
    Object.defineProperty(CEGEditor.prototype, "nodes", {
        get: function () {
            return this.contents.filter(function (element) { return Type_1.Type.is(element, CEGNode_1.CEGNode) || Type_1.Type.is(element, CEGCauseNode_1.CEGCauseNode) || Type_1.Type.is(element, CEGEffectNode_1.CEGEffectNode); });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGEditor.prototype, "connections", {
        get: function () {
            return this.contents.filter(function (element) { return Type_1.Type.is(element, CEGConnection_1.CEGConnection); });
        },
        enumerable: true,
        configurable: true
    });
    CEGEditor.prototype.delete = function () {
        var _this = this;
        this.modal.open('Do you really want to delete all elements in ' + this.model.name + '?')
            .then(function () { return _this.removeAllElements(); })
            .catch(function () { });
    };
    CEGEditor.prototype.removeAllElements = function () {
        var compoundId = Id_1.Id.uuid;
        for (var i = this.connections.length - 1; i >= 0; i--) {
            this.dataService.deleteElement(this.connections[i].url, true, compoundId);
        }
        for (var i = this.nodes.length - 1; i >= 0; i--) {
            this.dataService.deleteElement(this.nodes[i].url, true, compoundId);
        }
    };
    __decorate([
        core_1.ViewChildren(ceg_node_details_component_1.CEGNodeDetails),
        __metadata("design:type", core_1.QueryList)
    ], CEGEditor.prototype, "nodeDetails", void 0);
    __decorate([
        core_1.ViewChildren(ceg_graphical_connection_component_1.CEGGraphicalConnection),
        __metadata("design:type", core_1.QueryList),
        __metadata("design:paramtypes", [core_1.QueryList])
    ], CEGEditor.prototype, "graphicalConnections", null);
    __decorate([
        core_1.Input(),
        __metadata("design:type", CEGModel_1.CEGModel)
    ], CEGEditor.prototype, "model", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Array),
        __metadata("design:paramtypes", [Array])
    ], CEGEditor.prototype, "contents", null);
    CEGEditor = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'ceg-editor',
            templateUrl: 'ceg-editor.component.html',
            styleUrls: ['ceg-editor.component.css'],
            changeDetection: core_1.ChangeDetectionStrategy.Default
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService, core_1.ChangeDetectorRef, confirmation_modal_service_1.ConfirmationModal])
    ], CEGEditor);
    return CEGEditor;
}());
exports.CEGEditor = CEGEditor;
//# sourceMappingURL=ceg-editor.component.js.map