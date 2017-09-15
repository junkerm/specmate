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
var CEGNode_1 = require("../../../../model/CEGNode");
var config_1 = require("../../../../config/config");
var Id_1 = require("../../../../util/Id");
var Url_1 = require("../../../../util/Url");
var create_tool_1 = require("./create-tool");
var draggable_element_base_1 = require("../elements/draggable-element-base");
var NodeTool = (function (_super) {
    __extends(NodeTool, _super);
    function NodeTool(parent, dataService) {
        var _this = _super.call(this, parent, dataService) || this;
        _this.parent = parent;
        _this.dataService = dataService;
        _this.name = "Add Node";
        _this.icon = "plus";
        _this.color = "primary";
        _this.cursor = 'cell';
        _this.done = false;
        return _this;
    }
    Object.defineProperty(NodeTool.prototype, "newNode", {
        get: function () {
            return new CEGNode_1.CEGNode();
        },
        enumerable: true,
        configurable: true
    });
    NodeTool.prototype.click = function (event) {
        return this.createNewNode(event.offsetX, event.offsetY);
    };
    NodeTool.prototype.select = function (element) {
        this.selectedElements[0] = element;
        return Promise.resolve();
    };
    NodeTool.prototype.createNewNode = function (x, y) {
        var _this = this;
        var node = this.nodeFactory(Id_1.Id.uuid, x, y);
        return this.createAndSelect(node).then(function () {
            _this.done = true;
        });
    };
    NodeTool.prototype.nodeFactory = function (id, x, y) {
        var url = Url_1.Url.build([this.parent.url, id]);
        var node = this.newNode;
        node.name = config_1.Config.CEG_NEW_NODE_NAME;
        node.description = config_1.Config.CEG_NEW_NODE_DESCRIPTION;
        node.id = id;
        node.url = url;
        node.type = config_1.Config.CEG_NODE_NEW_TYPE;
        node.variable = config_1.Config.CEG_NODE_NEW_VARIABLE;
        node.condition = config_1.Config.CEG_NODE_NEW_CONDITION;
        node.x = draggable_element_base_1.DraggableElementBase.roundToGrid(x);
        node.y = draggable_element_base_1.DraggableElementBase.roundToGrid(y);
        return node;
    };
    return NodeTool;
}(create_tool_1.CreateTool));
exports.NodeTool = NodeTool;
//# sourceMappingURL=node-tool.js.map