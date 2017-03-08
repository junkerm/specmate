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
var proxy_1 = require('../../../model/support/proxy');
var specmate_data_service_1 = require('../../../services/specmate-data.service');
var CEGGraphicalConnection = (function () {
    function CEGGraphicalConnection(dataService) {
        this.dataService = dataService;
    }
    Object.defineProperty(CEGGraphicalConnection.prototype, "originX", {
        get: function () {
            this.dataService.getDetails(this.sourceProxy.url);
            return 0;
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        core_1.Input(), 
        __metadata('design:type', proxy_1.Proxy)
    ], CEGGraphicalConnection.prototype, "sourceProxy", void 0);
    __decorate([
        core_1.Input(), 
        __metadata('design:type', proxy_1.Proxy)
    ], CEGGraphicalConnection.prototype, "targetProxy", void 0);
    CEGGraphicalConnection = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'ceg-graphical-connection',
            templateUrl: 'ceg-graphical-connection.component.svg'
        }), 
        __metadata('design:paramtypes', [specmate_data_service_1.SpecmateDataService])
    ], CEGGraphicalConnection);
    return CEGGraphicalConnection;
}());
exports.CEGGraphicalConnection = CEGGraphicalConnection;
//# sourceMappingURL=ceg-graphical-connection.component.js.map