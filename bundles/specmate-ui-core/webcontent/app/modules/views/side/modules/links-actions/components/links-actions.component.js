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
var core_1 = require("@angular/core");
var additional_information_service_1 = require("../services/additional-information.service");
var LinksActions = (function () {
    function LinksActions(additionalInformationService) {
        this.additionalInformationService = additionalInformationService;
    }
    Object.defineProperty(LinksActions.prototype, "element", {
        get: function () {
            return this.additionalInformationService.element;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(LinksActions.prototype, "model", {
        get: function () {
            return this.additionalInformationService.model;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(LinksActions.prototype, "requirement", {
        get: function () {
            return this.additionalInformationService.requirement;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(LinksActions.prototype, "testSpecification", {
        get: function () {
            return this.additionalInformationService.testSpecification;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(LinksActions.prototype, "testSpecifications", {
        get: function () {
            return this.additionalInformationService.testSpecifications;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(LinksActions.prototype, "canHaveTestSpecifications", {
        get: function () {
            return this.additionalInformationService.canHaveTestSpecifications;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(LinksActions.prototype, "canGenerateTestSpecifications", {
        get: function () {
            return this.additionalInformationService.canGenerateTestSpecifications;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(LinksActions.prototype, "canAddTestSpecifications", {
        get: function () {
            return this.additionalInformationService.canAddTestSpecifications;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(LinksActions.prototype, "canExportToALM", {
        get: function () {
            return this.additionalInformationService.canExportToALM;
        },
        enumerable: true,
        configurable: true
    });
    LinksActions = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'links-actions',
            templateUrl: 'links-actions.component.html',
            styleUrls: ['links-actions.component.css']
        }),
        __metadata("design:paramtypes", [additional_information_service_1.AdditionalInformationService])
    ], LinksActions);
    return LinksActions;
}());
exports.LinksActions = LinksActions;
//# sourceMappingURL=links-actions.component.js.map