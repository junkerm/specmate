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
var Arrays_1 = require('../util/Arrays');
var config_1 = require('../config/config');
var SpecmateDataService = (function () {
    function SpecmateDataService(http) {
        this.http = http;
        this.detailsCache = [];
        this.listCache = [];
        this._ready = true;
    }
    Object.defineProperty(SpecmateDataService.prototype, "ready", {
        get: function () {
            return this._ready;
        },
        enumerable: true,
        configurable: true
    });
    SpecmateDataService.prototype.newUrl = function (url) {
        return Url_1.Url.build([config_1.Config.BASE_URL, Url_1.Url.parent(url), SpecmateDataService.CONTENTS]);
    };
    SpecmateDataService.prototype.deleteUrl = function (url) {
        return Url_1.Url.build([config_1.Config.BASE_URL, url, SpecmateDataService.DELETE]);
    };
    SpecmateDataService.prototype.updateUrl = function (url) {
        return Url_1.Url.build([config_1.Config.BASE_URL, url, SpecmateDataService.ELEMENT]);
    };
    SpecmateDataService.prototype.elementUrl = function (url) {
        return Url_1.Url.build([config_1.Config.BASE_URL, url, SpecmateDataService.ELEMENT]);
    };
    SpecmateDataService.prototype.contentsUrl = function (url) {
        return Url_1.Url.build([config_1.Config.BASE_URL, url, SpecmateDataService.CONTENTS]);
    };
    SpecmateDataService.prototype.reGetContents = function (url) {
        var _this = this;
        this._ready = false;
        var fullUrl = this.contentsUrl(url);
        return this.http.get(fullUrl).toPromise().then(function (response) {
            var list = response.json();
            _this.listCache[url] = list;
            for (var i = 0; i < list.length; i++) {
                var details = list[i];
                _this.detailsCache[details.url] = details;
            }
            setTimeout(function () { _this._ready = true; }, 1000);
            return list;
        }).catch(this.handleError);
    };
    SpecmateDataService.prototype.getContents = function (url) {
        if (this.listCache[url]) {
            return Promise.resolve(this.listCache[url]);
        }
        return this.reGetContents(url);
    };
    SpecmateDataService.prototype.addContents = function (element, contents) {
        if (this.listCache[element.url]) {
            console.error('Element with URL ' + element.url + ' already existed.');
        }
        this.listCache[element.url] = contents;
    };
    SpecmateDataService.prototype.reGetElement = function (url) {
        var _this = this;
        this._ready = false;
        var fullUrl = this.elementUrl(url);
        return this.http.get(fullUrl).toPromise().then(function (response) {
            var details = response.json();
            _this.detailsCache[url] = details;
            setTimeout(function () { _this._ready = true; }, 1000);
            return details;
        }).catch(this.handleError);
    };
    SpecmateDataService.prototype.getElement = function (url) {
        if (this.detailsCache[url]) {
            return Promise.resolve(this.detailsCache[url]);
        }
        return this.reGetElement(url);
    };
    SpecmateDataService.prototype.addElement = function (element) {
        var _this = this;
        var url = element.url;
        element.url = undefined;
        console.log('ADD ELEMENT ' + url);
        console.log(element);
        return this.http.post(this.newUrl(url), element).toPromise()
            .then(function (response) { return _this.reGetContents(Url_1.Url.parent(url)); })
            .then(function () { return _this.reGetElement(url); });
    };
    SpecmateDataService.prototype.removeElement = function (element) {
        var _this = this;
        return this.http.delete(this.deleteUrl(element.url)).toPromise()
            .then(function () { return _this.reGetContents(Url_1.Url.parent(element.url)); });
    };
    SpecmateDataService.prototype.emptyCache = function () {
        this.detailsCache = [];
        this.listCache = [];
    };
    SpecmateDataService.prototype.removeFromCache = function (element) {
        this.detailsCache[element.url] = undefined;
        this.listCache[element.url] = undefined;
        Arrays_1.Arrays.remove(this.listCache[Url_1.Url.parent(element.url)], element);
    };
    SpecmateDataService.prototype.handleError = function (error) {
        return Promise.reject(error.message || error);
    };
    SpecmateDataService.CONTENTS = '/list';
    SpecmateDataService.ELEMENT = '/details';
    SpecmateDataService.DELETE = '/delete';
    SpecmateDataService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [http_1.Http])
    ], SpecmateDataService);
    return SpecmateDataService;
}());
exports.SpecmateDataService = SpecmateDataService;
//# sourceMappingURL=specmate-data.service.js.map