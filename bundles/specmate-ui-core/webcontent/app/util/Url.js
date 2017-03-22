"use strict";
var config_1 = require('../config/config');
var Url = (function () {
    function Url() {
    }
    Url.parent = function (url) {
        var parts = url.split(Url.SEP);
        parts.splice(parts.length - 1, 1);
        var parentUrl = Url.build(parts);
        if (parentUrl.length == 0) {
            parentUrl = Url.SEP;
        }
        return parentUrl;
    };
    Url.build = function (parts) {
        if (parts.filter(function (part) { return part === undefined; }).length > 0) {
            console.error('Supplied undefined part for URL building!');
            console.error(parts);
        }
        var joined = parts.join(Url.SEP);
        return Url.clean(joined);
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
        return Url.build([config_1.Config.URL_BASE, url, config_1.Config.URL_ELEMENT]);
    };
    Url.urlContents = function (url) {
        return Url.build([config_1.Config.URL_BASE, url, config_1.Config.URL_CONTENTS]);
    };
    Url.SEP = '/';
    return Url;
}());
exports.Url = Url;
//# sourceMappingURL=Url.js.map