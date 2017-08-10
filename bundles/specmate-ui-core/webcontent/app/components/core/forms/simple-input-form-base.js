"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var Id_1 = require("../../../util/Id");
var forms_1 = require("@angular/forms");
var SimpleInputFormBase = (function () {
    function SimpleInputFormBase() {
        this.formGroup = new forms_1.FormGroup({});
    }
    Object.defineProperty(SimpleInputFormBase.prototype, "modelElement", {
        get: function () {
            return this._modelElement;
        },
        set: function (modelElement) {
            this._modelElement = modelElement;
            this.buildFormGroup();
        },
        enumerable: true,
        configurable: true
    });
    SimpleInputFormBase.prototype.ngDoCheck = function (args) {
        this.updateFormGroupIfChanged(this.modelElement, this.fields);
    };
    SimpleInputFormBase.prototype.updateFormGroupIfChanged = function (modelElement, fields) {
        var _this = this;
        if (!modelElement) {
            return;
        }
        var changed = false;
        fields.forEach(function (field) {
            var currentFormValue = _this.formGroup.controls[field].value;
            var currentModelValue = modelElement[field];
            if (currentFormValue !== currentModelValue) {
                changed = true;
            }
        });
        if (changed) {
            var formBuilderObject_1 = {};
            fields.forEach(function (field) {
                formBuilderObject_1[field] = _this.modelElement[field];
            });
            this.formGroup.setValue(formBuilderObject_1);
        }
    };
    SimpleInputFormBase.prototype.buildFormGroup = function () {
        var _this = this;
        this.formGroup = this.buildFormGroupObject(this.modelElement, this.fields);
        this.formGroup.valueChanges.subscribe(function () { return _this.updateModelPropertiesIfChanged(_this.modelElement, _this.fields); });
    };
    SimpleInputFormBase.prototype.buildFormGroupObject = function (modelElement, fields) {
        var formGroupObject = {};
        for (var i = 0; i < fields.length; i++) {
            var currentField = fields[i];
            formGroupObject[currentField] = new forms_1.FormControl(modelElement[currentField], forms_1.Validators.required);
        }
        return new forms_1.FormGroup(formGroupObject);
    };
    SimpleInputFormBase.prototype.updateModelPropertiesIfChanged = function (modelElement, fields) {
        var changed = false;
        for (var i = 0; i < fields.length; i++) {
            var currentChanged = this.updateModelPropertyIfChanged(modelElement, fields[i]);
            if (currentChanged) {
                changed = true;
            }
        }
        if (changed) {
            this.dataService.updateElement(modelElement, true, Id_1.Id.uuid);
        }
    };
    SimpleInputFormBase.prototype.updateModelPropertyIfChanged = function (modelElement, field) {
        var formValue = this.formGroup.controls[field].value;
        var modelValue = modelElement[field];
        if (modelValue !== formValue) {
            modelElement[field] = formValue;
            return true;
        }
        return false;
    };
    return SimpleInputFormBase;
}());
exports.SimpleInputFormBase = SimpleInputFormBase;
//# sourceMappingURL=simple-input-form-base.js.map