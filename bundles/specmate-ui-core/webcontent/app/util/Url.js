"use strict";
var Url = (function () {
    function Url() {
    }
    // UNTESTED!!!
    Url.parent = function (url) {
        var parts = url.split(Url.SEP);
        parts = parts.splice(parts.length - 1);
        var parent = Url.build(parts);
        return parent.substr(0, parent.length - 1);
    };
    Url.build = function (parts) {
        return parts.join(Url.SEP);
    };
    Url.SEP = '/';
    return Url;
}());
exports.Url = Url;
//# sourceMappingURL=Url.js.map