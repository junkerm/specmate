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
var log_element_1 = require("../services/log-element");
var log_presentation_1 = require("../base/log-presentation");
var LogEntry = /** @class */ (function () {
    function LogEntry() {
    }
    Object.defineProperty(LogEntry.prototype, "icon", {
        get: function () {
            return log_presentation_1.LogPresentation.icon(this.logElement.severity);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(LogEntry.prototype, "color", {
        get: function () {
            return log_presentation_1.LogPresentation.color(this.logElement.severity);
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        core_1.Input(),
        __metadata("design:type", log_element_1.LogElement)
    ], LogEntry.prototype, "logElement", void 0);
    LogEntry = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: '[log-entry]',
            templateUrl: 'log-entry.component.html'
        }),
        __metadata("design:paramtypes", [])
    ], LogEntry);
    return LogEntry;
}());
exports.LogEntry = LogEntry;
//# sourceMappingURL=log-entry.component.js.map