"use strict";
var Arrays_1 = require("../util/Arrays");
var forms_1 = require("@angular/forms");
var AbstractForm = (function () {
    function AbstractForm(formBuilder) {
        this.formBuilder = formBuilder;
        this.createForm();
    }
    AbstractForm.prototype.createForm = function () {
        var formBuilderObject = {};
        for (var i = 0; i < this.formFields.length; i++) {
            var fieldName = this.formFields[i];
            var formBuilderObjectValue = [''];
            if (Arrays_1.Arrays.contains(this.requiredFields, fieldName)) {
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
        for (var i = 0; i < this.formFields.length; i++) {
            var fieldName = this.formFields[i];
            var updateValue = this.inputForm.controls[fieldName].value;
            if (!updateValue) {
                updateValue = '';
            }
            this.formModel[fieldName] = updateValue;
        }
    };
    AbstractForm.prototype.updateForm = function () {
        var updateObject = {};
        for (var i = 0; i < this.formFields.length; i++) {
            var fieldName = this.formFields[i];
            updateObject[fieldName] = this.formModel[fieldName];
        }
        this.inputForm.setValue(updateObject);
    };
    return AbstractForm;
}());
exports.AbstractForm = AbstractForm;
//# sourceMappingURL=AbstractForm.js.map