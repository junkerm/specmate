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
var confirmation_modal_service_1 = require("../../core/forms/confirmation-modal.service");
var navigator_service_1 = require("../../../services/navigator.service");
var ceg_editor_component_1 = require("./ceg-editor.component");
var core_1 = require("@angular/core");
var router_1 = require("@angular/router");
var specmate_data_service_1 = require("../../../services/specmate-data.service");
var CEGModel_1 = require("../../../model/CEGModel");
require("rxjs/add/operator/switchMap");
require("rxjs/add/operator/reduce");
var generic_form_component_1 = require("../../core/forms/generic-form.component");
var editor_common_control_service_1 = require("../../../services/editor-common-control.service");
var specmate_view_base_1 = require("../../core/views/specmate-view-base");
var ModelEditor = (function (_super) {
    __extends(ModelEditor, _super);
    /** Constructor */
    function ModelEditor(dataService, navigator, route, modal, editorCommonControlService, changeDetectorRef) {
        var _this = _super.call(this, dataService, navigator, route, modal, editorCommonControlService) || this;
        _this.changeDetectorRef = changeDetectorRef;
        return _this;
    }
    Object.defineProperty(ModelEditor, "modelElementClass", {
        get: function () {
            return CEGModel_1.CEGModel;
        },
        enumerable: true,
        configurable: true
    });
    ModelEditor.prototype.ngDoCheck = function () {
        _super.prototype.ngDoCheck.call(this);
        this.changeDetectorRef.detectChanges();
    };
    ModelEditor.prototype.onElementResolved = function (element) {
        var _this = this;
        this.model = element;
        this.dataService.readContents(this.model.url).then(function (contents) {
            _this.contents = contents;
        });
    };
    Object.defineProperty(ModelEditor.prototype, "isValid", {
        get: function () {
            if (!this.cegEditor || !this.form) {
                return true;
            }
            return this.cegEditor.isValid && this.form.isValid;
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        core_1.ViewChild(ceg_editor_component_1.CEGEditor),
        __metadata("design:type", ceg_editor_component_1.CEGEditor)
    ], ModelEditor.prototype, "cegEditor", void 0);
    __decorate([
        core_1.ViewChild(generic_form_component_1.GenericForm),
        __metadata("design:type", generic_form_component_1.GenericForm)
    ], ModelEditor.prototype, "form", void 0);
    ModelEditor = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'model-editor',
            templateUrl: 'model-editor.component.html'
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService,
            navigator_service_1.NavigatorService,
            router_1.ActivatedRoute,
            confirmation_modal_service_1.ConfirmationModal,
            editor_common_control_service_1.EditorCommonControlService,
            core_1.ChangeDetectorRef])
    ], ModelEditor);
    return ModelEditor;
}(specmate_view_base_1.SpecmateViewBase));
exports.ModelEditor = ModelEditor;
//# sourceMappingURL=model-editor.component.js.map