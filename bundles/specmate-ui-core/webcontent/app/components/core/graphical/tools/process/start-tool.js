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
var create_node_tool_base_1 = require("../create-node-tool-base");
var Process_1 = require("../../../../../model/Process");
var process_start_factory_1 = require("../../../../../factory/process-start-factory");
var StartTool = (function (_super) {
    __extends(StartTool, _super);
    function StartTool(parent, dataService, selectedElementService) {
        var _this = _super.call(this, parent, dataService, selectedElementService) || this;
        _this.modelType = Process_1.Process;
        _this.name = "Add Start";
        _this.icon = "plus";
        return _this;
    }
    StartTool.prototype.getElementFactory = function (coords) {
        return new process_start_factory_1.ProcessStartFactory(coords, this.dataService);
    };
    return StartTool;
}(create_node_tool_base_1.CreateNodeToolBase));
exports.StartTool = StartTool;
//# sourceMappingURL=start-tool.js.map