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
var specmate_data_service_1 = require("../../../../../data/modules/data-service/services/specmate-data.service");
var proxy_1 = require("../../../../../../model/support/proxy");
/** This component displays a trace link. It takes a trace link proxy as input. */
var TracingLink = /** @class */ (function () {
    /** constructor */
    function TracingLink(dataService) {
        this.dataService = dataService;
    }
    Object.defineProperty(TracingLink.prototype, "traceProxy", {
        /** Sets a new trace link proxy to be displayed */
        set: function (traceProxy) {
            this._traceProxy = traceProxy;
        },
        enumerable: true,
        configurable: true
    });
    TracingLink.prototype.ngOnInit = function () {
        var _this = this;
        this.dataService.readElement(this._traceProxy.url, true).then(function (trace) {
            return _this.trace = trace;
        });
    };
    __decorate([
        core_1.Input(),
        __metadata("design:type", proxy_1.Proxy),
        __metadata("design:paramtypes", [proxy_1.Proxy])
    ], TracingLink.prototype, "traceProxy", null);
    TracingLink = __decorate([
        core_1.Component({
            moduleId: module.id.toString(),
            selector: 'tracing-link',
            templateUrl: 'tracing-link.component.html'
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService])
    ], TracingLink);
    return TracingLink;
}());
exports.TracingLink = TracingLink;
//# sourceMappingURL=tracing-link.component.js.map