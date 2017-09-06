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
var TestCase_1 = require("../../model/TestCase");
var simple_input_form_base_1 = require("../forms/simple-input-form-base");
var specmate_data_service_1 = require("../../services/data/specmate-data.service");
var core_1 = require("@angular/core");
var TestCaseNameForm = (function (_super) {
    __extends(TestCaseNameForm, _super);
    function TestCaseNameForm(dataService) {
        var _this = _super.call(this) || this;
        _this.dataService = dataService;
        return _this;
    }
    Object.defineProperty(TestCaseNameForm.prototype, "testCase", {
        set: function (testCase) {
            this.modelElement = testCase;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestCaseNameForm.prototype, "fields", {
        get: function () {
            return ['name'];
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        core_1.Input(),
        __metadata("design:type", TestCase_1.TestCase),
        __metadata("design:paramtypes", [TestCase_1.TestCase])
    ], TestCaseNameForm.prototype, "testCase", null);
    TestCaseNameForm = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'test-case-name-form',
            templateUrl: 'test-case-name-form.component.html',
            styleUrls: ['test-case-name-form.component.css']
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService])
    ], TestCaseNameForm);
    return TestCaseNameForm;
}(simple_input_form_base_1.SimpleInputFormBase));
exports.TestCaseNameForm = TestCaseNameForm;
//# sourceMappingURL=test-case-name-form.component.js.map