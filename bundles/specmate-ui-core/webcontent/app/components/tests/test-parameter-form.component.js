"use strict";
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
var specmate_data_service_1 = require("../../services/specmate-data.service");
var forms_1 = require("@angular/forms");
var TestParameter_1 = require("../../model/TestParameter");
var core_1 = require("@angular/core");
var TestParameterForm = (function () {
    function TestParameterForm(dataService) {
        this.dataService = dataService;
        this.formGroup = new forms_1.FormGroup({});
    }
    Object.defineProperty(TestParameterForm.prototype, "testParameter", {
        /** The test parameter to display */
        set: function (testParameter) {
            this._testParameter = testParameter;
            this.buildFormGroup();
        },
        enumerable: true,
        configurable: true
    });
    TestParameterForm.prototype.ngDoCheck = function (args) {
        this.updateForm();
    };
    TestParameterForm.prototype.updateForm = function () {
        var formBuilderObject = {};
        formBuilderObject.parameter = this._testParameter.name;
        this.formGroup.setValue(formBuilderObject);
    };
    TestParameterForm.prototype.buildFormGroup = function () {
        var _this = this;
        this.formGroup = new forms_1.FormGroup({
            'parameter': new forms_1.FormControl(this._testParameter.name, forms_1.Validators.required)
        });
        this.formGroup.valueChanges.subscribe(function () {
            _this._testParameter.name = _this.formGroup.controls["parameter"].value;
            _this.dataService.updateElement(_this._testParameter, true, Id_1.Id.uuid);
        });
    };
    TestParameterForm.prototype.deleteParameter = function () {
        this.dataService.deleteElement(this._testParameter.url, true, Id_1.Id.uuid);
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
}());
exports.TestParameterForm = TestParameterForm;
//# sourceMappingURL=test-parameter-form.component.js.map