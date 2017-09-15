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
var CEGModel_1 = require("../../../../model/CEGModel");
var Process_1 = require("../../../../model/Process");
var NameProvider = (function (_super) {
    __extends(NameProvider, _super);
    function NameProvider(model) {
        return _super.call(this, model) || this;
    }
    Object.defineProperty(NameProvider.prototype, "name", {
        get: function () {
            if (Type_1.Type.is(this.modelType, CEGModel_1.CEGModel)) {
                return "Cause Effect Graph";
            }
            else if (Type_1.Type.is(this.modelType, Process_1.Process)) {
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