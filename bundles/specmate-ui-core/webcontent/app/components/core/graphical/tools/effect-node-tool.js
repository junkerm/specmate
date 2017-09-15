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
var CEGEffectNode_1 = require("../../../../model/CEGEffectNode");
var EffectNodeTool = (function (_super) {
    __extends(EffectNodeTool, _super);
    function EffectNodeTool() {
        var _this = _super !== null && _super.apply(this, arguments) || this;
        _this.name = "Add Effect Node";
        _this.icon = "plus";
        return _this;
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