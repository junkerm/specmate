"use strict";
var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var node_tool_1 = require("./node-tool");
var CEGEffectNode_1 = require("../../../../model/CEGEffectNode");
var EffectNodeTool = (function (_super) {
    __extends(EffectNodeTool, _super);
    function EffectNodeTool() {
        _super.apply(this, arguments);
        this.name = "Add Effect Node";
        this.icon = "plus";
    }
    Object.defineProperty(EffectNodeTool.prototype, "newNode", {
        get: function () {
            return new CEGEffectNode_1.CEGEffectNode();
        },
        enumerable: true,
        configurable: true
    });
    return EffectNodeTool;
}(node_tool_1.NodeTool));
exports.EffectNodeTool = EffectNodeTool;
//# sourceMappingURL=effect-node-tool.js.map