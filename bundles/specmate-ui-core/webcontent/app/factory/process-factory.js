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
var Process_1 = require("../model/Process");
var config_1 = require("../config/config");
var ProcessFactory = (function (_super) {
    __extends(ProcessFactory, _super);
    function ProcessFactory() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    Object.defineProperty(ProcessFactory.prototype, "simpleModel", {
        get: function () {
            return new Process_1.Process();
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessFactory.prototype, "name", {
        get: function () {
            return config_1.Config.PROCESS_NEW_PROCESS_NAME;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessFactory.prototype, "description", {
        get: function () {
            return config_1.Config.PROCESS_NEW_PROCESS_DESCRIPTION;
        },
        enumerable: true,
        configurable: true
    });
    return ProcessFactory;
}(model_factory_base_1.ModelFactoryBase));
exports.ProcessFactory = ProcessFactory;
//# sourceMappingURL=process-factory.js.map