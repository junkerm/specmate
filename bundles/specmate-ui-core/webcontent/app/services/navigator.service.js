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
var router_1 = require("@angular/router");
var core_1 = require("@angular/core");
var confirmation_modal_service_1 = require("../components/core/forms/confirmation-modal.service");
var specmate_data_service_1 = require("./specmate-data.service");
var config_1 = require("../config/config");
var NavigatorService = (function () {
    function NavigatorService(dataService, modal, router) {
        this.dataService = dataService;
        this.modal = modal;
        this.router = router;
        this.history = [];
    }
    NavigatorService.prototype.navigate = function (element) {
        var _this = this;
        if (this.dataService.hasCommits) {
            this.modal.open(config_1.Config.NAVIGATION_CONFIRMATION).then(function () {
                _this.performNavigation(element);
            }).catch();
        }
        else {
            this.performNavigation(element);
        }
    };
    NavigatorService.prototype.performNavigation = function (element) {
        if (this.history[this.history.length - 1] !== element) {
            this.history.push(element);
        }
        this.dataService.clearCommits();
        this.router.navigate([element.className, element.url]);
    };
    Object.defineProperty(NavigatorService.prototype, "hasHistory", {
        get: function () {
            return this.history && this.history.length > 1;
        },
        enumerable: true,
        configurable: true
    });
    NavigatorService.prototype.back = function () {
        var _this = this;
        if (this.dataService.hasCommits) {
            this.modal.open(config_1.Config.NAVIGATION_CONFIRMATION).then(function () {
                _this.performBack();
            }).catch();
        }
        else {
            this.performBack();
        }
    };
    NavigatorService.prototype.performBack = function () {
        if (!this.hasHistory) {
            return;
        }
        this.history.pop();
        var lastElement = this.history.pop();
        this.performNavigation(lastElement);
    };
    NavigatorService = __decorate([
        core_1.Injectable(),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService, confirmation_modal_service_1.ConfirmationModal, router_1.Router])
    ], NavigatorService);
    return NavigatorService;
}());
exports.NavigatorService = NavigatorService;
//# sourceMappingURL=navigator.service.js.map