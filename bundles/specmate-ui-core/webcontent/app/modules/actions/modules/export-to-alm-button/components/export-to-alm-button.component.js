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
var TestProcedure_1 = require("../../../../../model/TestProcedure");
var specmate_data_service_1 = require("../../../../data/modules/data-service/services/specmate-data.service");
var confirmation_modal_service_1 = require("../../../../notification/modules/modals/services/confirmation-modal.service");
var validation_service_1 = require("../../../../forms/modules/validation/services/validation.service");
var ExportToALMButton = /** @class */ (function () {
    function ExportToALMButton(dataService, modal, validation) {
        this.dataService = dataService;
        this.modal = modal;
        this.validation = validation;
    }
    ExportToALMButton.prototype.ngOnInit = function () {
        var _this = this;
        this.dataService.readContents(this.testProcedure.url).then(function (contents) { return _this.contents = contents; });
    };
    /** Pushes or updates a test procedure to HP ALM */
    ExportToALMButton.prototype.pushTestProcedure = function () {
        var _this = this;
        if (!this.isValid) {
            return;
        }
        this.modal.confirmSave().then(function () {
            return _this.dataService.commit('Save before ALM Export').then(function () {
                return _this.dataService.performOperations(_this.testProcedure.url, 'syncalm')
                    .then(function (result) {
                    if (result) {
                        _this.modal.open('Procedure exported successfully', false);
                    }
                });
            });
        });
    };
    Object.defineProperty(ExportToALMButton.prototype, "isValid", {
        get: function () {
            return this.validation.isValid(this.testProcedure) && this.validation.allValid(this.contents);
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        core_1.Input(),
        __metadata("design:type", TestProcedure_1.TestProcedure)
    ], ExportToALMButton.prototype, "testProcedure", void 0);
    ExportToALMButton = __decorate([
        core_1.Component({
            moduleId: module.id.toString(),
            selector: 'export-to-alm-button',
            templateUrl: 'export-to-alm-button.component.html',
            styleUrls: ['export-to-alm-button.component.css']
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService, confirmation_modal_service_1.ConfirmationModal, validation_service_1.ValidationService])
    ], ExportToALMButton);
    return ExportToALMButton;
}());
exports.ExportToALMButton = ExportToALMButton;
//# sourceMappingURL=export-to-alm-button.component.js.map