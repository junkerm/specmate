"use strict";
var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
Object.defineProperty(exports, "__esModule", { value: true });
var type_aware_tool_base_1 = require("./type-aware-tool-base");
var id_1 = require("../../../../../../../util/id");
var arrays_1 = require("../../../../../../../util/arrays");
var DeleteToolBase = (function (_super) {
    __extends(DeleteToolBase, _super);
    function DeleteToolBase(parent, dataService, selectedElementService) {
        var _this = _super.call(this, selectedElementService) || this;
        _this.parent = parent;
        _this.dataService = dataService;
        _this.name = 'Delete';
        _this.icon = 'eraser';
        _this.color = 'danger';
        _this.cursor = 'alias';
        _this.done = false;
        _this.selectedElements = [];
        return _this;
    }
    DeleteToolBase.prototype.activate = function () {
        this.done = false;
    };
    DeleteToolBase.prototype.deactivate = function () { };
    DeleteToolBase.prototype.click = function (event, zoom) {
        return Promise.resolve();
    };
    DeleteToolBase.prototype.select = function (element) {
        var _this = this;
        var compoundId = id_1.Id.uuid;
        return this.getConnections(element)
            .then(function (connections) {
            var chain = Promise.resolve();
            var _loop_1 = function (i) {
                chain = chain.then(function () {
                    return _this.deleteElementAndDeselect(connections[i], compoundId);
                });
            };
            for (var i = 0; i < connections.length; i++) {
                _loop_1(i);
            }
            return chain;
        })
            .then(function () {
            return _this.deleteElementAndDeselect(element, compoundId);
        }).then(function () {
            _this.done = true;
        });
    };
    DeleteToolBase.prototype.deleteElementAndDeselect = function (element, compoundId) {
        this.selectedElementService.deselect();
        if (this.isNode(element)) {
            this.deleteNode(element, compoundId);
            return;
        }
        else if (this.isConnection(element)) {
            return this.deleteConnection(element, compoundId);
        }
        throw new Error('Tried to delete element with type ' + element.className + '. This type is not supported.');
    };
    DeleteToolBase.prototype.deleteNode = function (node, compoundId) {
        return this.dataService.deleteElement(node.url, true, compoundId);
    };
    DeleteToolBase.prototype.deleteConnection = function (connection, compoundId) {
        var _this = this;
        return this.removeConnectionFromSource(connection, compoundId)
            .then(function () { return _this.removeConnectionFromTarget(connection, compoundId); })
            .then(function () { return _this.dataService.deleteElement(connection.url, true, compoundId); });
    };
    DeleteToolBase.prototype.removeConnectionFromSource = function (connection, compoundId) {
        var _this = this;
        return this.dataService.readElement(connection.source.url, true)
            .then(function (source) {
            var proxyToDelete = source.outgoingConnections.find(function (proxy) { return proxy.url === connection.url; });
            arrays_1.Arrays.remove(source.outgoingConnections, proxyToDelete);
            return source;
        })
            .then(function (source) { return _this.dataService.updateElement(source, true, compoundId); });
    };
    DeleteToolBase.prototype.removeConnectionFromTarget = function (connection, compoundId) {
        var _this = this;
        return this.dataService.readElement(connection.target.url, true)
            .then(function (target) {
            var proxyToDelete = target.incomingConnections.find(function (proxy) { return proxy.url === connection.url; });
            arrays_1.Arrays.remove(target.incomingConnections, proxyToDelete);
            return target;
        })
            .then(function (target) { return _this.dataService.updateElement(target, true, compoundId); });
    };
    DeleteToolBase.prototype.getConnections = function (node) {
        var _this = this;
        if (this.isNode(node)) {
            return this.dataService.readContents(this.parent.url, true)
                .then(function (contents) {
                return _this.getConnectionsOfNode(node, contents);
            });
        }
        return Promise.resolve([]);
    };
    DeleteToolBase.prototype.getConnectionsOfNode = function (node, contents) {
        var connections = [];
        for (var i = 0; i < contents.length; i++) {
            var currentElement = contents[i];
            if (this.isConnection(currentElement)) {
                var currentConnection = currentElement;
                if (currentConnection.source.url === node.url || currentConnection.target.url === node.url) {
                    connections.push(currentConnection);
                }
            }
        }
        return connections;
    };
    return DeleteToolBase;
}(type_aware_tool_base_1.TypeAwareToolBase));
exports.DeleteToolBase = DeleteToolBase;
//# sourceMappingURL=delete-tool-base.js.map