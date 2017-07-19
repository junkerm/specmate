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
var config_1 = require("../../../../config/config");
var proxy_1 = require("../../../../model/support/proxy");
var CEGCauseNode_1 = require("../../../../model/CEGCauseNode");
var CEGConnection_1 = require("../../../../model/CEGConnection");
var CEGEffectNode_1 = require("../../../../model/CEGEffectNode");
var CEGNode_1 = require("../../../../model/CEGNode");
var Type_1 = require("../../../../util/Type");
var Url_1 = require("../../../../util/Url");
var create_tool_1 = require("./create-tool");
var ConnectionTool = (function (_super) {
    __extends(ConnectionTool, _super);
    function ConnectionTool(parent, dataService) {
        var _this = _super.call(this, parent, dataService) || this;
        _this.parent = parent;
        _this.dataService = dataService;
        _this.name = 'Add Connection';
        _this.icon = 'sitemap';
        _this.color = 'primary';
        _this.cursor = 'crosshair';
        _this.done = false;
        _this.selectedElements = [];
        return _this;
    }
    Object.defineProperty(ConnectionTool.prototype, "firstNode", {
        get: function () {
            return this.selectedElements[0];
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ConnectionTool.prototype, "secondNode", {
        get: function () {
            return this.selectedElements[1];
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ConnectionTool.prototype, "firstNodePresent", {
        get: function () {
            return this.nodePresent(0);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ConnectionTool.prototype, "secondNodePresent", {
        get: function () {
            return this.nodePresent(1);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ConnectionTool.prototype, "present", {
        get: function () {
            return this.firstNodePresent && this.secondNodePresent;
        },
        enumerable: true,
        configurable: true
    });
    ConnectionTool.prototype.nodePresent = function (index) {
        return this.selectedElements[index] != null && this.selectedElements[index] !== undefined;
    };
    Object.defineProperty(ConnectionTool.prototype, "isValid", {
        get: function () {
            return this.present && this.firstNode !== this.secondNode;
            // && (Type.is(this.firstNode, CEGCauseNode) && Type.is(this.secondNode, CEGEffectNode))
            // || (Type.is(this.secondNode, CEGCauseNode) && Type.is(this.firstNode, CEGEffectNode));
        },
        enumerable: true,
        configurable: true
    });
    ConnectionTool.prototype.activate = function () {
        this.selectedElements = [];
        this.done = false;
    };
    ConnectionTool.prototype.deactivate = function () {
        this.selectedElements = [];
    };
    ConnectionTool.prototype.click = function (event) {
        return Promise.resolve();
    };
    ConnectionTool.prototype.select = function (element) {
        if (this.isConnectionSelected) {
            this.selectedElements = [];
        }
        if (Type_1.Type.is(element, CEGNode_1.CEGNode) || Type_1.Type.is(element, CEGCauseNode_1.CEGCauseNode) || Type_1.Type.is(element, CEGEffectNode_1.CEGEffectNode)) {
            if (this.selectedElements.length === 2 || this.selectedElements.length === 0) {
                this.selectedElements = [];
                this.selectedElements[0] = element;
            }
            else if (this.selectedElements.length === 1 && this.selectedElements[0] !== element) {
                this.selectedElements[1] = element;
            }
        }
        if (this.isValid) {
            return this.createNewConnection(this.selectedElements[0], this.selectedElements[1]);
        }
        return Promise.resolve();
    };
    Object.defineProperty(ConnectionTool.prototype, "isConnectionSelected", {
        get: function () {
            return this.selectedElements.length === 1 && Type_1.Type.is(this.selectedElements[0], CEGConnection_1.CEGConnection);
        },
        enumerable: true,
        configurable: true
    });
    ConnectionTool.prototype.createNewConnection = function (e1, e2) {
        var _this = this;
        return this.dataService.readContents(this.parent.url, true).then(function (contents) {
            var siblingConnections = contents.filter(function (element) { return Type_1.Type.is(element, CEGConnection_1.CEGConnection); });
            var alreadyExists = siblingConnections.some(function (connection) { return connection.source.url === e1.url && connection.target.url === e2.url; });
            if (!alreadyExists) {
                return _this.getNewId(config_1.Config.CEG_CONNECTION_BASE_ID);
            }
            return Promise.resolve(undefined);
        }).then(function (id) {
            if (id) {
                var connection = _this.connectionFactory(id, e1, e2);
                _this.createAndSelect(connection);
            }
        });
    };
    ConnectionTool.prototype.connectionFactory = function (id, e1, e2) {
        var url = Url_1.Url.build([this.parent.url, id]);
        var connection = new CEGConnection_1.CEGConnection();
        connection.name = config_1.Config.CEG_NEW_CONNECTION_NAME;
        connection.description = config_1.Config.CEG_NEW_CONNECTION_DESCRIPTION;
        connection.id = id;
        connection.url = url;
        connection.negate = false;
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
    return ConnectionTool;
}(create_tool_1.CreateTool));
exports.ConnectionTool = ConnectionTool;
//# sourceMappingURL=connection-tool.js.map