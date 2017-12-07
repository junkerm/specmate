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
var process_end_factory_1 = require("../../../../../../../../factory/process-end-factory");
var EndTool = /** @class */ (function (_super) {
    __extends(EndTool, _super);
    function EndTool(parent, dataService, selectedElementService) {
        var _this = _super.call(this, parent, dataService, selectedElementService) || this;
        _this.modelType = Process_1.Process;
        _this.name = 'Add End';
        _this.icon = 'plus';
        return _this;
    }
    EndTool.prototype.getElementFactory = function (coords) {
        return new process_end_factory_1.ProcessEndFactory(coords, this.dataService);
    };
    return EndTool;
}(create_node_tool_base_1.CreateNodeToolBase));
exports.EndTool = EndTool;
//# sourceMappingURL=end-tool.js.map