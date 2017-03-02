"use strict";
var Url = (function () {
    function Url() {
    }
    Url.parent = function (url) {
        console.log(url);
        var parts = url.split("/");
        var parent = "";
        for (var i = 0; i < parts.length - 1; i++) {
            parent += parts[i] + "/";
        }
        console.log(parent);
        return parent.substr(0, parent.length - 1);
    };
    return Url;
}());
exports.Url = Url;
//# sourceMappingURL=Url.js.map