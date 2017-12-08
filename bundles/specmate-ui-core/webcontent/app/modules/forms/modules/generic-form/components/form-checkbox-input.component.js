"use strict";
var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var form_element_1 = require("../base/form-element");
var FormCheckboxInput = /** @class */ (function (_super) {
    __extends(FormCheckboxInput, _super);
    function FormCheckboxInput() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    Object.defineProperty(FormCheckboxInput.prototype, "control", {
        get: function () {
            return this.form.controls[this.meta.name];
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(FormCheckboxInput.prototype, "value", {
        get: function () {
            return this.control.value;
        },
        set: function (val) {
            this.control.setValue(val);
        },
        enumerable: true,
        configurable: true
    });
    FormCheckboxInput = __decorate([
        core_1.Component({
            moduleId: module.id.toString(),
            selector: '[form-checkbox-input]',
            templateUrl: 'form-checkbox-input.component.html'
        })
    ], FormCheckboxInput);
    return FormCheckboxInput;
}(form_element_1.FormElement));
exports.FormCheckboxInput = FormCheckboxInput;
//# sourceMappingURL=form-checkbox-input.component.js.map