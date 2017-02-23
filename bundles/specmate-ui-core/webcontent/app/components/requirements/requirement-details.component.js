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
require('rxjs/add/operator/switchMap');
var RequirementsDetails = (function () {
    function RequirementsDetails(dataService, route) {
        this.dataService = dataService;
        this.route = route;
    }
    RequirementsDetails.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params
            .switchMap(function (params) { return _this.dataService.getContent(params['url']); })
            .subscribe(function (requirement) { return _this.requirement = requirement; });
    };
    RequirementsDetails = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'requirements-details',
            templateUrl: 'requirement-details.component.html'
        }), 
        __metadata('design:paramtypes', [specmate_data_service_1.SpecmateDataService, router_1.ActivatedRoute])
    ], RequirementsDetails);
    return RequirementsDetails;
}());
exports.RequirementsDetails = RequirementsDetails;
//# sourceMappingURL=requirement-details.component.js.map