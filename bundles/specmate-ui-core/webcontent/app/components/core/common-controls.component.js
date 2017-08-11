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
var config_1 = require("../../config/config");
var confirmation_modal_service_1 = require("./forms/confirmation-modal.service");
var specmate_data_service_1 = require("../../services/specmate-data.service");
var core_1 = require("@angular/core");
var editor_common_control_service_1 = require("../../services/editor-common-control.service");
var Rx_1 = require("rxjs/Rx");
var navigator_service_1 = require("../../services/navigator.service");
var CommonControls = (function () {
    function CommonControls(dataService, commonControlService, modal, navigator) {
        var _this = this;
        this.dataService = dataService;
        this.commonControlService = commonControlService;
        this.modal = modal;
        this.navigator = navigator;
        this.connected = true;
        var timer = Rx_1.Observable.timer(0, config_1.Config.CONNECTIVITY_CHECK_DELAY);
        timer.subscribe(function () {
            _this.dataService.checkConnection().then(function (val) { return _this.connected = val; });
        });
    }
    CommonControls.prototype.save = function () {
        if (this.isSaveEnabled) {
            this.dataService.commit("Save");
        }
    };
    CommonControls.prototype.close = function () {
        this.back();
    };
    CommonControls.prototype.undo = function () {
        if (this.isUndoEnabled) {
            this.dataService.undo();
        }
    };
    CommonControls.prototype.forward = function () {
        if (this.isForwardEnabled) {
            this.navigator.forward();
        }
    };
    CommonControls.prototype.back = function () {
        if (this.isBackEnabled) {
            this.navigator.back();
        }
    };
    Object.defineProperty(CommonControls.prototype, "isSaveEnabled", {
        get: function () {
            return this.isEnabled && this.dataService.hasCommits && this.commonControlService.isCurrentEditorValid;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CommonControls.prototype, "isUndoEnabled", {
        get: function () {
            return this.isEnabled && this.dataService.hasCommits;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CommonControls.prototype, "isBackEnabled", {
        get: function () {
            return this.navigator.hasPrevious;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CommonControls.prototype, "isForwardEnabled", {
        get: function () {
            return this.navigator.hasNext;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CommonControls.prototype, "isEnabled", {
        get: function () {
            return true;
        },
        enumerable: true,
        configurable: true
    });
    CommonControls = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'common-controls',
            templateUrl: 'common-controls.component.html',
            styleUrls: ['common-controls.component.css']
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService, editor_common_control_service_1.EditorCommonControlService, confirmation_modal_service_1.ConfirmationModal, navigator_service_1.NavigatorService])
    ], CommonControls);
    return CommonControls;
}());
exports.CommonControls = CommonControls;
//# sourceMappingURL=common-controls.component.js.map