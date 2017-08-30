"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var e_log_severity_1 = require("./e-log-severity");
var LogElement = (function () {
    function LogElement(message, severity, time, url) {
        this.message = message;
        this.severity = severity;
        this.time = time;
        this.url = url;
    }
    Object.defineProperty(LogElement.prototype, "isError", {
        get: function () {
            return this.isSeverity(e_log_severity_1.ELogSeverity.ERROR);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(LogElement.prototype, "isWarn", {
        get: function () {
            return this.isSeverity(e_log_severity_1.ELogSeverity.WARN);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(LogElement.prototype, "isInfo", {
        get: function () {
            return this.isSeverity(e_log_severity_1.ELogSeverity.INFO);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(LogElement.prototype, "isDebug", {
        get: function () {
            return this.isSeverity(e_log_severity_1.ELogSeverity.DEBUG);
        },
        enumerable: true,
        configurable: true
    });
    LogElement.prototype.isSeverity = function (severity) {
        return this.severity === severity;
    };
    return LogElement;
}());
exports.LogElement = LogElement;
//# sourceMappingURL=log-element.js.map