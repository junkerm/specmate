"use strict";
var forms_1 = require("@angular/forms");
var AbstractForm = (function () {
    function AbstractForm(formBuilder, dataService) {
        this.formBuilder = formBuilder;
        this.dataService = dataService;
        this.errorMessage = 'This field is required.';
        this.createForm();
    }
    AbstractForm.prototype.createForm = function () {
        if (!this.fieldMeta) {
            this.inputForm = this.formBuilder.group({});
            return;
        }
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
        for (var i = 0; i < this.fieldMeta.length; i++) {
            var fieldMeta = this.fieldMeta[i];
            var fieldName = fieldMeta.name;
            var updateValue = this.inputForm.controls[fieldName].value;
            if (!updateValue) {
                updateValue = '';
            }
            if (fieldMeta.type === FieldType.CHECKBOX) {
                if (updateValue === '' || updateValue === 'false') {
                    updateValue = false;
                }
                else {
                    updateValue = true;
                }
            }
            // We do not need to clone here (hopefully), because only simple values can be passed via forms.
            this.formModel[fieldName] = updateValue;
        }
        this.dataService.updateElement(this.formModel, true);
    };
    AbstractForm.prototype.updateForm = function () {
        if (!this.formModel) {
            return;
        }
        var updateObject = {};
        for (var i = 0; i < this.fieldMeta.length; i++) {
            var fieldMeta = this.fieldMeta[i];
            var fieldName = fieldMeta.name;
            updateObject[fieldName] = this.formModel[fieldName] || '';
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
    return AbstractForm;
}());
exports.AbstractForm = AbstractForm;
var FieldType = (function () {
    function FieldType() {
    }
    FieldType.TEXT = 'TEXT';
    FieldType.TEXT_LONG = 'TEXT_LONG';
    FieldType.CHECKBOX = 'CHECKBOX';
    return FieldType;
}());
exports.FieldType = FieldType;
//# sourceMappingURL=AbstractForm.js.map