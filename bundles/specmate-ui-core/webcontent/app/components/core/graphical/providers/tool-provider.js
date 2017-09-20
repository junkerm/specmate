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
var node_tool_1 = require("../tools/node-tool");
var connection_tool_1 = require("../tools/connection-tool");
var delete_tool_1 = require("../tools/delete-tool");
var Process_1 = require("../../../../model/Process");
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
            if (Type_1.Type.is(this.modelType, Process_1.Process)) {
                this.createToolsForProcess();
            }
            return this._tools;
        },
        enumerable: true,
        configurable: true
    });
    ToolProvider.prototype.createToolsForCEGModel = function () {
        this._tools = [
            new move_tool_1.MoveTool(),
            new node_tool_1.NodeTool(this.model, this.dataService),
            new connection_tool_1.ConnectionTool(this.model, this.dataService),
            new delete_tool_1.DeleteTool(this.model, this.dataService)
        ];
    };
    ToolProvider.prototype.createToolsForProcess = function () {
        this._tools = [
            new move_tool_1.MoveTool(),
        ];
    };
    return ToolProvider;
}(provider_base_1.ProviderBase));
exports.ToolProvider = ToolProvider;
//# sourceMappingURL=tool-provider.js.map