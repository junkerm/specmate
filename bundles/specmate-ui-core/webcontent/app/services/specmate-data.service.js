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
var core_1 = require('@angular/core');
var http_1 = require('@angular/http');
require('rxjs/add/operator/toPromise');
var Url_1 = require('../util/Url');
var data_cache_1 = require("./data-cache");
var SpecmateDataService = (function () {
    function SpecmateDataService(http) {
        this.http = http;
        this.cache = new data_cache_1.DataCache();
        this._ready = true;
    }
    Object.defineProperty(SpecmateDataService.prototype, "ready", {
        get: function () {
            return this._ready;
        },
        enumerable: true,
        configurable: true
    });
    SpecmateDataService.prototype.commit = function () {
        var operations = this.cache.operationStore;
        for (var url in operations) {
            var operation = operations[url];
            var element = this.cache.getElement(url);
            switch (operation) {
                case data_cache_1.EOperation.CREATE:
                    this.serverCreateElement(element);
                    break;
                case data_cache_1.EOperation.UPDATE:
                    this.serverUpdateElement(element);
                    break;
                case data_cache_1.EOperation.DELETE:
                    this.serverDeleteElement(element);
                    break;
            }
        }
        return Promise.resolve();
    };
    SpecmateDataService.prototype.getContents = function (url) {
        return this.getContentsFresh(url);
    };
    SpecmateDataService.prototype.getElement = function (url) {
        return this.getElementFresh(url);
    };
    SpecmateDataService.prototype.createElement = function (element) {
        this.cache.storeElement(element);
        return Promise.resolve(this.cache.getContents(Url_1.Url.parent(element.url)));
    };
    SpecmateDataService.prototype.deleteElement = function (element) {
        this.cache.deleteElement(element);
        return Promise.resolve(this.cache.getContents(Url_1.Url.parent(element.url)));
    };
    SpecmateDataService.prototype.serverCreateElement = function (element) {
        var _this = this;
        var url = element.url;
        element.url = undefined;
        return this.http.post(Url_1.Url.urlCreate(url), element).toPromise()
            .then(function (response) {
            element.url = url;
            return _this.getContents(Url_1.Url.parent(url));
        });
    };
    SpecmateDataService.prototype.serverUpdateElement = function (element) {
        var _this = this;
        return this.http.put(Url_1.Url.urlUpdate(element.url), element).toPromise()
            .then(function (response) {
            return _this.getElement(element.url);
        });
    };
    SpecmateDataService.prototype.serverDeleteElement = function (element) {
        var _this = this;
        return this.http.delete(Url_1.Url.urlDelete(element.url)).toPromise()
            .then(function () { return _this.getContents(Url_1.Url.parent(element.url)); });
    };
    SpecmateDataService.prototype.getElementFresh = function (url) {
        var _this = this;
        var fullUrl = Url_1.Url.urlElement(url);
        return this.http.get(fullUrl).toPromise()
            .then(function (response) {
            var element = response.json();
            return _this.cache.storeElement(element);
        }).catch(this.handleError);
    };
    SpecmateDataService.prototype.getContentsFresh = function (url) {
        var _this = this;
        var fullUrl = Url_1.Url.urlContents(url);
        return this.http.get(fullUrl).toPromise()
            .then(function (response) {
            var contents = response.json();
            for (var i = 0; i < contents.length; i++) {
                _this.cache.storeElement(contents[i]);
            }
            return _this.cache.getContents(url);
        }).catch(this.handleError);
    };
    SpecmateDataService.prototype.handleError = function (error) {
        return Promise.reject(error.message || error);
    };
    SpecmateDataService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [http_1.Http])
    ], SpecmateDataService);
    return SpecmateDataService;
}());
exports.SpecmateDataService = SpecmateDataService;
//# sourceMappingURL=specmate-data.service.js.map