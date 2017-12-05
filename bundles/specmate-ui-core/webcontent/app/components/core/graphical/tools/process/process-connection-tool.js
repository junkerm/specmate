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
var connection_tool_base_1 = require("../connection-tool-base");
var Process_1 = require("../../../../../model/Process");
var process_connection_factory_1 = require("../../../../../factory/process-connection-factory");
var ProcessConnectionTool = (function (_super) {
    __extends(ProcessConnectionTool, _super);
    function ProcessConnectionTool() {
        var _this = _super !== null && _super.apply(this, arguments) || this;
        _this.modelType = Process_1.Process;
        _this.name = 'Add Connection';
        _this.icon = 'sitemap';
        return _this;
    }
    ProcessConnectionTool.prototype.getFactory = function (e1, e2) {
        return new process_connection_factory_1.ProcessConnectionFactory(e1, e2, this.dataService);
    };
    return ProcessConnectionTool;
}(connection_tool_base_1.ConnectionToolBase));
exports.ProcessConnectionTool = ProcessConnectionTool;
//# sourceMappingURL=process-connection-tool.js.map