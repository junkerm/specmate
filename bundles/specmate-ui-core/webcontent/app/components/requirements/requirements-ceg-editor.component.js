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
var router_1 = require('@angular/router');
var specmate_data_service_1 = require('../../services/specmate-data.service');
var CEGModel_1 = require('../../model/CEGModel');
var Requirement_1 = require('../../model/Requirement');
var Type_1 = require('../../util/Type');
var Url_1 = require('../../util/Url');
var RequirementsCEGEditor = (function () {
    function RequirementsCEGEditor(dataService, route) {
        this.dataService = dataService;
        this.route = route;
    }
    RequirementsCEGEditor.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params
            .switchMap(function (params) {
            var url = params['url'];
            return _this.dataService.getDetails(url);
        })
            .subscribe(function (object) {
            if (Type_1.Type.is(object, CEGModel_1.CEGModel)) {
                _this.model = object;
                _this.backUrl = ['..', '..', Url_1.Url.parent(_this.model.url)];
            }
            else if (Type_1.Type.is(object, Requirement_1.Requirement)) {
                _this.model = new CEGModel_1.CEGModel();
                _this.backUrl = ['..', object.url];
            }
        });
    };
    RequirementsCEGEditor = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'ceg-editor',
            templateUrl: 'requirements-ceg-editor.component.html'
        }), 
        __metadata('design:paramtypes', [specmate_data_service_1.SpecmateDataService, router_1.ActivatedRoute])
    ], RequirementsCEGEditor);
    return RequirementsCEGEditor;
}());
exports.RequirementsCEGEditor = RequirementsCEGEditor;
//# sourceMappingURL=requirements-ceg-editor.component.js.map