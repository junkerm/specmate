"use strict";
var Url = (function () {
    function Url() {
    }
    Url.parent = function (url) {
        var parts = url.split(Url.SEP);
        parts.splice(parts.length - 1, 1);
        return Url.build(parts);
    };
    Url.build = function (parts) {
        return parts.join(Url.SEP);
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
        return url;
    };
    Url.SEP = '/';
    return Url;
}());
exports.Url = Url;
//# sourceMappingURL=Url.js.map