"use strict";
var Url_1 = require('../util/Url');
var Objects_1 = require("../util/Objects");
var ServiceInterface = (function () {
    function ServiceInterface(http) {
        this.http = http;
    }
    ServiceInterface.prototype.createElement = function (element) {
        var payload = {};
        Objects_1.Objects.clone(element, payload);
        payload.url = undefined;
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
        return Promise.reject(error.message || error);
    };
    return ServiceInterface;
}());
exports.ServiceInterface = ServiceInterface;
//# sourceMappingURL=service-interface.js.map