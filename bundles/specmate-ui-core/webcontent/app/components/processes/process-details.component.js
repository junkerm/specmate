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
var specmate_view_base_1 = require("../core/views/specmate-view-base");
var core_1 = require("@angular/core");
var specmate_data_service_1 = require("../../services/data/specmate-data.service");
var navigator_service_1 = require("../../services/navigation/navigator.service");
var router_1 = require("@angular/router");
var confirmation_modal_service_1 = require("../../services/notification/confirmation-modal.service");
var editor_common_control_service_1 = require("../../services/common-controls/editor-common-control.service");
var graphical_editor_component_1 = require("../core/graphical/graphical-editor.component");
var ProcessDetails = (function (_super) {
    __extends(ProcessDetails, _super);
    function ProcessDetails(dataService, navigator, route, modal, editorCommonControlService) {
        return _super.call(this, dataService, navigator, route, modal, editorCommonControlService) || this;
    }
    ProcessDetails.prototype.onElementResolved = function (element) {
        this.model = element;
    };
    Object.defineProperty(ProcessDetails.prototype, "isValid", {
        get: function () {
            if (!this.processEditor) {
                return true;
            }
            return this.processEditor.isValid;
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        core_1.ViewChild(graphical_editor_component_1.GraphicalEditor),
        __metadata("design:type", graphical_editor_component_1.GraphicalEditor)
    ], ProcessDetails.prototype, "processEditor", void 0);
    ProcessDetails = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'process-details',
            templateUrl: 'process-details.component.html',
            styleUrls: ['process-details.component.css']
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService,
            navigator_service_1.NavigatorService,
            router_1.ActivatedRoute,
            confirmation_modal_service_1.ConfirmationModal,
            editor_common_control_service_1.EditorCommonControlService])
    ], ProcessDetails);
    return ProcessDetails;
}(specmate_view_base_1.SpecmateViewBase));
exports.ProcessDetails = ProcessDetails;
//# sourceMappingURL=process-details.component.js.map