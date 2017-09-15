"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var Type_1 = require("../../../../util/Type");
var CEGModel_1 = require("../../../../model/CEGModel");
var CEGNode_1 = require("../../../../model/CEGNode");
var Arrays_1 = require("../../../../util/Arrays");
var CEGConnection_1 = require("../../../../model/CEGConnection");
var ElementProvider = (function () {
    function ElementProvider(_elements, modelType) {
        this._elements = _elements;
        this.modelType = modelType;
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
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ElementProvider.prototype, "connectionTypes", {
        get: function () {
            if (Type_1.Type.is(this.modelType, CEGModel_1.CEGModel)) {
                return [CEGConnection_1.CEGConnection];
            }
        },
        enumerable: true,
        configurable: true
    });
    return ElementProvider;
}());
exports.ElementProvider = ElementProvider;
//# sourceMappingURL=element-provider.js.map