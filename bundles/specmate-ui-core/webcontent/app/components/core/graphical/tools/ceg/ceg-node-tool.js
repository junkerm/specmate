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
var CEGNode_1 = require("../../../../../model/CEGNode");
var config_1 = require("../../../../../config/config");
var Url_1 = require("../../../../../util/Url");
var create_node_tool_base_1 = require("../create-node-tool-base");
var CEGModel_1 = require("../../../../../model/CEGModel");
var CEGNodeTool = (function (_super) {
    __extends(CEGNodeTool, _super);
    function CEGNodeTool(parent, dataService) {
        var _this = _super.call(this, parent, dataService) || this;
        _this.modelType = CEGModel_1.CEGModel;
        _this.name = "Add Node";
        _this.icon = "plus";
        return _this;
    }
    CEGNodeTool.prototype.createNode = function (id, coords) {
        var url = Url_1.Url.build([this.parent.url, id]);
        var node = new CEGNode_1.CEGNode();
        node.name = config_1.Config.CEG_NEW_NODE_NAME;
        node.description = config_1.Config.CEG_NEW_NODE_DESCRIPTION;
        node.id = id;
        node.url = url;
        node.type = config_1.Config.CEG_NODE_NEW_TYPE;
        node.variable = config_1.Config.CEG_NODE_NEW_VARIABLE;
        node.condition = config_1.Config.CEG_NODE_NEW_CONDITION;
        node.x = coords.x;
        node.y = coords.y;
        return node;
    };
    return CEGNodeTool;
}(create_node_tool_base_1.CreateNodeToolBase));
exports.CEGNodeTool = CEGNodeTool;
//# sourceMappingURL=ceg-node-tool.js.map