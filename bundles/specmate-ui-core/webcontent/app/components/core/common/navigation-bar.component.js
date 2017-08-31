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
Object.defineProperty(exports, "__esModule", { value: true });
var config_1 = require("../../../config/config");
var core_1 = require("@angular/core");
var specmate_data_service_1 = require("../../../services/data/specmate-data.service");
var NavigationBar = (function () {
    function NavigationBar(dataService) {
        this.dataService = dataService;
    }
    Object.defineProperty(NavigationBar.prototype, "version", {
        get: function () {
            return config_1.Config.VERSION;
        },
        enumerable: true,
        configurable: true
    });
    NavigationBar = __decorate([
        core_1.Component({
            selector: 'navigation-bar',
            moduleId: module.id,
            templateUrl: 'navigation-bar.component.html'
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService])
    ], NavigationBar);
    return NavigationBar;
}());
exports.NavigationBar = NavigationBar;
//# sourceMappingURL=navigation-bar.component.js.map