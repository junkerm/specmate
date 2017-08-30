"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var log_element_1 = require("./log-element");
var e_log_severity_1 = require("./e-log-severity");
var LoggingService = (function () {
    function LoggingService() {
        this.logHistory = [];
    }
    Object.defineProperty(LoggingService.prototype, "logs", {
        get: function () {
            return this.logHistory.reverse();
        },
        enumerable: true,
        configurable: true
    });
    LoggingService.prototype.debug = function (message, element) {
        this.log(message, e_log_severity_1.ELogSeverity.DEBUG, element);
    };
    LoggingService.prototype.info = function (message, element) {
        this.log(message, e_log_severity_1.ELogSeverity.INFO, element);
    };
    LoggingService.prototype.warn = function (message, element) {
        this.log(message, e_log_severity_1.ELogSeverity.WARN, element);
    };
    LoggingService.prototype.error = function (message, element) {
        this.log(message, e_log_severity_1.ELogSeverity.ERROR, element);
    };
    LoggingService.prototype.log = function (message, severity, element) {
        this.logHistory.push(new log_element_1.LogElement(message, severity, new Date(), element));
    };
    LoggingService = __decorate([
        core_1.Injectable()
    ], LoggingService);
    return LoggingService;
}());
exports.LoggingService = LoggingService;
//# sourceMappingURL=logging.service.js.map