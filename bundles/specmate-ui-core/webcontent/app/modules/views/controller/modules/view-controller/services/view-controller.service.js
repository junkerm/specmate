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
var config_1 = require("../../../../../../config/config");
var selected_element_service_1 = require("../../../../side/modules/selected-element/services/selected-element.service");
var additional_information_service_1 = require("../../../../side/modules/links-actions/services/additional-information.service");
var ViewControllerService = /** @class */ (function () {
    function ViewControllerService(selectedElementService, additionalInformationService) {
        this.selectedElementService = selectedElementService;
        this.additionalInformationService = additionalInformationService;
        this._isEditorMaximized = false;
        this._loggingOutputShown = config_1.Config.LOG_INITIALLY_SHOWN;
    }
    Object.defineProperty(ViewControllerService.prototype, "projectExplorerShown", {
        get: function () {
            return true;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ViewControllerService.prototype, "loggingOutputShown", {
        get: function () {
            return this._loggingOutputShown;
        },
        set: function (loggingOutputShown) {
            this._loggingOutputShown = loggingOutputShown;
        },
        enumerable: true,
        configurable: true
    });
    ViewControllerService.prototype.showLoggingOutput = function () {
        this.loggingOutputShown = true;
    };
    ViewControllerService.prototype.hideLoggingOutput = function () {
        this.loggingOutputShown = false;
    };
    ViewControllerService.prototype.maximizeEditor = function () {
        this._isEditorMaximized = true;
    };
    ViewControllerService.prototype.unmaximizeEditor = function () {
        this._isEditorMaximized = false;
    };
    Object.defineProperty(ViewControllerService.prototype, "isEditorMaximized", {
        get: function () {
            return this._isEditorMaximized;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ViewControllerService.prototype, "propertiesShown", {
        get: function () {
            return this.selectedElementService.hasSelection;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ViewControllerService.prototype, "linksActionsShown", {
        get: function () {
            return this.additionalInformationService.hasAdditionalInformation;
        },
        enumerable: true,
        configurable: true
    });
    ViewControllerService = __decorate([
        core_1.Injectable(),
        __metadata("design:paramtypes", [selected_element_service_1.SelectedElementService,
            additional_information_service_1.AdditionalInformationService])
    ], ViewControllerService);
    return ViewControllerService;
}());
exports.ViewControllerService = ViewControllerService;
//# sourceMappingURL=view-controller.service.js.map