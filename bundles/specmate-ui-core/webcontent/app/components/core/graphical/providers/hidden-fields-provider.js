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
var Type_1 = require("../../../../util/Type");
var CEGNode_1 = require("../../../../model/CEGNode");
var HiddenFieldsProvider = (function (_super) {
    __extends(HiddenFieldsProvider, _super);
    function HiddenFieldsProvider(element) {
        return _super.call(this, element) || this;
    }
    Object.defineProperty(HiddenFieldsProvider.prototype, "element", {
        get: function () {
            return this.modelType;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(HiddenFieldsProvider.prototype, "hiddenFields", {
        get: function () {
            if (this.isCEGNodeWithoutIncomingConnections) {
                return ['type'];
            }
            return [];
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(HiddenFieldsProvider.prototype, "isCEGNodeWithoutIncomingConnections", {
        get: function () {
            return Type_1.Type.is(this.element, CEGNode_1.CEGNode) && (!this.element.incomingConnections || this.element.incomingConnections.length <= 1);
        },
        enumerable: true,
        configurable: true
    });
    return HiddenFieldsProvider;
}(provider_base_1.ProviderBase));
exports.HiddenFieldsProvider = HiddenFieldsProvider;
//# sourceMappingURL=hidden-fields-provider.js.map