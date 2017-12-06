"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var type_1 = require("../../../../../../../../util/type");
var CEGModel_1 = require("../../../../../../../../model/CEGModel");
var Process_1 = require("../../../../../../../../model/Process");
var ProviderBase = (function () {
    function ProviderBase(modelType) {
        this.modelType = modelType;
    }
    Object.defineProperty(ProviderBase.prototype, "isCEGModel", {
        get: function () {
            return type_1.Type.is(this.modelType, CEGModel_1.CEGModel);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProviderBase.prototype, "isProcessModel", {
        get: function () {
            return type_1.Type.is(this.modelType, Process_1.Process);
        },
        enumerable: true,
        configurable: true
    });
    return ProviderBase;
}());
exports.ProviderBase = ProviderBase;
//# sourceMappingURL=provider-base.js.map