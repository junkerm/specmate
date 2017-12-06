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
var NameProvider = /** @class */ (function (_super) {
    __extends(NameProvider, _super);
    function NameProvider(model) {
        return _super.call(this, model) || this;
    }
    Object.defineProperty(NameProvider.prototype, "name", {
        get: function () {
            if (this.isCEGModel) {
                return "Cause Effect Graph";
            }
            else if (this.isProcessModel) {
                return "Process Model";
            }
        },
        enumerable: true,
        configurable: true
    });
    return NameProvider;
}(provider_base_1.ProviderBase));
exports.NameProvider = NameProvider;
//# sourceMappingURL=name-provider.js.map