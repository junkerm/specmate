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
var core_1 = require("@angular/core");
var forms_1 = require("@angular/forms");
var specmate_data_service_1 = require("../../../services/specmate-data.service");
var AbstractForm = (function () {
    function AbstractForm(formBuilder, dataService) {
        this.formBuilder = formBuilder;
        this.dataService = dataService;
        this.errorMessage = 'This field is required.';
        this.createForm();
    }
    AbstractForm.prototype.orderFieldMeta = function () {
        this.fieldMeta.sort(function (item1, item2) { return Number.parseInt(item1.position) - Number.parseInt(item2.position); });
    };
    AbstractForm.prototype.createForm = function () {
        if (!this.fieldMeta) {
            this.inputForm = this.formBuilder.group({});
            return;
        }
        this.orderFieldMeta();
        var formBuilderObject = {};
        for (var i = 0; i < this.fieldMeta.length; i++) {
            var fieldMeta = this.fieldMeta[i];
            var fieldName = fieldMeta.name;
            var formBuilderObjectValue = [''];
            if (this.fieldMeta[i].required) {
                formBuilderObjectValue.push(forms_1.Validators.required);
            }
            formBuilderObject[fieldName] = formBuilderObjectValue;
        }
        this.inputForm = this.formBuilder.group(formBuilderObject);
    };
    AbstractForm.prototype.updateFormModel = function () {
        if (!this.inputForm.valid) {
            return;
        }
        // We need this, since in some cases, the update event on th control is fired, even though the data did actually not change. We want to prevent unnecessary updates.
        var changed = false;
        for (var i = 0; i < this.fieldMeta.length; i++) {
            var fieldMeta = this.fieldMeta[i];
            var fieldName = fieldMeta.name;
            var updateValue = this.inputForm.controls[fieldName].value;
            if (updateValue === undefined) {
                updateValue = '';
            }
            // We do not need to clone here (hopefully), because only simple values can be passed via forms.
            if (this.formModel[fieldName] !== updateValue) {
                this.formModel[fieldName] = updateValue;
                changed = true;
            }
        }
        if (changed) {
            this.dataService.updateElement(this.formModel, true);
        }
        else {
            console.log("SKIPPING UPDATE");
        }
    };
    AbstractForm.prototype.updateForm = function () {
        if (!this.formModel) {
            return;
        }
        var updateObject = {};
        for (var i = 0; i < this.fieldMeta.length; i++) {
            var fieldMeta = this.fieldMeta[i];
            var fieldName = fieldMeta.name;
            var value = this.formModel[fieldName] || '';
            if (AbstractForm.isBoolean(value)) {
                value = AbstractForm.convertToBoolean(value);
            }
            updateObject[fieldName] = value;
        }
        this.inputForm.setValue(updateObject);
    };
    Object.defineProperty(AbstractForm.prototype, "isValid", {
        get: function () {
            return this.inputForm.valid;
        },
        enumerable: true,
        configurable: true
    });
    AbstractForm.isBoolean = function (str) {
        return AbstractForm.convertToBoolean(str) !== undefined;
    };
    AbstractForm.convertToBoolean = function (str) {
        if (str.toLowerCase() === 'true') {
            return true;
        }
        else if (str === '' || str.toLocaleLowerCase() === 'false') {
            return false;
        }
        return undefined;
    };
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Object)
    ], AbstractForm.prototype, "formModel", void 0);
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Array)
    ], AbstractForm.prototype, "fieldMeta", void 0);
    AbstractForm = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'input-form',
            templateUrl: 'abstract-form.component.html'
        }), 
        __metadata('design:paramtypes', [forms_1.FormBuilder, specmate_data_service_1.SpecmateDataService])
    ], AbstractForm);
    return AbstractForm;
}());
exports.AbstractForm = AbstractForm;
var FieldType = (function () {
    function FieldType() {
    }
    FieldType.TEXT = 'text';
    FieldType.TEXT_LONG = 'longText';
    FieldType.CHECKBOX = 'checkbox';
    FieldType.SINGLE_SELECT = 'singleSelect';
    return FieldType;
}());
exports.FieldType = FieldType;
//# sourceMappingURL=abstract-form.component.js.map