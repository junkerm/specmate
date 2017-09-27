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
var delete_tool_base_1 = require("../delete-tool-base");
var Process_1 = require("../../../../../model/Process");
var ProcessDeleteTool = (function (_super) {
    __extends(ProcessDeleteTool, _super);
    function ProcessDeleteTool() {
        var _this = _super !== null && _super.apply(this, arguments) || this;
        _this.modelType = Process_1.Process;
        return _this;
    }
    return ProcessDeleteTool;
}(delete_tool_base_1.DeleteToolBase));
exports.ProcessDeleteTool = ProcessDeleteTool;
//# sourceMappingURL=process-delete-tool.js.map