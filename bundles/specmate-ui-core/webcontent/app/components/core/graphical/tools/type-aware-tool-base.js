"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var element_provider_1 = require("../providers/element-provider");
var TypeAwareToolBase = (function () {
    function TypeAwareToolBase() {
    }
    Object.defineProperty(TypeAwareToolBase.prototype, "elementProvider", {
        get: function () {
            if (!this._elementProvider && this.modelType) {
                this._elementProvider = new element_provider_1.ElementProvider(this.modelType);
            }
            return this._elementProvider;
        },
        enumerable: true,
        configurable: true
    });
    TypeAwareToolBase.prototype.isNode = function (element) {
        return this.elementProvider.isNode(element);
    };
    TypeAwareToolBase.prototype.isConnection = function (element) {
        return this.elementProvider.isConnection(element);
    };
    return TypeAwareToolBase;
}());
exports.TypeAwareToolBase = TypeAwareToolBase;
//# sourceMappingURL=type-aware-tool-base.js.map