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
var specmate_data_service_1 = require("../../services/specmate-data.service");
var TestStep_1 = require("../../model/TestStep");
var core_1 = require("@angular/core");
var forms_1 = require("@angular/forms");
var TestStepRow = (function () {
    function TestStepRow(dataService) {
        this.dataService = dataService;
        this.formGroup = new forms_1.FormGroup({});
    }
    Object.defineProperty(TestStepRow.prototype, "testStep", {
        get: function () {
            return this._testStep;
        },
        set: function (testStep) {
            this._testStep = testStep;
            this.buildFormGroup();
        },
        enumerable: true,
        configurable: true
    });
    TestStepRow.prototype.ngDoCheck = function (args) {
        this.updateFormGroup();
    };
    TestStepRow.prototype.buildFormGroup = function () {
        var _this = this;
        this.formGroup = new forms_1.FormGroup({
            'description': new forms_1.FormControl(this._testStep.description, forms_1.Validators.required),
            'expectedOutcome': new forms_1.FormControl(this._testStep.expectedOutcome, forms_1.Validators.required)
        });
        this.formGroup.valueChanges.subscribe(function () {
            _this._testStep.description = _this.formGroup.controls["description"].value;
            _this._testStep.expectedOutcome = _this.formGroup.controls["expectedOutcome"].value;
            _this.dataService.updateElement(_this._testStep, true);
        });
    };
    TestStepRow.prototype.updateFormGroup = function () {
        var formBuilderObject = {};
        formBuilderObject.description = this._testStep.description;
        formBuilderObject.expectedOutcome = this._testStep.expectedOutcome;
        this.formGroup.setValue(formBuilderObject);
    };
    TestStepRow.prototype.delete = function () {
        this.dataService.deleteElement(this._testStep.url, true);
    };
    __decorate([
        core_1.Input(),
        __metadata("design:type", TestStep_1.TestStep),
        __metadata("design:paramtypes", [TestStep_1.TestStep])
    ], TestStepRow.prototype, "testStep", null);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Number)
    ], TestStepRow.prototype, "stepNumber", void 0);
    TestStepRow = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: '[test-step-row]',
            templateUrl: 'test-step-row.component.html'
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService])
    ], TestStepRow);
    return TestStepRow;
}());
exports.TestStepRow = TestStepRow;
//# sourceMappingURL=test-step-row.component.js.map