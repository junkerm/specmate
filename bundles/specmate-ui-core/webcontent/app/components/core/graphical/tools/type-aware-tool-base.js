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
var element_provider_1 = require("../providers/element-provider");
var tool_base_1 = require("./tool-base");
var TypeAwareToolBase = (function (_super) {
    __extends(TypeAwareToolBase, _super);
    function TypeAwareToolBase() {
        return _super !== null && _super.apply(this, arguments) || this;
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
}(tool_base_1.ToolBase));
exports.TypeAwareToolBase = TypeAwareToolBase;
//# sourceMappingURL=type-aware-tool-base.js.map