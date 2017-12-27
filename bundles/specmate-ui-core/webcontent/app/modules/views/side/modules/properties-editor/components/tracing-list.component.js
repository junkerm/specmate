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
require("rxjs/add/operator/map");
var TracingList = /** @class */ (function () {
    /** constructor */
    function TracingList(dataService, differs) {
        this.dataService = dataService;
        this.differ = differs.find([]).create(null);
    }
    Object.defineProperty(TracingList.prototype, "traceProxies", {
        /** Sets a new object to be edited */
        set: function (traceProxies) {
            this._traceProxies = traceProxies;
        },
        enumerable: true,
        configurable: true
    });
    /** formats a specmate object. called by typeahead */
    TracingList.prototype.formatter = function (toFormat) {
        return toFormat.name;
    };
    /** Resolves the trace references */
    TracingList.prototype.ngDoCheck = function () {
        var _this = this;
        var change = this.differ.diff(this._traceProxies);
        if (change) {
            var promises = this._traceProxies.map(function (proxy) {
                return _this.dataService.readElement(proxy.url, true);
            });
            Promise.all(promises).then(function (result) { return _this.traces = result; });
        }
    };
    __decorate([
        core_1.Input(),
        __metadata("design:type", Array),
        __metadata("design:paramtypes", [Array])
    ], TracingList.prototype, "traceProxies", null);
    TracingList = __decorate([
        core_2.Component({
            moduleId: module.id.toString(),
            selector: 'tracing-list',
            templateUrl: 'tracing-list.component.html'
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService, core_1.IterableDiffers])
    ], TracingList);
    return TracingList;
}());
exports.TracingList = TracingList;
//# sourceMappingURL=tracing-list.component.js.map