"use strict";
var config_1 = require('../../../../config/config');
var CEGCauseNode_1 = require('../../../../model/CEGCauseNode');
var CEGConnection_1 = require('../../../../model/CEGConnection');
var CEGEffectNode_1 = require('../../../../model/CEGEffectNode');
var CEGNode_1 = require('../../../../model/CEGNode');
var Id_1 = require('../../../../util/Id');
var Type_1 = require('../../../../util/Type');
var Url_1 = require('../../../../util/Url');
var ConnectionTool = (function () {
    function ConnectionTool(parent, contents, dataService) {
        this.parent = parent;
        this.contents = contents;
        this.dataService = dataService;
        this.name = 'Add Connection';
        this.icon = 'sitemap';
        this.color = 'primary';
        this.selectedElements = [];
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
    Object.defineProperty(ConnectionTool.prototype, "siblingConnections", {
        get: function () {
            return this.contents.filter(function (element) { return Type_1.Type.is(element, CEGConnection_1.CEGConnection); });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ConnectionTool.prototype, "newConnectionUrl", {
        get: function () {
            return Url_1.Url.build([this.parent.url, Id_1.Id.generate(this.siblingConnections, config_1.Config.CEG_CONNECTION_BASE_ID)]);
        },
        enumerable: true,
        configurable: true
    });
    ConnectionTool.prototype.activate = function () {
        this.selectedElements = [];
    };
    ConnectionTool.prototype.deactivate = function () {
        this.selectedElements = [];
    };
    ConnectionTool.prototype.click = function (event) { };
    ConnectionTool.prototype.select = function (element) {
        if (this.isConnectionSelected) {
            this.selectedElements = [];
        }
        if (Type_1.Type.is(element, CEGNode_1.CEGNode) || Type_1.Type.is(element, CEGCauseNode_1.CEGCauseNode) || Type_1.Type.is(element, CEGEffectNode_1.CEGEffectNode)) {
            if (this.selectedElements.length === 2 || this.selectedElements.length === 0) {
                this.selectedElements = [];
                this.selectedElements[0] = element;
            }
            else {
                this.selectedElements[1] = element;
            }
        }
        if (this.isValid) {
            this.addConnection(this.selectedElements[0], this.selectedElements[1]);
        }
    };
    Object.defineProperty(ConnectionTool.prototype, "isConnectionSelected", {
        get: function () {
            return this.selectedElements.length === 1 && Type_1.Type.is(this.selectedElements[0], CEGConnection_1.CEGConnection);
        },
        enumerable: true,
        configurable: true
    });
    ConnectionTool.prototype.addConnection = function (e1, e2) {
        var id = Id_1.Id.generate(this.siblingConnections, config_1.Config.CEG_CONNECTION_BASE_ID);
        var url = Url_1.Url.build([this.parent.url, id]);
        var connection = new CEGConnection_1.CEGConnection();
        connection.name = config_1.Config.CEG_NEW_CONNECTION_NAME;
        connection.description = config_1.Config.CEG_NEW_CONNECTION_DESCRIPTION;
        connection.id = id;
        connection.url = url;
        connection.source = { url: e1.url };
        connection.target = { url: e2.url };
        this.dataService.addElement(connection);
        this.selectedElements = [connection];
    };
    return ConnectionTool;
}());
exports.ConnectionTool = ConnectionTool;
//# sourceMappingURL=connection-tool.js.map