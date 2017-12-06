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
var CEGNode_1 = require("../../../../../../../../model/CEGNode");
var ProcessStep_1 = require("../../../../../../../../model/ProcessStep");
var ProcessDecision_1 = require("../../../../../../../../model/ProcessDecision");
var ProcessStart_1 = require("../../../../../../../../model/ProcessStart");
var ProcessEnd_1 = require("../../../../../../../../model/ProcessEnd");
var CEGConnection_1 = require("../../../../../../../../model/CEGConnection");
var ProcessConnection_1 = require("../../../../../../../../model/ProcessConnection");
var arrays_1 = require("../../../../../../../../util/arrays");
var type_1 = require("../../../../../../../../util/type");
var ElementProvider = /** @class */ (function (_super) {
    __extends(ElementProvider, _super);
    function ElementProvider(type, _elements) {
        var _this = _super.call(this, type) || this;
        _this._elements = _elements;
        return _this;
    }
    Object.defineProperty(ElementProvider.prototype, "nodes", {
        get: function () {
            return this.getElementsOfTypes(this.nodeTypes);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ElementProvider.prototype, "connections", {
        get: function () {
            return this.getElementsOfTypes(this.connectionTypes);
        },
        enumerable: true,
        configurable: true
    });
    ElementProvider.prototype.getElementsOfTypes = function (types) {
        var _this = this;
        return this._elements.filter(function (element) { return _this.isOfTypes(element, types); });
    };
    Object.defineProperty(ElementProvider.prototype, "nodeTypes", {
        get: function () {
            if (this.isCEGModel) {
                return [CEGNode_1.CEGNode];
            }
            else if (this.isProcessModel) {
                return [ProcessStep_1.ProcessStep, ProcessDecision_1.ProcessDecision, ProcessStart_1.ProcessStart, ProcessEnd_1.ProcessEnd];
            }
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ElementProvider.prototype, "connectionTypes", {
        get: function () {
            if (this.isCEGModel) {
                return [CEGConnection_1.CEGConnection];
            }
            else if (this.isProcessModel) {
                return [ProcessConnection_1.ProcessConnection];
            }
        },
        enumerable: true,
        configurable: true
    });
    ElementProvider.prototype.isNode = function (element) {
        return arrays_1.Arrays.contains(this.nodeTypes.map(function (type) { return type_1.Type.of(type); }), type_1.Type.of(element));
    };
    ElementProvider.prototype.isConnection = function (element) {
        return arrays_1.Arrays.contains(this.connectionTypes.map(function (type) { return type_1.Type.of(type); }), type_1.Type.of(element));
    };
    ElementProvider.prototype.isOfTypes = function (element, types) {
        return arrays_1.Arrays.contains(types.map(function (type) { return type_1.Type.of(type); }), type_1.Type.of(element));
    };
    return ElementProvider;
}(provider_base_1.ProviderBase));
exports.ElementProvider = ElementProvider;
//# sourceMappingURL=element-provider.js.map