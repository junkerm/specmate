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
var config_1 = require("../config/config");
var confirmation_modal_service_1 = require("../components/core/forms/confirmation-modal.service");
var specmate_data_service_1 = require("../services/data/specmate-data.service");
var core_1 = require("@angular/core");
var UnsavedChangesGuard = (function () {
    function UnsavedChangesGuard(dataService, modal) {
        this.dataService = dataService;
        this.modal = modal;
    }
    UnsavedChangesGuard.prototype.canDeactivate = function (component) {
        if (this.dataService.hasCommits) {
            return this.modal.open(config_1.Config.NAVIGATION_CONFIRMATION).then(function () { return true; }).catch(function () { return false; });
        }
        else {
            return true;
        }
    };
    UnsavedChangesGuard = __decorate([
        core_1.Injectable(),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService, confirmation_modal_service_1.ConfirmationModal])
    ], UnsavedChangesGuard);
    return UnsavedChangesGuard;
}());
exports.UnsavedChangesGuard = UnsavedChangesGuard;
//# sourceMappingURL=unsaved-changes-guard.js.map