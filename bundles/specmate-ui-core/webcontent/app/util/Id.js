"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var config_1 = require("../config/config");
var Id = (function () {
    function Id() {
    }
    Id.fromName = function (name) {
        var arr = name.toLowerCase().split('');
        for (var i = 0; i < arr.length; i++) {
            var char = arr[i];
            if (!Id.isAllowed(char)) {
                arr[i] = config_1.Config.ID_FORBIDDEN_REPLACEMENT;
            }
        }
        return arr.join('');
    };
    Id.isAllowed = function (char) {
        return config_1.Config.ID_ALLOWED_CHARS.indexOf(char) >= 0;
    };
    Id.replaceAll = function (str, search, replacement) {
        while (str.indexOf(search) >= 0) {
            str = str.replace(search, replacement);
        }
        return str;
    };
    Id.highestId = function (elements) {
        var max = config_1.Config.ID_MIN;
        for (var i = 0; i < elements.length; i++) {
            var element = elements[i];
            var regex = new RegExp('.+' + config_1.Config.ID_SEP + '([0-9]+)', 'g');
            var match = regex.exec(element.id);
            if (match) {
                var num = Number(match[1]);
                if (num > max) {
                    max = num;
                }
            }
        }
        return max;
    };
    Id.generate = function (existingElements, prefix) {
        return prefix + config_1.Config.ID_SEP + (Id.highestId(existingElements) + 1);
    };
    return Id;
}());
exports.Id = Id;
//# sourceMappingURL=Id.js.map