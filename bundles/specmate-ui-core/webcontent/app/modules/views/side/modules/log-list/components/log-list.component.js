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
var log_presentation_1 = require("../base/log-presentation");
var forms_1 = require("@angular/forms");
var logging_service_1 = require("../services/logging.service");
var view_controller_service_1 = require("../../../../controller/modules/view-controller/services/view-controller.service");
var LogList = /** @class */ (function () {
    function LogList(logger, viewController, formBuilder) {
        this.logger = logger;
        this.viewController = viewController;
        this.formBuilder = formBuilder;
        this.errorName = log_presentation_1.LogPresentation.ERROR;
        this.warnName = log_presentation_1.LogPresentation.WARN;
        this.infoName = log_presentation_1.LogPresentation.INFO;
        this.debugName = log_presentation_1.LogPresentation.DEBUG;
        this.severityNames = [this.errorName, this.warnName, this.infoName, this.debugName];
        var formGroupObj = {};
        this.severityNames.forEach(function (severityName) { return formGroupObj[severityName] = true; });
        this.checkboxGroupForm = this.formBuilder.group(formGroupObj);
    }
    Object.defineProperty(LogList.prototype, "log", {
        get: function () {
            var _this = this;
            return this.logger.logs.filter(function (logElement) { return _this.isVisible(logElement); });
        },
        enumerable: true,
        configurable: true
    });
    LogList.prototype.closeLog = function () {
        this.viewController.hideLoggingOutput();
    };
    LogList.prototype.isChecked = function (severityName) {
        return this.checkboxGroupForm.value[severityName];
    };
    LogList.prototype.isSeverityActive = function (severity) {
        return this.isChecked(log_presentation_1.LogPresentation.getStringForSeverity(severity));
    };
    LogList.prototype.isVisible = function (logElement) {
        return this.isSeverityActive(logElement.severity);
    };
    LogList.prototype.icon = function (severityName) {
        return log_presentation_1.LogPresentation.iconForStr(severityName);
    };
    LogList.prototype.color = function (severityName) {
        return log_presentation_1.LogPresentation.colorForStr(severityName);
    };
    LogList = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'log-list',
            templateUrl: 'log-list.component.html'
        }),
        __metadata("design:paramtypes", [logging_service_1.LoggingService, view_controller_service_1.ViewControllerService, forms_1.FormBuilder])
    ], LogList);
    return LogList;
}());
exports.LogList = LogList;
//# sourceMappingURL=log-list.component.js.map