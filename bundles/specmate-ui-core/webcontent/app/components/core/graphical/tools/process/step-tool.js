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
var config_1 = require("../../../../../config/config");
var Url_1 = require("../../../../../util/Url");
var create_node_tool_base_1 = require("../create-node-tool-base");
var ProcessStep_1 = require("../../../../../model/ProcessStep");
var Process_1 = require("../../../../../model/Process");
var StepTool = (function (_super) {
    __extends(StepTool, _super);
    function StepTool(parent, dataService) {
        var _this = _super.call(this, parent, dataService) || this;
        _this.modelType = Process_1.Process;
        _this.name = "Add Activity";
        _this.icon = "plus";
        return _this;
    }
    StepTool.prototype.createNode = function (id, coords) {
        var url = Url_1.Url.build([this.parent.url, id]);
        var node = new ProcessStep_1.ProcessStep();
        node.name = config_1.Config.PROCESS_NEW_STEP_NAME;
        node.description = config_1.Config.PROCESS_NEW_STEP_DESCRIPTION;
        node.id = id;
        node.url = url;
        node.x = coords.x;
        node.y = coords.y;
        return node;
    };
    return StepTool;
}(create_node_tool_base_1.CreateNodeToolBase));
exports.StepTool = StepTool;
//# sourceMappingURL=step-tool.js.map