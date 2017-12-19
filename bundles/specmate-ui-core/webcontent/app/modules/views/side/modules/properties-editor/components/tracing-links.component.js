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
var specmate_data_service_1 = require("../../../../../data/modules/data-service/services/specmate-data.service");
var core_1 = require("@angular/core");
var core_2 = require("@angular/core");
var TracingLinks = /** @class */ (function () {
    function TracingLinks(dataService) {
        this.dataService = dataService;
    }
    TracingLinks.prototype.ngOnInit = function () {
        var _this = this;
        var tracePromises = this.model.tracesTo.map(function (proxy) { return _this.dataService.readElement(proxy.url); });
        Promise.all(tracePromises).then(function (traces) { return _this.traces = traces; });
    };
    __decorate([
        core_1.Input(),
        __metadata("design:type", Object)
    ], TracingLinks.prototype, "model", void 0);
    TracingLinks = __decorate([
        core_2.Component({
            moduleId: module.id.toString(),
            selector: 'tracing-links',
            templateUrl: 'tracing-links.component.html'
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService])
    ], TracingLinks);
    return TracingLinks;
}());
exports.TracingLinks = TracingLinks;
//# sourceMappingURL=tracing-links.component.js.map