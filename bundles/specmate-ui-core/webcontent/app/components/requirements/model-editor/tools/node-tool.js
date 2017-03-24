"use strict";
var CEGNode_1 = require("../../../../model/CEGNode");
var config_1 = require("../../../../config/config");
var Id_1 = require("../../../../util/Id");
var Url_1 = require("../../../../util/Url");
var NodeTool = (function () {
    function NodeTool(parent, dataService) {
        this.parent = parent;
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
        return this.createNewNode(event.offsetX, event.offsetY);
    };
    NodeTool.prototype.select = function (element) {
        this.selectedElements[0] = element;
        return Promise.resolve();
    };
    NodeTool.prototype.createNewNode = function (x, y) {
        var _this = this;
        return this.dataService.readContents(this.parent.url, true).then(function (contents) {
            var id = Id_1.Id.generate(contents, config_1.Config.CEG_NODE_BASE_ID);
            var url = Url_1.Url.build([_this.parent.url, id]);
            var node = _this.newNode;
            node.name = config_1.Config.CEG_NEW_NODE_NAME;
            node.description = config_1.Config.CEG_NEW_NODE_DESCRIPTION;
            node.id = id;
            node.url = url;
            node.variable = config_1.Config.CEG_NODE_NEW_VARIABLE;
            node.operator = config_1.Config.CEG_NODE_NEW_OPERATOR;
            node.value = config_1.Config.CEG_NODE_NEW_VALUE;
            node.x = x;
            node.y = y;
            _this.dataService.createElement(node, true);
            _this.selectedElements = [node];
        });
    };
    return NodeTool;
}());
exports.NodeTool = NodeTool;
//# sourceMappingURL=node-tool.js.map