"use strict";
var CEGNode_1 = require("../../../../model/CEGNode");
var CEGConnection_1 = require("../../../../model/CEGConnection");
var Type_1 = require("../../../../util/Type");
var CEGEffectNode_1 = require("../../../../model/CEGEffectNode");
var CEGCauseNode_1 = require("../../../../model/CEGCauseNode");
var DeleteTool = (function () {
    function DeleteTool(contents, dataService) {
        this.contents = contents;
        this.dataService = dataService;
        this.name = "Delete";
        this.icon = "trash";
        this.color = "danger";
        this.selectedElements = [];
    }
    DeleteTool.prototype.activate = function () { };
    DeleteTool.prototype.deactivate = function () { };
    DeleteTool.prototype.click = function (event) { };
    DeleteTool.prototype.select = function (element) {
        if (this.confirm()) {
            this.delete(element);
        }
    };
    DeleteTool.prototype.confirm = function () {
        return true;
    };
    DeleteTool.prototype.delete = function (element) {
        if (Type_1.Type.is(element, CEGNode_1.CEGNode) || Type_1.Type.is(element, CEGCauseNode_1.CEGCauseNode) || Type_1.Type.is(element, CEGEffectNode_1.CEGEffectNode)) {
            var connections = this.getConnections(element);
            for (var i = 0; i < connections.length; i++) {
                this.dataService.removeDetails(connections[i]);
            }
        }
        this.dataService.removeDetails(element);
    };
    DeleteTool.prototype.getConnections = function (node) {
        var connections = [];
        for (var i = 0; i < this.contents.length; i++) {
            var currentElement = this.contents[i];
            if (Type_1.Type.is(currentElement, CEGConnection_1.CEGConnection)) {
                var currentConnection = currentElement;
                if (currentConnection.source.url === node.url || currentConnection.target.url === node.url) {
                    connections.push(currentConnection);
                }
            }
        }
        return connections;
    };
    return DeleteTool;
}());
exports.DeleteTool = DeleteTool;
//# sourceMappingURL=delete-tool.js.map