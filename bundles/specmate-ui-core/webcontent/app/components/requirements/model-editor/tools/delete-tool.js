"use strict";
var CEGNode_1 = require('../../../../model/CEGNode');
var CEGConnection_1 = require('../../../../model/CEGConnection');
var Type_1 = require('../../../../util/Type');
var CEGEffectNode_1 = require('../../../../model/CEGEffectNode');
var CEGCauseNode_1 = require('../../../../model/CEGCauseNode');
var DeleteTool = (function () {
    function DeleteTool(parent, dataService) {
        this.parent = parent;
        this.dataService = dataService;
        this.name = 'Delete';
        this.icon = 'trash';
        this.color = 'danger';
        this.selectedElements = [];
    }
    DeleteTool.prototype.activate = function () { };
    DeleteTool.prototype.deactivate = function () { };
    DeleteTool.prototype.click = function (event) { };
    DeleteTool.prototype.select = function (element) {
        var _this = this;
        this.getConnections(element)
            .then(function (connections) {
            for (var i = 0; i < connections.length; i++) {
                _this.dataService.deleteElement(connections[i].url, true);
            }
        })
            .then(function () {
            _this.dataService.deleteElement(element.url, true);
        });
    };
    DeleteTool.prototype.getConnections = function (node) {
        var _this = this;
        if (Type_1.Type.is(node, CEGNode_1.CEGNode) || Type_1.Type.is(node, CEGCauseNode_1.CEGCauseNode) || Type_1.Type.is(node, CEGEffectNode_1.CEGEffectNode)) {
            return this.dataService.readContents(this.parent.url, true)
                .then(function (contents) {
                return _this.getConnectionsOfNode(node, contents);
            });
        }
        return Promise.resolve([]);
    };
    DeleteTool.prototype.getConnectionsOfNode = function (node, contents) {
        var connections = [];
        for (var i = 0; i < contents.length; i++) {
            var currentElement = contents[i];
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