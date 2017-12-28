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
var of_1 = require("rxjs/observable/of");
require("rxjs/add/operator/map");
var specmate_data_service_1 = require("../../../../../data/modules/data-service/services/specmate-data.service");
var proxy_1 = require("../../../../../../model/support/proxy");
var id_1 = require("../../../../../../util/id");
var TracingLinks = /** @class */ (function () {
    /** constructor */
    function TracingLinks(dataService) {
        var _this = this;
        this.dataService = dataService;
        /** searches suggestions based on the typed text */
        this.search = function (text$) {
            return text$
                .debounceTime(300)
                .distinctUntilChanged()
                .switchMap(function (term) {
                return _this.dataService.search(term)
                    .catch(function () {
                    return of_1.of([]);
                });
            })
                .map(function (searchResult) { return searchResult.filter(function (result) {
                var existing = _this._model.tracesTo.find(function (t) { return t.url === result.url; });
                return existing == undefined;
            }); });
        };
    }
    Object.defineProperty(TracingLinks.prototype, "model", {
        /** getter */
        get: function () {
            return this._model;
        },
        /** Sets a new object to be edited */
        set: function (model) {
            this._model = model;
        },
        enumerable: true,
        configurable: true
    });
    /** formats a specmate object. called by typeahead */
    TracingLinks.prototype.formatter = function (toFormat) {
        return toFormat.name;
    };
    /** called when an item is selected in the typeahead */
    TracingLinks.prototype.selectItem = function (event, reqtypeahead) {
        event.preventDefault();
        var trace = new proxy_1.Proxy();
        trace.url = event.item.url;
        this.model.tracesTo.push(trace);
        this.dataService.updateElement(this.model, true, id_1.Id.uuid);
        reqtypeahead.value = '';
    };
    __decorate([
        core_1.Input(),
        __metadata("design:type", Object),
        __metadata("design:paramtypes", [Object])
    ], TracingLinks.prototype, "model", null);
    TracingLinks = __decorate([
        core_1.Component({
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