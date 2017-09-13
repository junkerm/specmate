"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var Url_1 = require("../../util/Url");
var Objects_1 = require("../../util/Objects");
var http_1 = require("@angular/http");
var Type_1 = require("../../util/Type");
var CEGConnection_1 = require("../../model/CEGConnection");
require("rxjs/add/operator/toPromise");
var ServiceInterface = (function () {
    function ServiceInterface(http) {
        this.http = http;
    }
    ServiceInterface.prototype.checkConnection = function () {
        return this.http.get(Url_1.Url.urlCheckConnectivity()).toPromise().then(function () { return true; }).catch(function () { return false; });
    };
    ServiceInterface.prototype.createElement = function (element) {
        var payload = this.prepareElementPayload(element);
        return this.http.post(Url_1.Url.urlCreate(element.url), payload).toPromise().catch(this.handleError).then(function (response) { });
    };
    ServiceInterface.prototype.readElement = function (url) {
        return this.http.get(Url_1.Url.urlElement(url)).toPromise().catch(this.handleError).then(function (response) { return response.json(); });
    };
    ServiceInterface.prototype.readContents = function (url) {
        return this.http.get(Url_1.Url.urlContents(url)).toPromise().catch(this.handleError).then(function (response) { return response.json(); });
    };
    ServiceInterface.prototype.updateElement = function (element) {
        var payload = this.prepareElementPayload(element);
        return this.http.put(Url_1.Url.urlUpdate(element.url), payload).toPromise().catch(this.handleError).then(function (response) { });
    };
    ServiceInterface.prototype.deleteElement = function (url) {
        return this.http.delete(Url_1.Url.urlDelete(url)).toPromise().catch(this.handleError).then(function (response) { });
    };
    ServiceInterface.prototype.performOperation = function (url, serviceSuffix, payload) {
        return this.http.post(Url_1.Url.urlCustomService(url, serviceSuffix), payload)
            .toPromise().catch(this.handleError)
            .then(function (response) {
            return response.json();
        });
    };
    ServiceInterface.prototype.performQuery = function (url, serviceSuffix, parameters) {
        var urlParams = new http_1.URLSearchParams();
        for (var key in parameters) {
            urlParams.append(key, parameters[key]);
        }
        return this.http.get(Url_1.Url.urlCustomService(url, serviceSuffix), { search: urlParams }).toPromise().catch(this.handleError).then(function (response) { return response.json(); });
    };
    ServiceInterface.prototype.handleError = function (error) {
        console.error('Error in Service Interface! (details below)');
        return Promise.reject(error.message || error);
    };
    ServiceInterface.prototype.prepareElementPayload = function (element) {
        var payload = Objects_1.Objects.clone(element);
        payload.url = undefined;
        delete payload.url;
        if (Type_1.Type.is(element, CEGConnection_1.CEGConnection)) {
            payload.source.___proxy = 'true';
            payload.target.___proxy = 'true';
        }
        if (!element.id) {
            payload['___proxy'] = 'true';
        }
        return payload;
    };
    return ServiceInterface;
}());
exports.ServiceInterface = ServiceInterface;
//# sourceMappingURL=service-interface.js.map