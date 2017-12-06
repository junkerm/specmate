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
var model_factory_base_1 = require("./model-factory-base");
var CEGModel_1 = require("../model/CEGModel");
var config_1 = require("../config/config");
var CEGModelFactory = (function (_super) {
    __extends(CEGModelFactory, _super);
    function CEGModelFactory() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    Object.defineProperty(CEGModelFactory.prototype, "simpleModel", {
        get: function () {
            return new CEGModel_1.CEGModel();
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGModelFactory.prototype, "name", {
        get: function () {
            return config_1.Config.CEG_NEW_MODEL_NAME;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGModelFactory.prototype, "description", {
        get: function () {
            return config_1.Config.CEG_NEW_MODEL_DESCRIPTION;
        },
        enumerable: true,
        configurable: true
    });
    return CEGModelFactory;
}(model_factory_base_1.ModelFactoryBase));
exports.CEGModelFactory = CEGModelFactory;
//# sourceMappingURL=ceg-model-factory.js.map