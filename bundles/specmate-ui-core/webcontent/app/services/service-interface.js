"use strict";
var Url_1 = require('../util/Url');
var Objects_1 = require('../util/Objects');
var Type_1 = require("../util/Type");
var CEGConnection_1 = require("../model/CEGConnection");
var ServiceInterface = (function () {
    function ServiceInterface(http) {
        this.http = http;
    }
    ServiceInterface.prototype.createElement = function (element) {
        var payload = {};
        Objects_1.Objects.clone(element, payload);
        payload.url = undefined;
        if (Type_1.Type.is(element, CEGConnection_1.CEGConnection)) {
            payload.source.___proxy = true;
            payload.target.___proxy = true;
        }
        if (!element.id) {
            element['___proxy'] = true;
        }
        return this.http.post(Url_1.Url.urlCreate(element.url), payload).toPromise().catch(this.handleError).then(function (response) { });
    };
    ServiceInterface.prototype.readElement = function (url) {
        return this.http.get(Url_1.Url.urlElement(url)).toPromise().catch(this.handleError).then(function (response) { return response.json(); });
    };
    ServiceInterface.prototype.readContents = function (url) {
        return this.http.get(Url_1.Url.urlContents(url)).toPromise().catch(this.handleError).then(function (response) { return response.json(); });
    };
    ServiceInterface.prototype.updateElement = function (element) {
        return this.http.put(Url_1.Url.urlUpdate(element.url), element).toPromise().catch(this.handleError).then(function (response) { });
    };
    ServiceInterface.prototype.deleteElement = function (url) {
        return this.http.delete(Url_1.Url.urlDelete(url)).toPromise().catch(this.handleError).catch(this.handleError).then(function (response) { });
    };
    ServiceInterface.prototype.handleError = function (error) {
        console.log('Error in Service Interface! (details below)');
        return Promise.reject(error.message || error);
    };
    return ServiceInterface;
}());
exports.ServiceInterface = ServiceInterface;
//# sourceMappingURL=service-interface.js.map