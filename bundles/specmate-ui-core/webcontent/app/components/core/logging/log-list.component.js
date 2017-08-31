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
var logging_service_1 = require("../../../services/logging/logging.service");
var core_1 = require("@angular/core");
var LogList = (function () {
    function LogList(logger) {
        this.logger = logger;
    }
    Object.defineProperty(LogList.prototype, "log", {
        get: function () {
            return this.logger.logs;
        },
        enumerable: true,
        configurable: true
    });
    LogList = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'log-list',
            templateUrl: 'log-list.component.html'
        }),
        __metadata("design:paramtypes", [logging_service_1.LoggingService])
    ], LogList);
    return LogList;
}());
exports.LogList = LogList;
//# sourceMappingURL=log-list.component.js.map