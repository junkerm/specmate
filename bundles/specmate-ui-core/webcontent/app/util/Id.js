"use strict";
var config_1 = require('../config/config');
var Id = (function () {
    function Id() {
    }
    Id.fromName = function (name) {
        for (var i = 0; i < config_1.Config.ID_FORBIDDEN_CHARS.length; i++) {
            name = Id.replaceAll(name, config_1.Config.ID_FORBIDDEN_CHARS[i], config_1.Config.ID_FORBIDDEN_REPLACEMENT);
        }
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