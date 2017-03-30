"use strict";
var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var specmate_data_service_1 = require('../../../services/specmate-data.service');
var core_1 = require('@angular/core');
var forms_1 = require("@angular/forms");
var Type_1 = require('../../../util/Type');
var CEGNode_1 = require('../../../model/CEGNode');
var CEGConnection_1 = require('../../../model/CEGConnection');
var CEGEffectNode_1 = require("../../../model/CEGEffectNode");
var CEGCauseNode_1 = require("../../../model/CEGCauseNode");
var AbstractForm_1 = require("../../../controls/AbstractForm");
var field_meta_1 = require("../../../model/meta/field-meta");
var CEGNodeDetails = (function (_super) {
    __extends(CEGNodeDetails, _super);
    function CEGNodeDetails(formBuilder, dataService) {
        _super.call(this, formBuilder, dataService);
        this.update();
    }
    Object.defineProperty(CEGNodeDetails.prototype, "fieldMeta", {
        get: function () {
            if (this.element) {
                return field_meta_1.MetaInfo[this.element.className];
            }
            return [];
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGNodeDetails.prototype, "formModel", {
        get: function () {
            return this.element;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGNodeDetails.prototype, "element", {
        get: function () {
            return this._element;
        },
        set: function (element) {
            this._element = element;
            this.createForm();
            this.update();
        },
        enumerable: true,
        configurable: true
    });
    CEGNodeDetails.prototype.setUpChangeListener = function () {
        var _this = this;
        this.inputForm.valueChanges.subscribe(function () {
            _this.updateFormModel();
            return '';
        });
    };
    Object.defineProperty(CEGNodeDetails.prototype, "isNode", {
        get: function () {
            return Type_1.Type.is(this.element, CEGNode_1.CEGNode) || Type_1.Type.is(this.element, CEGCauseNode_1.CEGCauseNode) || Type_1.Type.is(this.element, CEGEffectNode_1.CEGEffectNode);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGNodeDetails.prototype, "isConnection", {
        get: function () {
            return Type_1.Type.is(this.element, CEGConnection_1.CEGConnection);
        },
        enumerable: true,
        configurable: true
    });
    CEGNodeDetails.prototype.update = function () {
        this.updateForm();
        this.setUpChangeListener();
    };
    __decorate([
        core_1.Input(), 
        __metadata('design:type', CEGNode_1.CEGNode)
    ], CEGNodeDetails.prototype, "element", null);
    CEGNodeDetails = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'ceg-node-details',
            templateUrl: 'ceg-node-details.component.html'
        }), 
        __metadata('design:paramtypes', [forms_1.FormBuilder, specmate_data_service_1.SpecmateDataService])
    ], CEGNodeDetails);
    return CEGNodeDetails;
}(AbstractForm_1.AbstractForm));
exports.CEGNodeDetails = CEGNodeDetails;
//# sourceMappingURL=ceg-node-details.component.js.map