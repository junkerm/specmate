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
var editor_common_control_service_1 = require("../../../services/common-controls/editor-common-control.service");
var specmate_view_base_1 = require("../../core/views/specmate-view-base");
var graphical_editor_component_1 = require("../../core/graphical/graphical-editor.component");
var CEGModelDetails = (function (_super) {
    __extends(CEGModelDetails, _super);
    /** Constructor */
    function CEGModelDetails(dataService, navigator, route, modal, editorCommonControlService) {
        return _super.call(this, dataService, navigator, route, modal, editorCommonControlService) || this;
    }
    CEGModelDetails.prototype.resolveRequirement = function (element) {
        return this.dataService.readElement(Url_1.Url.parent(element.url)).then(function (element) { return element; });
    };
    CEGModelDetails.prototype.onElementResolved = function (element) {
        var _this = this;
        this.model = element;
        this.dataService.readContents(this.model.url).then(function (contents) { return _this.contents = contents; });
    };
    Object.defineProperty(CEGModelDetails.prototype, "isValid", {
        get: function () {
            if (!this.cegEditor) {
                return true;
            }
            return this.cegEditor.isValid;
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        core_1.ViewChild(graphical_editor_component_1.GraphicalEditor),
        __metadata("design:type", graphical_editor_component_1.GraphicalEditor)
    ], CEGModelDetails.prototype, "cegEditor", void 0);
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
            editor_common_control_service_1.EditorCommonControlService])
    ], CEGModelDetails);
    return CEGModelDetails;
}(specmate_view_base_1.SpecmateViewBase));
exports.CEGModelDetails = CEGModelDetails;
//# sourceMappingURL=ceg-model-details.component.js.map