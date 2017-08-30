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
var Id_1 = require("../../util/Id");
var simple_input_form_base_1 = require("../core/forms/simple-input-form-base");
var specmate_data_service_1 = require("../../services/data/specmate-data.service");
var TestParameter_1 = require("../../model/TestParameter");
var core_1 = require("@angular/core");
var TestParameterForm = (function (_super) {
    __extends(TestParameterForm, _super);
    function TestParameterForm(dataService) {
        var _this = _super.call(this) || this;
        _this.dataService = dataService;
        return _this;
    }
    Object.defineProperty(TestParameterForm.prototype, "testParameter", {
        get: function () {
            return this.modelElement;
        },
        set: function (testParameter) {
            this.modelElement = testParameter;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestParameterForm.prototype, "fields", {
        get: function () {
            return ['name'];
        },
        enumerable: true,
        configurable: true
    });
    TestParameterForm.prototype.deleteParameter = function () {
        this.dataService.deleteElement(this.testParameter.url, true, Id_1.Id.uuid);
    };
    __decorate([
        core_1.Input(),
        __metadata("design:type", TestParameter_1.TestParameter),
        __metadata("design:paramtypes", [TestParameter_1.TestParameter])
    ], TestParameterForm.prototype, "testParameter", null);
    TestParameterForm = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'test-parameter-form',
            templateUrl: 'test-parameter-form.component.html',
            styleUrls: ['test-parameter-form.component.css']
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService])
    ], TestParameterForm);
    return TestParameterForm;
}(simple_input_form_base_1.SimpleInputFormBase));
exports.TestParameterForm = TestParameterForm;
//# sourceMappingURL=test-parameter-form.component.js.map