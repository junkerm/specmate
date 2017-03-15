"use strict";
var CEGNode_1 = require("../../../../model/CEGNode");
var config_1 = require("../../../../config/config");
var Id_1 = require("../../../../util/Id");
var Url_1 = require("../../../../util/Url");
var NodeTool = (function () {
    function NodeTool(parent, contents, dataService) {
        this.parent = parent;
        this.contents = contents;
        this.dataService = dataService;
        this.name = "Add Node";
        this.icon = "plus";
        this.color = "primary";
        this.selectedElements = [];
    }
    Object.defineProperty(NodeTool.prototype, "newNode", {
        get: function () {
            return new CEGNode_1.CEGNode();
        },
        enumerable: true,
        configurable: true
    });
    NodeTool.prototype.activate = function () {
        this.selectedElements = [];
    };
    NodeTool.prototype.deactivate = function () {
        this.selectedElements = [];
    };
    NodeTool.prototype.click = function (event) {
        this.addNode(event.offsetX, event.offsetY);
    };
    NodeTool.prototype.select = function (element) { };
    NodeTool.prototype.addNode = function (x, y) {
        var node = this.createNewNode(x, y);
        this.dataService.addElement(node);
        this.selectedElements[0] = node;
    };
    NodeTool.prototype.createNewNode = function (x, y) {
        var id = Id_1.Id.generate(this.contents, config_1.Config.CEG_NODE_BASE_ID);
        var url = Url_1.Url.build([this.parent.url, id]);
        var node = this.newNode;
        node.name = config_1.Config.CEG_NEW_NODE_NAME;
        node.description = config_1.Config.CEG_NEW_NODE_DESCRIPTION;
        node.id = id;
        node.url = url;
        node.x = x;
        node.y = y;
        return node;
    };
    return NodeTool;
}());
exports.NodeTool = NodeTool;
//# sourceMappingURL=node-tool.js.map