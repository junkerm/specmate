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
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
Object.defineProperty(exports, "__esModule", { value: true });
var confirmation_modal_service_1 = require("../../../services/notification/confirmation-modal.service");
var navigator_service_1 = require("../../../services/navigation/navigator.service");
var core_1 = require("@angular/core");
var router_1 = require("@angular/router");
var specmate_data_service_1 = require("../../../services/data/specmate-data.service");
var Url_1 = require("../../../util/Url");
var Type_1 = require("../../../util/Type");
var generic_form_component_1 = require("../../forms/generic-form.component");
var editor_common_control_service_1 = require("../../../services/common-controls/editor-common-control.service");
var TestSpecification_1 = require("../../../model/TestSpecification");
var graphical_editor_component_1 = require("../../core/graphical/graphical-editor.component");
var test_specification_generator_1 = require("../../core/common/test-specification-generator");
var CEGModelDetails = (function (_super) {
    __extends(CEGModelDetails, _super);
    /** Constructor */
    function CEGModelDetails(dataService, navigator, route, modal, editorCommonControlService, changeDetectorRef) {
        var _this = _super.call(this, dataService, modal, route, navigator, editorCommonControlService) || this;
        _this.changeDetectorRef = changeDetectorRef;
        return _this;
    }
    CEGModelDetails.prototype.ngDoCheck = function () {
        _super.prototype.ngDoCheck.call(this);
        this.changeDetectorRef.detectChanges();
        if (this.model && this.contents) {
            this.doCheckCanCreateTestSpec(this.model, this.contents);
        }
    };
    CEGModelDetails.prototype.resolveRequirement = function (element) {
        return this.dataService.readElement(Url_1.Url.parent(element.url)).then(function (element) { return element; });
    };
    CEGModelDetails.prototype.onElementResolved = function (element) {
        var _this = this;
        _super.prototype.onElementResolved.call(this, element);
        this.model = element;
        this.dataService.readContents(this.model.url).then(function (contents) { return _this.contents = contents; });
    };
    Object.defineProperty(CEGModelDetails.prototype, "isValid", {
        get: function () {
            if (!this.cegEditor || !this.form) {
                return true;
            }
            return this.cegEditor.isValid && this.form.isValid;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGModelDetails.prototype, "testSpecifications", {
        get: function () {
            if (!this.contents) {
                return undefined;
            }
            return this.contents.filter(function (element) { return Type_1.Type.is(element, TestSpecification_1.TestSpecification); });
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        core_1.ViewChild(graphical_editor_component_1.GraphicalEditor),
        __metadata("design:type", graphical_editor_component_1.GraphicalEditor)
    ], CEGModelDetails.prototype, "cegEditor", void 0);
    __decorate([
        core_1.ViewChild(generic_form_component_1.GenericForm),
        __metadata("design:type", generic_form_component_1.GenericForm)
    ], CEGModelDetails.prototype, "form", void 0);
    CEGModelDetails = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'ceg-model-details-editor',
            templateUrl: 'ceg-model-details.component.html'
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService,
            navigator_service_1.NavigatorService,
            router_1.ActivatedRoute,
            confirmation_modal_service_1.ConfirmationModal,
            editor_common_control_service_1.EditorCommonControlService,
            core_1.ChangeDetectorRef])
    ], CEGModelDetails);
    return CEGModelDetails;
}(test_specification_generator_1.TestSpecificationGenerator));
exports.CEGModelDetails = CEGModelDetails;
//# sourceMappingURL=ceg-model-details.component.js.map