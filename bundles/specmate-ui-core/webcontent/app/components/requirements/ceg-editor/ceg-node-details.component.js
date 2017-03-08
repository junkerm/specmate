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
var specmate_data_service_1 = require('../../../services/specmate-data.service');
var CEGNodeDetails = (function () {
    function CEGNodeDetails(dataService, route) {
        this.dataService = dataService;
        this.route = route;
    }
    CEGNodeDetails.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params
            .switchMap(function (params) { return _this.dataService.getDetails(params['url']); })
            .subscribe(function (node) { return _this.node = node; });
    };
    CEGNodeDetails = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'ceg-node-details',
            templateUrl: 'ceg-node-details.component.html'
        }), 
        __metadata('design:paramtypes', [specmate_data_service_1.SpecmateDataService, router_1.ActivatedRoute])
    ], CEGNodeDetails);
    return CEGNodeDetails;
}());
exports.CEGNodeDetails = CEGNodeDetails;
//# sourceMappingURL=ceg-node-details.component.js.map