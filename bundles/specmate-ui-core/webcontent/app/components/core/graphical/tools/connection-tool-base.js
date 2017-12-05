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
var create_tool_base_1 = require("./create-tool-base");
var ConnectionToolBase = (function (_super) {
    __extends(ConnectionToolBase, _super);
    function ConnectionToolBase(parent, dataService, selectedElementService) {
        var _this = _super.call(this, parent, dataService, selectedElementService) || this;
        _this.parent = parent;
        _this.dataService = dataService;
        _this.color = 'primary';
        _this.cursor = 'crosshair';
        _this.done = false;
        _this.selectedElements = [];
        return _this;
    }
    Object.defineProperty(ConnectionToolBase.prototype, "firstNode", {
        get: function () {
            return this.selectedElements[0];
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ConnectionToolBase.prototype, "secondNode", {
        get: function () {
            return this.selectedElements[1];
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ConnectionToolBase.prototype, "firstNodePresent", {
        get: function () {
            return this.nodePresent(0);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ConnectionToolBase.prototype, "secondNodePresent", {
        get: function () {
            return this.nodePresent(1);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ConnectionToolBase.prototype, "present", {
        get: function () {
            return this.firstNodePresent && this.secondNodePresent;
        },
        enumerable: true,
        configurable: true
    });
    ConnectionToolBase.prototype.nodePresent = function (index) {
        return this.selectedElements[index] != null && this.selectedElements[index] !== undefined;
    };
    Object.defineProperty(ConnectionToolBase.prototype, "isValid", {
        get: function () {
            return this.present && this.firstNode !== this.secondNode;
        },
        enumerable: true,
        configurable: true
    });
    ConnectionToolBase.prototype.activate = function () {
        this.selectedElements = [];
        this.done = false;
    };
    ConnectionToolBase.prototype.deactivate = function () {
        this.selectedElements = [];
    };
    ConnectionToolBase.prototype.click = function (event, zoom) {
        return Promise.resolve();
    };
    ConnectionToolBase.prototype.select = function (element) {
        if (this.isConnectionSelected) {
            this.selectedElements = [];
        }
        if (this.isNode(element)) {
            if (this.selectedElements.length === 2 || this.selectedElements.length === 0) {
                this.selectedElements = [];
                this.selectedElements[0] = element;
            }
            else if (this.selectedElements.length === 1 && this.selectedElements[0] !== element) {
                this.selectedElements[1] = element;
            }
            this.selectedElementService.select(element);
        }
        if (this.isValid) {
            return this.createNewConnection(this.selectedElements[0], this.selectedElements[1]);
        }
        return Promise.resolve();
    };
    Object.defineProperty(ConnectionToolBase.prototype, "isConnectionSelected", {
        get: function () {
            return this.selectedElements.length === 1 && this.isConnection(this.selectedElements[0]);
        },
        enumerable: true,
        configurable: true
    });
    ConnectionToolBase.prototype.createNewConnection = function (e1, e2) {
        var _this = this;
        return this.dataService.readContents(this.parent.url, true).then(function (contents) {
            var siblingConnections = contents.filter(function (element) { return _this.isConnection(element); });
            var alreadyExists = siblingConnections.some(function (connection) { return connection.source.url === e1.url && connection.target.url === e2.url; });
            if (!alreadyExists) {
                return _this.getFactory(e1, e2).create(_this.parent, false)
                    .then(function (element) { return _this.selectedElementService.select(element); })
                    .then(function () { return _this.done = true; })
                    .then(function () { return Promise.resolve(); });
            }
            return Promise.resolve();
        });
    };
    return ConnectionToolBase;
}(create_tool_base_1.CreateToolBase));
exports.ConnectionToolBase = ConnectionToolBase;
//# sourceMappingURL=connection-tool-base.js.map