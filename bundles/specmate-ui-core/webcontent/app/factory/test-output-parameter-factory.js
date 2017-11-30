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
var test_parameter_factory_1 = require("./test-parameter-factory");
var TestOutputParameterFactory = (function (_super) {
    __extends(TestOutputParameterFactory, _super);
    function TestOutputParameterFactory() {
        var _this = _super !== null && _super.apply(this, arguments) || this;
        _this.parameterType = 'OUTPUT';
        return _this;
    }
    return TestOutputParameterFactory;
}(test_parameter_factory_1.TestParameterFactory));
exports.TestOutputParameterFactory = TestOutputParameterFactory;
//# sourceMappingURL=test-output-parameter-factory.js.map