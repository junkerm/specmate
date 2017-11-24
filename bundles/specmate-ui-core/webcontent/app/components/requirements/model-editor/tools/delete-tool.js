"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var Id_1 = require("../../../../util/Id");
var CEGNode_1 = require("../../../../model/CEGNode");
var CEGConnection_1 = require("../../../../model/CEGConnection");
var Type_1 = require("../../../../util/Type");
var CEGEffectNode_1 = require("../../../../model/CEGEffectNode");
var CEGCauseNode_1 = require("../../../../model/CEGCauseNode");
var Arrays_1 = require("../../../../util/Arrays");
var DeleteTool = (function () {
    function DeleteTool(parent, dataService) {
        this.parent = parent;
        this.dataService = dataService;
        this.name = 'Delete';
        this.icon = 'trash';
        this.color = 'danger';
        this.cursor = 'alias';
        this.done = false;
        this.selectedElements = [];
    }
    DeleteTool.prototype.activate = function () {
        this.done = false;
    };
    DeleteTool.prototype.deactivate = function () { };
    DeleteTool.prototype.click = function (event) {
        return Promise.resolve();
    };
    DeleteTool.prototype.select = function (element) {
        var _this = this;
        var compoundId = Id_1.Id.uuid;
        return this.getConnections(element)
            .then(function (connections) {
            var chain = Promise.resolve();
            var _loop_1 = function (i) {
                chain = chain.then(function () {
                    return _this.deleteElement(connections[i], compoundId);
                });
            };
            for (var i = 0; i < connections.length; i++) {
                _loop_1(i);
            }
            return chain;
        })
            .then(function () {
            return _this.deleteElement(element, compoundId);
        }).then(function () {
            _this.done = true;
        });
    };
    DeleteTool.prototype.deleteElement = function (element, compoundId) {
        if (Type_1.Type.is(element, CEGNode_1.CEGNode) || Type_1.Type.is(element, CEGCauseNode_1.CEGCauseNode) || Type_1.Type.is(element, CEGEffectNode_1.CEGEffectNode)) {
            this.deleteNode(element, compoundId);
            return;
        }
        else if (Type_1.Type.is(element, CEGConnection_1.CEGConnection)) {
            return this.deleteConnection(element, compoundId);
        }
        throw new Error('Tried to delete element with type ' + element.className + '. Only elements of tyoe CEGNode and CEGConnection are supported.');
    };
    DeleteTool.prototype.deleteNode = function (node, compoundId) {
        return this.dataService.deleteElement(node.url, true, compoundId);
    };
    DeleteTool.prototype.deleteConnection = function (connection, compoundId) {
        var _this = this;
        return this.removeConnectionFromSource(connection, compoundId)
            .then(function () { return _this.removeConnectionFromTarget(connection, compoundId); })
            .then(function () { return _this.dataService.deleteElement(connection.url, true, compoundId); });
    };
    DeleteTool.prototype.removeConnectionFromSource = function (connection, compoundId) {
        var _this = this;
        return this.dataService.readElement(connection.source.url, true)
            .then(function (source) {
            var proxyToDelete = source.outgoingConnections.find(function (proxy) { return proxy.url === connection.url; });
            Arrays_1.Arrays.remove(source.outgoingConnections, proxyToDelete);
            return source;
        })
            .then(function (source) { return _this.dataService.updateElement(source, true, compoundId); });
    };
    DeleteTool.prototype.removeConnectionFromTarget = function (connection, compoundId) {
        var _this = this;
        return this.dataService.readElement(connection.target.url, true)
            .then(function (target) {
            var proxyToDelete = target.incomingConnections.find(function (proxy) { return proxy.url === connection.url; });
            Arrays_1.Arrays.remove(target.incomingConnections, proxyToDelete);
            return target;
        })
            .then(function (target) { return _this.dataService.updateElement(target, true, compoundId); });
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