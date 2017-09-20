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
var Type_1 = require("../../../../util/Type");
var CEGModel_1 = require("../../../../model/CEGModel");
var CEGNode_1 = require("../../../../model/CEGNode");
var Arrays_1 = require("../../../../util/Arrays");
var CEGConnection_1 = require("../../../../model/CEGConnection");
var provider_base_1 = require("./provider-base");
var Process_1 = require("../../../../model/Process");
var ProcessDecision_1 = require("../../../../model/ProcessDecision");
var ProcessStep_1 = require("../../../../model/ProcessStep");
var ProcessConnection_1 = require("../../../../model/ProcessConnection");
var ProcessStart_1 = require("../../../../model/ProcessStart");
var ProcessEnd_1 = require("../../../../model/ProcessEnd");
var ElementProvider = (function (_super) {
    __extends(ElementProvider, _super);
    function ElementProvider(model, _elements) {
        var _this = _super.call(this, model) || this;
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
        return this._elements.filter(function (element) { return Arrays_1.Arrays.contains(types.map(function (type) { return Type_1.Type.of(type); }), Type_1.Type.of(element)); });
    };
    Object.defineProperty(ElementProvider.prototype, "nodeTypes", {
        get: function () {
            if (Type_1.Type.is(this.modelType, CEGModel_1.CEGModel)) {
                return [CEGNode_1.CEGNode];
            }
            else if (Type_1.Type.is(this.modelType, Process_1.Process)) {
                return [ProcessStep_1.ProcessStep, ProcessDecision_1.ProcessDecision, ProcessStart_1.ProcessStart, ProcessEnd_1.ProcessEnd];
            }
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ElementProvider.prototype, "connectionTypes", {
        get: function () {
            if (Type_1.Type.is(this.modelType, CEGModel_1.CEGModel)) {
                return [CEGConnection_1.CEGConnection];
            }
            else if (Type_1.Type.is(this.modelType, Process_1.Process)) {
                return [ProcessConnection_1.ProcessConnection];
            }
        },
        enumerable: true,
        configurable: true
    });
    return ElementProvider;
}(provider_base_1.ProviderBase));
exports.ElementProvider = ElementProvider;
//# sourceMappingURL=element-provider.js.map