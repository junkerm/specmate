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
var TestCase_1 = require("../../model/TestCase");
var specmate_data_service_1 = require("../../services/specmate-data.service");
var forms_1 = require("@angular/forms");
var core_1 = require("@angular/core");
var TestCaseNameForm = (function () {
    function TestCaseNameForm(dataService) {
        this.dataService = dataService;
        this.formGroup = new forms_1.FormGroup({});
    }
    Object.defineProperty(TestCaseNameForm.prototype, "testCase", {
        /** The parameter assignment */
        set: function (testCase) {
            this._testCase = testCase;
            this.buildFormGroup();
        },
        enumerable: true,
        configurable: true
    });
    TestCaseNameForm.prototype.ngDoCheck = function (args) {
        this.updateForm();
    };
    TestCaseNameForm.prototype.updateForm = function () {
        var formBuilderObject = {};
        formBuilderObject.testCase = this._testCase.name;
        this.formGroup.setValue(formBuilderObject);
    };
    TestCaseNameForm.prototype.buildFormGroup = function () {
        var _this = this;
        this.formGroup = new forms_1.FormGroup({
            'testCase': new forms_1.FormControl(this._testCase.name, forms_1.Validators.required)
        });
        this.formGroup.valueChanges.subscribe(function () {
            _this._testCase.name = _this.formGroup.controls['testCase'].value;
            _this.dataService.updateElement(_this._testCase, true, Id_1.Id.uuid);
        });
    };
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
}());
exports.TestCaseNameForm = TestCaseNameForm;
//# sourceMappingURL=test-case-name-form.component.js.map