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
var config_1 = require("../../config/config");
var CEGModel_1 = require("../../model/CEGModel");
var specmate_data_service_1 = require("../../services/specmate-data.service");
var Id_1 = require("../../util/Id");
var Url_1 = require("../../util/Url");
var core_1 = require("@angular/core");
var router_1 = require("@angular/router");
var confirmation_modal_service_1 = require("../core/forms/confirmation-modal.service");
var editor_common_control_service_1 = require("../../services/editor-common-control.service");
var navigator_service_1 = require("../../services/navigator.service");
var test_specification_generator_1 = require("./test-specification-generator");
var RequirementsDetails = (function (_super) {
    __extends(RequirementsDetails, _super);
    /** Constructor */
    function RequirementsDetails(dataService, navigator, route, modal, editorCommonControlService) {
        var _this = _super.call(this, dataService, modal, route, navigator, editorCommonControlService) || this;
        _this.cegModelType = CEGModel_1.CEGModel;
        return _this;
    }
    RequirementsDetails.prototype.resolveRequirement = function (element) {
        return Promise.resolve(element);
    };
    RequirementsDetails.prototype.delete = function (element) {
        var _this = this;
        this.modal.open("Do you really want to delete " + element.name + "?")
            .then(function () { return _this.dataService.deleteElement(element.url, true, Id_1.Id.uuid); })
            .then(function () { return _this.dataService.commit('Delete'); })
            .then(function () { return _this.dataService.readContents(_this.requirement.url, true); })
            .then(function (contents) { return _this.requirementContents = contents; })
            .then(function () { return _this.readAllTestSpecifications(); })
            .catch(function () { });
    };
    RequirementsDetails.prototype.createModel = function () {
        var _this = this;
        if (!this.requirementContents) {
            return;
        }
        var model = new CEGModel_1.CEGModel();
        model.id = Id_1.Id.uuid;
        var modelUrl = Url_1.Url.build([this.requirement.url, model.id]);
        model.url = modelUrl;
        model.name = config_1.Config.CEG_NEW_MODEL_NAME;
        model.description = config_1.Config.CEG_NEW_NODE_DESCRIPTION;
        this.dataService.createElement(model, true, Id_1.Id.uuid)
            .then(function () { return _this.dataService.readContents(model.url, true); })
            .then(function (contents) { return _this.requirementContents = contents; })
            .then(function () { return _this.dataService.commit('Create'); })
            .then(function () { return _this.navigator.navigate(model); });
    };
    Object.defineProperty(RequirementsDetails.prototype, "isValid", {
        get: function () {
            return true;
        },
        enumerable: true,
        configurable: true
    });
    RequirementsDetails = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'requirements-details',
            templateUrl: 'requirement-details.component.html',
            styleUrls: ['requirement-details.component.css']
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService,
            navigator_service_1.NavigatorService,
            router_1.ActivatedRoute,
            confirmation_modal_service_1.ConfirmationModal,
            editor_common_control_service_1.EditorCommonControlService])
    ], RequirementsDetails);
    return RequirementsDetails;
}(test_specification_generator_1.TestSpecificationGenerator));
exports.RequirementsDetails = RequirementsDetails;
//# sourceMappingURL=requirement-details.component.js.map