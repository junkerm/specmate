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
var Process_1 = require("../../../../../../../../model/Process");
var process_step_factory_1 = require("../../../../../../../../factory/process-step-factory");
var StepTool = /** @class */ (function (_super) {
    __extends(StepTool, _super);
    function StepTool(parent, dataService, selectedElementService) {
        var _this = _super.call(this, parent, dataService, selectedElementService) || this;
        _this.modelType = Process_1.Process;
        _this.name = 'Add Activity';
        _this.icon = 'plus';
        return _this;
    }
    StepTool.prototype.getElementFactory = function (coords) {
        return new process_step_factory_1.ProcessStepFactory(coords, this.dataService);
    };
    return StepTool;
}(create_node_tool_base_1.CreateNodeToolBase));
exports.StepTool = StepTool;
//# sourceMappingURL=step-tool.js.map