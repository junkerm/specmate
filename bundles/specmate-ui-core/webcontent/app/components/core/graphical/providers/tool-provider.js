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
var provider_base_1 = require("./provider-base");
var CEGModel_1 = require("../../../../model/CEGModel");
var Type_1 = require("../../../../util/Type");
var move_tool_1 = require("../tools/move-tool");
var ceg_node_tool_1 = require("../tools/ceg/ceg-node-tool");
var Process_1 = require("../../../../model/Process");
var step_tool_1 = require("../tools/process/step-tool");
var decision_tool_1 = require("../tools/process/decision-tool");
var start_tool_1 = require("../tools/process/start-tool");
var end_tool_1 = require("../tools/process/end-tool");
var ceg_connection_tool_1 = require("../tools/ceg/ceg-connection-tool");
var process_connection_tool_1 = require("../tools/process/process-connection-tool");
var ceg_delete_tool_1 = require("../tools/ceg/ceg-delete-tool");
var process_delete_tool_1 = require("../tools/process/process-delete-tool");
var ToolProvider = (function (_super) {
    __extends(ToolProvider, _super);
    function ToolProvider(model, dataService) {
        var _this = _super.call(this, model) || this;
        _this.model = model;
        _this.dataService = dataService;
        return _this;
    }
    Object.defineProperty(ToolProvider.prototype, "tools", {
        get: function () {
            if (this._tools) {
                return this._tools;
            }
            if (Type_1.Type.is(this.modelType, CEGModel_1.CEGModel)) {
                this.createToolsForCEGModel();
            }
            else if (Type_1.Type.is(this.modelType, Process_1.Process)) {
                this.createToolsForProcess();
            }
            else {
                this.createEmptyTools();
            }
            return this._tools;
        },
        enumerable: true,
        configurable: true
    });
    ToolProvider.prototype.createEmptyTools = function () {
        this._tools = [];
    };
    ToolProvider.prototype.createToolsForCEGModel = function () {
        this._tools = [
            new move_tool_1.MoveTool(),
            new ceg_node_tool_1.CEGNodeTool(this.model, this.dataService),
            new ceg_connection_tool_1.CEGConnectionTool(this.model, this.dataService),
            new ceg_delete_tool_1.CEGDeleteTool(this.model, this.dataService)
        ];
    };
    ToolProvider.prototype.createToolsForProcess = function () {
        this._tools = [
            new move_tool_1.MoveTool(),
            new step_tool_1.StepTool(this.model, this.dataService),
            new decision_tool_1.DecisionTool(this.model, this.dataService),
            new start_tool_1.StartTool(this.model, this.dataService),
            new end_tool_1.EndTool(this.model, this.dataService),
            new process_connection_tool_1.ProcessConnectionTool(this.model, this.dataService),
            new process_delete_tool_1.ProcessDeleteTool(this.model, this.dataService)
        ];
    };
    ToolProvider.prototype.getDefaultTool = function (contents) {
        return contents && contents.length > 0 ? this.tools[0] : this.tools[1];
    };
    return ToolProvider;
}(provider_base_1.ProviderBase));
exports.ToolProvider = ToolProvider;
//# sourceMappingURL=tool-provider.js.map