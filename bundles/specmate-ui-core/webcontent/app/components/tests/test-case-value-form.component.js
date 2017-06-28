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
var ParameterAssignment_1 = require('../../model/ParameterAssignment');
var specmate_data_service_1 = require('../../services/specmate-data.service');
var forms_1 = require('@angular/forms');
var core_1 = require('@angular/core');
var TestCaseValueForm = (function () {
    function TestCaseValueForm(dataService) {
        this.dataService = dataService;
        this.formGroup = new forms_1.FormGroup({});
    }
    Object.defineProperty(TestCaseValueForm.prototype, "paramAssignment", {
        /** The parameter assignment */
        set: function (paramAssignment) {
            this._paramAssignment = paramAssignment;
            this.buildFormGroup();
        },
        enumerable: true,
        configurable: true
    });
    TestCaseValueForm.prototype.buildFormGroup = function () {
        var _this = this;
        this.formGroup = new forms_1.FormGroup({
            'paramAssignment': new forms_1.FormControl(this._paramAssignment ? this._paramAssignment.value : "", forms_1.Validators.required)
        });
        this.formGroup.valueChanges.subscribe(function () {
            if (!_this._paramAssignment) {
                console.log("STILL");
                return;
            }
            _this._paramAssignment.value = _this.formGroup.controls['paramAssignment'].value;
            _this.dataService.updateElement(_this._paramAssignment, true);
        });
    };
    __decorate([
        core_1.Input(), 
        __metadata('design:type', ParameterAssignment_1.ParameterAssignment), 
        __metadata('design:paramtypes', [ParameterAssignment_1.ParameterAssignment])
    ], TestCaseValueForm.prototype, "paramAssignment", null);
    TestCaseValueForm = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'test-case-value-form',
            templateUrl: 'test-case-value-form.component.html',
            styleUrls: ['test-case-value-form.component.css']
        }), 
        __metadata('design:paramtypes', [specmate_data_service_1.SpecmateDataService])
    ], TestCaseValueForm);
    return TestCaseValueForm;
}());
exports.TestCaseValueForm = TestCaseValueForm;
//# sourceMappingURL=test-case-value-form.component.js.map