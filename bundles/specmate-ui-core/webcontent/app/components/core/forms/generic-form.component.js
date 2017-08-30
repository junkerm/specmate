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
var Id_1 = require("../../../util/Id");
var core_1 = require("@angular/core");
var forms_1 = require("@angular/forms");
var field_meta_1 = require("../../../model/meta/field-meta");
var specmate_data_service_1 = require("../../../services/data/specmate-data.service");
var Type_1 = require("../../../util/Type");
var converters_1 = require("./conversion/converters");
var Arrays_1 = require("../../../util/Arrays");
var GenericForm = (function () {
    function GenericForm(formBuilder, dataService) {
        this.formBuilder = formBuilder;
        this.dataService = dataService;
        this.errorMessage = 'This field is invalid.';
        this.ready = false;
        this.initEmpty();
    }
    GenericForm_1 = GenericForm;
    Object.defineProperty(GenericForm.prototype, "element", {
        get: function () {
            return this._element;
        },
        set: function (element) {
            if (!element) {
                return;
            }
            if (!this._element || !Type_1.Type.is(this._element, element)) {
                this._element = element;
                this.ready = false;
                this.createForm();
            }
            else {
                this._element = element;
                this.updateForm();
            }
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GenericForm.prototype, "meta", {
        get: function () {
            var _this = this;
            if (!this._meta) {
                return [];
            }
            return this._meta.filter(function (metaItem) { return !Arrays_1.Arrays.contains(_this.hiddenFields, metaItem.name); });
        },
        enumerable: true,
        configurable: true
    });
    GenericForm.prototype.ngDoCheck = function (args) {
        this.updateForm();
    };
    GenericForm.prototype.orderFieldMeta = function () {
        this._meta.sort(function (item1, item2) { return Number.parseInt(item1.position) - Number.parseInt(item2.position); });
    };
    GenericForm.prototype.initEmpty = function () {
        this.formGroup = this.formBuilder.group({});
    };
    GenericForm.prototype.createForm = function () {
        var _this = this;
        if (!this._element) {
            return;
        }
        this._meta = field_meta_1.MetaInfo[this.element.className];
        if (!this._meta) {
            this.initEmpty();
            return;
        }
        this.orderFieldMeta();
        var formBuilderObject = {};
        for (var i = 0; i < this._meta.length; i++) {
            var fieldMeta = this._meta[i];
            var fieldName = fieldMeta.name;
            var formBuilderObjectValue = [''];
            if (this._meta[i].required) {
                formBuilderObjectValue.push(forms_1.Validators.required);
            }
            formBuilderObject[fieldName] = formBuilderObjectValue;
        }
        this.formGroup = this.formBuilder.group(formBuilderObject);
        this.formGroup.valueChanges.subscribe(function () {
            _this.updateFormModel();
            return '';
        });
        this.ready = true;
        this.updateForm();
    };
    GenericForm.prototype.updateForm = function () {
        if (!this.ready) {
            return;
        }
        var changed = false;
        var formBuilderObject = {};
        for (var i = 0; i < this._meta.length; i++) {
            var fieldMeta = this._meta[i];
            var fieldName = fieldMeta.name;
            var fieldType = fieldMeta.type;
            var updateValue = this.element[fieldName] || '';
            var converter = converters_1.Converters[fieldMeta.type];
            if (converter) {
                updateValue = converter.convertFromModelToControl(updateValue);
            }
            formBuilderObject[fieldName] = updateValue;
            if (this.formGroup.controls[fieldName].value !== updateValue) {
                changed = true;
            }
        }
        if (changed) {
            this.formGroup.setValue(formBuilderObject);
        }
    };
    GenericForm.prototype.updateFormModel = function () {
        // We need this, since in some cases, the update event on the control is fired, even though the data did actually not change. We want to prevent unnecessary updates.
        var changed = false;
        for (var i = 0; i < this._meta.length; i++) {
            var fieldMeta = this._meta[i];
            var fieldName = fieldMeta.name;
            var updateValue = this.formGroup.controls[fieldName].value;
            if (updateValue === undefined) {
                updateValue = '';
            }
            var converter = converters_1.Converters[fieldMeta.type];
            if (converter) {
                updateValue = converter.convertFromControlToModel(updateValue);
            }
            // We do not need to clone here (hopefully), because only simple values can be passed via forms.
            if (this.element[fieldName] + '' !== updateValue + '') {
                this.element[fieldName] = updateValue;
                changed = true;
            }
        }
        if (changed && this.isValid) {
            this.dataService.updateElement(this.element, true, Id_1.Id.uuid);
        }
    };
    Object.defineProperty(GenericForm.prototype, "isValid", {
        get: function () {
            return this.formGroup.valid;
        },
        enumerable: true,
        configurable: true
    });
    GenericForm.isBooleanText = function (str) {
        return GenericForm_1.convertToBoolean(str) !== undefined;
    };
    GenericForm.convertToBoolean = function (str) {
        if (typeof (str) === 'boolean') {
            return str;
        }
        if (str.toLowerCase && str.toLowerCase() === 'true') {
            return true;
        }
        else if (str === '' || (str.toLowerCase && str.toLocaleLowerCase() === 'false')) {
            return false;
        }
        return undefined;
    };
    __decorate([
        core_1.Input(),
        __metadata("design:type", Object),
        __metadata("design:paramtypes", [Object])
    ], GenericForm.prototype, "element", null);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Array)
    ], GenericForm.prototype, "hiddenFields", void 0);
    GenericForm = GenericForm_1 = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'generic-form',
            templateUrl: 'generic-form.component.html'
        }),
        __metadata("design:paramtypes", [forms_1.FormBuilder, specmate_data_service_1.SpecmateDataService])
    ], GenericForm);
    return GenericForm;
    var GenericForm_1;
}());
exports.GenericForm = GenericForm;
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
//# sourceMappingURL=generic-form.component.js.map