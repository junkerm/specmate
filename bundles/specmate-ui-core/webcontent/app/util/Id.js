"use strict";
var Id = (function () {
    function Id() {
    }
    Id.fromName = function (name) {
        name = Id.replaceAll(name, ' ', '');
        name = Id.replaceAll(name, '/', '-');
        return name;
    };
    Id.replaceAll = function (str, search, replacement) {
        while (str.indexOf(search) >= 0) {
            str = str.replace(search, replacement);
        }
        return str;
    };
    return Id;
}());
exports.Id = Id;
//# sourceMappingURL=Id.js.map