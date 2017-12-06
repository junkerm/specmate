"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var field_meta_1 = require("../../../../../model/meta/field-meta");
var required_fields_validator_1 = require("../base/required-fields-validator");
var ValidationService = (function () {
    function ValidationService() {
    }
    ValidationService.prototype.isValid = function (element) {
        var valid = this.getValidator(element).isValid(element);
        return valid;
    };
    ValidationService.prototype.allValid = function (contents) {
        var _this = this;
        if (!contents) {
            return true;
        }
        return !contents.some(function (element) { return !_this.getValidator(element).isValid(element); });
    };
    ValidationService.prototype.getValidator = function (element) {
        if (!this.validatorMap) {
            this.validatorMap = {};
        }
        var type = element.className;
        if (!this.validatorMap[type]) {
            var fieldMetaInfo = field_meta_1.MetaInfo[type];
            var requiredFields_1 = [];
            fieldMetaInfo.forEach(function (metaItem) {
                if (metaItem.required) {
                    requiredFields_1.push(metaItem.name);
                }
            });
            this.validatorMap[type] = new required_fields_validator_1.RequiredFieldsValidator(requiredFields_1);
        }
        return this.validatorMap[type];
    };
    ValidationService = __decorate([
        core_1.Injectable()
    ], ValidationService);
    return ValidationService;
}());
exports.ValidationService = ValidationService;
//# sourceMappingURL=validation.service.js.map