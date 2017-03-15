"use strict";
var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var node_tool_1 = require("./node-tool");
var CEGCauseNode_1 = require("../../../../model/CEGCauseNode");
var CauseNodeTool = (function (_super) {
    __extends(CauseNodeTool, _super);
    function CauseNodeTool() {
        _super.apply(this, arguments);
        this.name = "Add Cause Node";
        this.icon = "plus";
    }
    Object.defineProperty(CauseNodeTool.prototype, "newNode", {
        get: function () {
            return new CEGCauseNode_1.CEGCauseNode();
        },
        enumerable: true,
        configurable: true
    });
    return CauseNodeTool;
}(node_tool_1.NodeTool));
exports.CauseNodeTool = CauseNodeTool;
//# sourceMappingURL=cause-node-tool.js.map