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
var validator_base_1 = require("./validator-base");
var RequiredFieldsValidator = /** @class */ (function (_super) {
    __extends(RequiredFieldsValidator, _super);
    function RequiredFieldsValidator(fields) {
        var _this = _super.call(this) || this;
        _this.fields = fields;
        return _this;
    }
    RequiredFieldsValidator.prototype.isValid = function (element) {
        return !this.fields.some(function (field) { return !element[field] || element[field].length === 0; });
    };
    return RequiredFieldsValidator;
}(validator_base_1.ValidatorBase));
exports.RequiredFieldsValidator = RequiredFieldsValidator;
//# sourceMappingURL=required-fields-validator.js.map