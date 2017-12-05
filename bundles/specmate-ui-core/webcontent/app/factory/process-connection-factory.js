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
var ProcessConnection_1 = require("../model/ProcessConnection");
var Url_1 = require("../util/Url");
var Id_1 = require("../util/Id");
var config_1 = require("../config/config");
var proxy_1 = require("../model/support/proxy");
var connection_element_factory_base_1 = require("./connection-element-factory-base");
var ProcessConnectionFactory = (function (_super) {
    __extends(ProcessConnectionFactory, _super);
    function ProcessConnectionFactory() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    ProcessConnectionFactory.prototype.create = function (parent, commit, compoundId) {
        var _this = this;
        compoundId = compoundId || Id_1.Id.uuid;
        var id = Id_1.Id.uuid;
        var url = Url_1.Url.build([parent.url, id]);
        var connection = new ProcessConnection_1.ProcessConnection();
        connection.name = config_1.Config.PROCESS_NEW_CONNECTION_NAME;
        connection.description = config_1.Config.PROCESS_NEW_CONNECTION_DESCRIPTION;
        connection.condition = config_1.Config.PROCESS_NEW_CONNECTION_DESCRIPTION;
        connection.id = id;
        connection.url = url;
        connection.source = new proxy_1.Proxy();
        connection.source.url = this.source.url;
        connection.target = new proxy_1.Proxy();
        connection.target.url = this.target.url;
        var proxy = new proxy_1.Proxy();
        proxy.url = connection.url;
        if (!this.source.outgoingConnections) {
            this.source.outgoingConnections = [];
        }
        if (!this.target.incomingConnections) {
            this.target.incomingConnections = [];
        }
        this.source.outgoingConnections.push(proxy);
        this.target.incomingConnections.push(proxy);
        return this.dataService.createElement(connection, true, compoundId)
            .then(function () { return _this.dataService.updateElement(_this.source, true, compoundId); })
            .then(function () { return _this.dataService.updateElement(_this.target, true, compoundId); })
            .then(function () { return connection; });
    };
    return ProcessConnectionFactory;
}(connection_element_factory_base_1.ConnectionElementFactoryBase));
exports.ProcessConnectionFactory = ProcessConnectionFactory;
//# sourceMappingURL=process-connection-factory.js.map