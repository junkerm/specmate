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
var Process_1 = require("../../model/Process");
var Type_1 = require("../../util/Type");
var CEGModel_1 = require("../../model/CEGModel");
var TestSpecification_1 = require("../../model/TestSpecification");
var specmate_data_service_1 = require("../../services/data/specmate-data.service");
var Id_1 = require("../../util/Id");
var Url_1 = require("../../util/Url");
var core_1 = require("@angular/core");
var router_1 = require("@angular/router");
var confirmation_modal_service_1 = require("../../services/notification/confirmation-modal.service");
var editor_common_control_service_1 = require("../../services/common-controls/editor-common-control.service");
var navigator_service_1 = require("../../services/navigation/navigator.service");
var specmate_view_base_1 = require("../core/views/specmate-view-base");
var Sort_1 = require("../../util/Sort");
var test_specification_factory_1 = require("../../factory/test-specification-factory");
var ceg_model_factory_1 = require("../../factory/ceg-model-factory");
var process_factory_1 = require("../../factory/process-factory");
var RequirementsDetails = (function (_super) {
    __extends(RequirementsDetails, _super);
    /** Constructor */
    function RequirementsDetails(dataService, navigator, route, modal, editorCommonControlService) {
        var _this = _super.call(this, dataService, navigator, route, modal, editorCommonControlService) || this;
        _this.cegModelType = CEGModel_1.CEGModel;
        _this.processModelType = Process_1.Process;
        return _this;
    }
    RequirementsDetails.prototype.onElementResolved = function (element) {
        var _this = this;
        this.requirement = element;
        this.dataService.readContents(this.requirement.url).then(function (contents) { return _this.contents = contents; });
        this.readTestSpecifications();
    };
    RequirementsDetails.prototype.readTestSpecifications = function () {
        var _this = this;
        this.dataService.performQuery(this.requirement.url, 'listRecursive', { class: TestSpecification_1.TestSpecification.className })
            .then(function (testSpecifications) { return _this.testSpecifications = Sort_1.Sort.sortArray(testSpecifications); });
    };
    RequirementsDetails.prototype.delete = function (element) {
        var _this = this;
        this.modal.open("Do you really want to delete '" + element.name + "'?")
            .then(function () { return _this.dataService.deleteElement(element.url, true, Id_1.Id.uuid); })
            .then(function () { return _this.dataService.commit('Delete'); })
            .then(function () { return _this.dataService.readContents(_this.requirement.url, true); })
            .then(function (contents) { return _this.contents = contents; })
            .then(function () { return _this.readTestSpecifications(); })
            .catch(function () { });
    };
    RequirementsDetails.prototype.createModel = function () {
        var _this = this;
        var factory = new ceg_model_factory_1.CEGModelFactory(this.dataService);
        factory.create(this.requirement, true).then(function (element) { return _this.navigator.navigate(element); });
    };
    RequirementsDetails.prototype.createProcess = function () {
        var _this = this;
        var factory = new process_factory_1.ProcessFactory(this.dataService);
        factory.create(this.requirement, true).then(function (element) { return _this.navigator.navigate(element); });
    };
    RequirementsDetails.prototype.createElement = function (element, name, description) {
        var _this = this;
        if (!this.contents) {
            return;
        }
        var factory;
        element.id = Id_1.Id.uuid;
        element.url = Url_1.Url.build([this.requirement.url, element.id]);
        element.name = name;
        element.description = description;
        this.dataService.createElement(element, true, Id_1.Id.uuid)
            .then(function () { return _this.dataService.commit('Create'); })
            .then(function () { return _this.dataService.readContents(Url_1.Url.parent(element.url), true); })
            .then(function (contents) { return _this.contents = contents; })
            .then(function () { return _this.navigator.navigate(element); });
    };
    RequirementsDetails.prototype.createTestSpecification = function () {
        var _this = this;
        var factory = new test_specification_factory_1.TestSpecificationFactory(this.dataService);
        factory.create(this.requirement, true).then(function (testSpec) { return _this.navigator.navigate(testSpec); });
    };
    Object.defineProperty(RequirementsDetails.prototype, "cegModels", {
        get: function () {
            var _this = this;
            if (!this.contents) {
                return [];
            }
            return this.contents.filter(function (element) { return Type_1.Type.is(element, _this.cegModelType); });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(RequirementsDetails.prototype, "processModels", {
        get: function () {
            var _this = this;
            if (!this.contents) {
                return [];
            }
            return this.contents.filter(function (element) { return Type_1.Type.is(element, _this.processModelType); });
        },
        enumerable: true,
        configurable: true
    });
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
}(specmate_view_base_1.SpecmateViewBase));
exports.RequirementsDetails = RequirementsDetails;
//# sourceMappingURL=requirement-details.component.js.map