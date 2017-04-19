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
require('rxjs/add/operator/switchMap');
var config_1 = require('../../config/config');
var CEGModel_1 = require('../../model/CEGModel');
var TestSpecification_1 = require('../../model/TestSpecification');
var specmate_data_service_1 = require('../../services/specmate-data.service');
var Id_1 = require('../../util/Id');
var Url_1 = require('../../util/Url');
var core_1 = require('@angular/core');
var router_1 = require('@angular/router');
var confirmation_modal_service_1 = require("../core/forms/confirmation-modal.service");
var RequirementsDetails = (function () {
    function RequirementsDetails(dataService, router, route, modal) {
        this.dataService = dataService;
        this.router = router;
        this.route = route;
        this.modal = modal;
    }
    RequirementsDetails.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params
            .switchMap(function (params) { return _this.dataService.readElement(Url_1.Url.fromParams(params)); })
            .subscribe(function (requirement) {
            _this.requirement = requirement;
            _this.dataService.readContents(requirement.url).then(function (contents) {
                _this.contents = contents;
            });
        });
    };
    RequirementsDetails.prototype.delete = function (model) {
        var _this = this;
        this.modal.open("Do you really want to delete the model " + model.name + "?")
            .then(function () { return _this.dataService.deleteElement(model.url); })
            .then(function () { return _this.dataService.readContents(_this.requirement.url, true); })
            .then(function (contents) { return _this.contents = contents; })
            .catch(function () { });
    };
    RequirementsDetails.prototype.createModel = function () {
        var _this = this;
        if (!this.contents) {
            return;
        }
        var model = new CEGModel_1.CEGModel();
        model.id = Id_1.Id.generate(this.contents, config_1.Config.CEG_MODEL_BASE_ID);
        var modelUrl = Url_1.Url.build([this.requirement.url, model.id]);
        model.url = modelUrl;
        model.name = config_1.Config.CEG_NEW_MODEL_NAME;
        model.description = config_1.Config.CEG_NEW_NODE_DESCRIPTION;
        this.dataService.createElement(model, true)
            .then(function () { return _this.dataService.readContents(model.url, true); })
            .then(function (contents) { return _this.contents = contents; })
            .then(function () { return _this.dataService.commit('Create'); })
            .then(function () { return _this.router.navigate(['/requirements', { outlets: { 'main': [modelUrl, 'ceg'] } }]); });
    };
    RequirementsDetails.prototype.createTestSpecification = function (ceg) {
        var _this = this;
        if (!this.contents) {
            return;
        }
        var testSpec = new TestSpecification_1.TestSpecification();
        testSpec.id = Id_1.Id.generate(this.contents, config_1.Config.TESTSPEC_BASE_ID);
        var testSpecUrl = Url_1.Url.build([ceg.url, testSpec.id]);
        testSpec.url = testSpecUrl;
        testSpec.name = config_1.Config.TESTSPEC_NAME;
        testSpec.description = config_1.Config.TESTSPEC_DESCRIPTION;
        this.dataService.createElement(testSpec, true)
            .then(function () { return _this.dataService.readContents(ceg.url, true); })
            .then(function () { return _this.dataService.commit('Create'); })
            .then(function () { return _this.router.navigate(['/tests', { outlets: { 'main': [testSpecUrl] } }]); });
    };
    RequirementsDetails = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'requirements-details',
            templateUrl: 'requirement-details.component.html',
            styleUrls: ['requirement-details.component.css']
        }), 
        __metadata('design:paramtypes', [specmate_data_service_1.SpecmateDataService, router_1.Router, router_1.ActivatedRoute, confirmation_modal_service_1.ConfirmationModal])
    ], RequirementsDetails);
    return RequirementsDetails;
}());
exports.RequirementsDetails = RequirementsDetails;
//# sourceMappingURL=requirement-details.component.js.map