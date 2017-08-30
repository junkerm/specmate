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
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
Object.defineProperty(exports, "__esModule", { value: true });
var simple_input_form_base_1 = require("../forms/simple-input-form-base");
var ParameterAssignment_1 = require("../../model/ParameterAssignment");
var specmate_data_service_1 = require("../../services/data/specmate-data.service");
var core_1 = require("@angular/core");
var TestCaseConditionForm = (function (_super) {
    __extends(TestCaseConditionForm, _super);
    function TestCaseConditionForm(dataService) {
        var _this = _super.call(this) || this;
        _this.dataService = dataService;
        return _this;
    }
    Object.defineProperty(TestCaseConditionForm.prototype, "paramAssignment", {
        set: function (paramAssignment) {
            this.modelElement = paramAssignment;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestCaseConditionForm.prototype, "fields", {
        get: function () {
            return ['condition'];
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        core_1.Input(),
        __metadata("design:type", ParameterAssignment_1.ParameterAssignment),
        __metadata("design:paramtypes", [ParameterAssignment_1.ParameterAssignment])
    ], TestCaseConditionForm.prototype, "paramAssignment", null);
    TestCaseConditionForm = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'test-case-condition-form',
            templateUrl: 'test-case-condition-form.component.html',
            styleUrls: ['test-case-condition-form.component.css']
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService])
    ], TestCaseConditionForm);
    return TestCaseConditionForm;
}(simple_input_form_base_1.SimpleInputFormBase));
exports.TestCaseConditionForm = TestCaseConditionForm;
//# sourceMappingURL=test-case-condition-form.component.js.map