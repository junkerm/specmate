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
var forms_1 = require('@angular/forms');
var core_1 = require('@angular/core');
var FormCheckboxInput = (function () {
    function FormCheckboxInput() {
    }
    Object.defineProperty(FormCheckboxInput.prototype, "control", {
        get: function () {
            return this.form.controls[this.meta.name];
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Object)
    ], FormCheckboxInput.prototype, "meta", void 0);
    __decorate([
        core_1.Input(), 
        __metadata('design:type', forms_1.FormGroup)
    ], FormCheckboxInput.prototype, "form", void 0);
    FormCheckboxInput = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'form-checkbox-input',
            templateUrl: 'form-checkbox-input.component.html'
        }), 
        __metadata('design:paramtypes', [])
    ], FormCheckboxInput);
    return FormCheckboxInput;
}());
exports.FormCheckboxInput = FormCheckboxInput;
//# sourceMappingURL=form-checkbox-input.component.js.map