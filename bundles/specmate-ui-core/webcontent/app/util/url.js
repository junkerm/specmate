"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var config_1 = require("../config/config");
var strings_1 = require("./strings");
var Url = /** @class */ (function () {
    function Url() {
    }
    Url.basePath = function (cls) {
        return config_1.Config.VIEW_URL_PREFIX + cls.className;
    };
    Url.parent = function (url) {
        var parts = url.split(Url.SEP);
        parts.splice(parts.length - 1, 1);
        var parentUrl = Url.build(parts);
        if (parentUrl.length === 0) {
            parentUrl = Url.SEP;
        }
        return parentUrl;
    };
    Url.isRoot = function (url) {
        return url === undefined || url === null || url === Url.SEP || url.length === 0;
    };
    Url.isParent = function (parentUrl, childUrl) {
        return strings_1.Strings.contains(childUrl, parentUrl) && childUrl !== parentUrl;
    };
    Url.build = function (parts, preventCache) {
        if (parts.filter(function (part) { return part === undefined; }).length > 0) {
            console.error('Supplied undefined part for URL building!');
            console.error(parts);
        }
        var joined = parts.join(Url.SEP);
        var url = Url.clean(joined);
        if (preventCache) {
            url += '?' + (new Date()).getTime();
        }
        return url;
    };
    Url.parts = function (url) {
        if (url) {
            return url.split(Url.SEP);
        }
        return null;
    };
    Url.clean = function (url) {
        while (url.indexOf(Url.SEP + Url.SEP) >= 0) {
            url = url.replace(Url.SEP + Url.SEP, Url.SEP);
        }
        if (url.startsWith(Url.SEP)) {
            url = url.slice(1, url.length);
        }
        return url;
    };
    Url.fromParams = function (params) {
        return params['url'];
    };
    Url.urlCreate = function (url) {
        return Url.build([config_1.Config.URL_BASE, Url.parent(url), config_1.Config.URL_CONTENTS]);
    };
    Url.urlDelete = function (url) {
        return Url.build([config_1.Config.URL_BASE, url, config_1.Config.URL_DELETE]);
    };
    Url.urlUpdate = function (url) {
        return Url.build([config_1.Config.URL_BASE, url, config_1.Config.URL_ELEMENT]);
    };
    Url.urlElement = function (url) {
        return Url.build([config_1.Config.URL_BASE, url, config_1.Config.URL_ELEMENT], true);
    };
    Url.urlContents = function (url) {
        return Url.build([config_1.Config.URL_BASE, url, config_1.Config.URL_CONTENTS], true);
    };
    Url.urlCustomService = function (url, serviceName) {
        return Url.build([config_1.Config.URL_BASE, url, serviceName], true);
    };
    Url.urlCheckConnectivity = function () {
        return Url.build([config_1.Config.URL_BASE, 'list'], true);
    };
    Url.SEP = '/';
    return Url;
}());
exports.Url = Url;
//# sourceMappingURL=url.js.map