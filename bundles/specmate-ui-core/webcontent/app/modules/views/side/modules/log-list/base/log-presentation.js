"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var e_log_severity_1 = require("../services/e-log-severity");
var config_1 = require("../../../../../../config/config");
var LogPresentation = (function () {
    function LogPresentation() {
    }
    LogPresentation.iconForStr = function (severity) {
        return LogPresentation.icon(LogPresentation.getSeverityForString(severity));
    };
    LogPresentation.colorForStr = function (severity) {
        return LogPresentation.color(LogPresentation.getSeverityForString(severity));
    };
    LogPresentation.color = function (severity) {
        if (severity == e_log_severity_1.ELogSeverity.ERROR) {
            return 'danger';
        }
        if (severity == e_log_severity_1.ELogSeverity.WARN) {
            return 'warning';
        }
        if (severity == e_log_severity_1.ELogSeverity.INFO) {
            return 'primary';
        }
        if (severity == e_log_severity_1.ELogSeverity.DEBUG) {
            return 'success';
        }
        return config_1.Config.LOG_DEFAULT_COLOR;
    };
    LogPresentation.icon = function (severity) {
        if (severity == e_log_severity_1.ELogSeverity.ERROR) {
            return 'warning';
        }
        if (severity == e_log_severity_1.ELogSeverity.WARN) {
            return 'warning';
        }
        if (severity == e_log_severity_1.ELogSeverity.INFO) {
            return 'info-circle';
        }
        if (severity == e_log_severity_1.ELogSeverity.DEBUG) {
            return 'bug';
        }
        return config_1.Config.LOG_DEFAULT_ICON;
    };
    LogPresentation.getSeverityForString = function (severity) {
        if (severity === LogPresentation.ERROR) {
            return e_log_severity_1.ELogSeverity.ERROR;
        }
        if (severity === LogPresentation.WARN) {
            return e_log_severity_1.ELogSeverity.WARN;
        }
        if (severity === LogPresentation.INFO) {
            return e_log_severity_1.ELogSeverity.INFO;
        }
        if (severity === LogPresentation.DEBUG) {
            return e_log_severity_1.ELogSeverity.DEBUG;
        }
        return undefined;
    };
    LogPresentation.getStringForSeverity = function (severity) {
        if (severity === e_log_severity_1.ELogSeverity.ERROR) {
            return LogPresentation.ERROR;
        }
        if (severity === e_log_severity_1.ELogSeverity.WARN) {
            return LogPresentation.WARN;
        }
        if (severity === e_log_severity_1.ELogSeverity.INFO) {
            return LogPresentation.INFO;
        }
        if (severity === e_log_severity_1.ELogSeverity.DEBUG) {
            return LogPresentation.DEBUG;
        }
        return undefined;
    };
    LogPresentation.ERROR = 'Error';
    LogPresentation.WARN = 'Warning';
    LogPresentation.INFO = 'Info';
    LogPresentation.DEBUG = 'Debug';
    return LogPresentation;
}());
exports.LogPresentation = LogPresentation;
//# sourceMappingURL=log-presentation.js.map