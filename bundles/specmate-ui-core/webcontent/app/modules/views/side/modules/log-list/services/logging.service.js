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
var BehaviorSubject_1 = require("rxjs/BehaviorSubject");
var core_1 = require("@angular/core");
var log_element_1 = require("./log-element");
var config_1 = require("../../../../../../config/config");
var e_log_severity_1 = require("./e-log-severity");
var LoggingService = (function () {
    function LoggingService() {
        this.logHistory = [];
        this.logSubject = new BehaviorSubject_1.BehaviorSubject(new log_element_1.LogElement(config_1.Config.LOG_START_MESSAGE, e_log_severity_1.ELogSeverity.INFO, new Date()));
        this.logObservable = this.logSubject.asObservable();
    }
    Object.defineProperty(LoggingService.prototype, "logs", {
        get: function () {
            return this.logHistory;
        },
        enumerable: true,
        configurable: true
    });
    LoggingService.prototype.debug = function (message, url) {
        this.log(message, e_log_severity_1.ELogSeverity.DEBUG, url);
    };
    LoggingService.prototype.info = function (message, url) {
        this.log(message, e_log_severity_1.ELogSeverity.INFO, url);
    };
    LoggingService.prototype.warn = function (message, url) {
        this.log(message, e_log_severity_1.ELogSeverity.WARN, url);
    };
    LoggingService.prototype.error = function (message, url) {
        this.log(message, e_log_severity_1.ELogSeverity.ERROR, url);
    };
    LoggingService.prototype.log = function (message, severity, url) {
        var logElement = new log_element_1.LogElement(message, severity, new Date(), url);
        this.logHistory.unshift(logElement);
        if (this.logHistory.length > config_1.Config.LOG_LENGTH) {
            this.logHistory = this.logHistory.slice(0, config_1.Config.LOG_LENGTH);
        }
        this.logSubject.next(logElement);
    };
    LoggingService = __decorate([
        core_1.Injectable(),
        __metadata("design:paramtypes", [])
    ], LoggingService);
    return LoggingService;
}());
exports.LoggingService = LoggingService;
//# sourceMappingURL=logging.service.js.map