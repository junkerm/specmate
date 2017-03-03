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
var core_1 = require('@angular/core');
var common_1 = require('@angular/common');
var router_1 = require('@angular/router');
var specmate_data_service_1 = require('../../services/specmate-data.service');
var CEGModel_1 = require('../../model/CEGModel');
var Requirement_1 = require('../../model/Requirement');
var Type_1 = require('../../util/Type');
var RequirementsCEGEditor = (function () {
    function RequirementsCEGEditor(dataService, route, location) {
        this.dataService = dataService;
        this.route = route;
        this.location = location;
    }
    RequirementsCEGEditor.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params
            .switchMap(function (params) { return _this.dataService.getDetails(params['url']); })
            .subscribe(function (object) {
            if (Type_1.Type.is(object, CEGModel_1.CEGModel)) {
                _this.model = object;
            }
            else if (Type_1.Type.is(object, Requirement_1.Requirement)) {
                _this.model = new CEGModel_1.CEGModel();
                _this.model.name = "New Model";
                _this.model.url = object.url + '/' + _this.model.name;
            }
        });
    };
    RequirementsCEGEditor.prototype.discard = function () {
        this.location.back();
    };
    RequirementsCEGEditor = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'ceg-editor',
            templateUrl: 'requirements-ceg-editor.component.html'
        }), 
        __metadata('design:paramtypes', [specmate_data_service_1.SpecmateDataService, router_1.ActivatedRoute, common_1.Location])
    ], RequirementsCEGEditor);
    return RequirementsCEGEditor;
}());
exports.RequirementsCEGEditor = RequirementsCEGEditor;
//# sourceMappingURL=requirements-ceg-editor.component.js.map