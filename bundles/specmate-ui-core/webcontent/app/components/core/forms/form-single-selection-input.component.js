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
var core_1 = require('@angular/core');
var forms_1 = require('@angular/forms');
var field_meta_1 = require("../../../model/meta/field-meta");
var FormSingleSelectionInput = (function () {
    function FormSingleSelectionInput() {
    }
    Object.defineProperty(FormSingleSelectionInput.prototype, "options", {
        get: function () {
            return JSON.parse(this.meta.values);
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        core_1.Input(), 
        __metadata('design:type', field_meta_1.FieldMetaItem)
    ], FormSingleSelectionInput.prototype, "meta", void 0);
    __decorate([
        core_1.Input(), 
        __metadata('design:type', forms_1.FormGroup)
    ], FormSingleSelectionInput.prototype, "form", void 0);
    FormSingleSelectionInput = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'form-single-selection-input',
            templateUrl: 'form-single-selection-input.component.html'
        }), 
        __metadata('design:paramtypes', [])
    ], FormSingleSelectionInput);
    return FormSingleSelectionInput;
}());
exports.FormSingleSelectionInput = FormSingleSelectionInput;
//# sourceMappingURL=form-single-selection-input.component.js.map