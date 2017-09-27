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
var node_tool_1 = require("./node-tool");
var CEGCauseNode_1 = require("../../../../model/CEGCauseNode");
var CauseNodeTool = (function (_super) {
    __extends(CauseNodeTool, _super);
    function CauseNodeTool() {
        var _this = _super !== null && _super.apply(this, arguments) || this;
        _this.name = 'Add Cause Node';
        _this.icon = 'plus';
        return _this;
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