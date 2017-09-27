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
var Url_1 = require("../../../../../util/Url");
var config_1 = require("../../../../../config/config");
var proxy_1 = require("../../../../../model/support/proxy");
var ProcessConnection_1 = require("../../../../../model/ProcessConnection");
var Process_1 = require("../../../../../model/Process");
var ProcessConnectionTool = (function (_super) {
    __extends(ProcessConnectionTool, _super);
    function ProcessConnectionTool() {
        var _this = _super !== null && _super.apply(this, arguments) || this;
        _this.modelType = Process_1.Process;
        _this.name = 'Add Connection';
        _this.icon = 'sitemap';
        return _this;
    }
    ProcessConnectionTool.prototype.createConnection = function (id, e1, e2) {
        var url = Url_1.Url.build([this.parent.url, id]);
        var connection = new ProcessConnection_1.ProcessConnection();
        connection.name = config_1.Config.CEG_NEW_CONNECTION_NAME;
        connection.description = config_1.Config.CEG_NEW_CONNECTION_DESCRIPTION;
        connection.id = id;
        connection.url = url;
        connection.source = new proxy_1.Proxy();
        connection.source.url = e1.url;
        connection.target = new proxy_1.Proxy();
        connection.target.url = e2.url;
        var proxy = new proxy_1.Proxy();
        proxy.url = connection.url;
        if (!e1.outgoingConnections) {
            e1.outgoingConnections = [];
        }
        if (!e2.incomingConnections) {
            e2.incomingConnections = [];
        }
        e1.outgoingConnections.push(proxy);
        e2.incomingConnections.push(proxy);
        return connection;
    };
    return ProcessConnectionTool;
}(connection_tool_base_1.ConnectionToolBase));
exports.ProcessConnectionTool = ProcessConnectionTool;
//# sourceMappingURL=process-connection-tool.js.map