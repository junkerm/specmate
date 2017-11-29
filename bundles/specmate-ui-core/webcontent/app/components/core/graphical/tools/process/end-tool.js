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
var ProcessEnd_1 = require("../../../../../model/ProcessEnd");
var Process_1 = require("../../../../../model/Process");
var EndTool = (function (_super) {
    __extends(EndTool, _super);
    function EndTool(parent, dataService, selectedElementService) {
        var _this = _super.call(this, parent, dataService, selectedElementService) || this;
        _this.modelType = Process_1.Process;
        _this.name = "Add End";
        _this.icon = "plus";
        return _this;
    }
    EndTool.prototype.createNode = function (id, coords) {
        var url = Url_1.Url.build([this.parent.url, id]);
        var node = new ProcessEnd_1.ProcessEnd();
        node.name = config_1.Config.PROCESS_NEW_END_NAME;
        node.description = config_1.Config.PROCESS_NEW_END_DESCRIPTION;
        node.id = id;
        node.url = url;
        node.x = coords.x;
        node.y = coords.y;
        return node;
    };
    return EndTool;
}(create_node_tool_base_1.CreateNodeToolBase));
exports.EndTool = EndTool;
//# sourceMappingURL=end-tool.js.map