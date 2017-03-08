"use strict";
var Id = (function () {
    function Id() {
    }
    Id.fromName = function (name) {
        for (var i = 0; i < Id.FORBIDDEN_CHARS.length; i++) {
            name = Id.replaceAll(name, Id.FORBIDDEN_CHARS[i], Id.FORBIDDEN_REPLACEMENT);
        }
        return name;
    };
    Id.replaceAll = function (str, search, replacement) {
        while (str.indexOf(search) >= 0) {
            str = str.replace(search, replacement);
        }
        return str;
    };
    Id.FORBIDDEN_CHARS = ['/', ' ', '\'', '"', '?', '%', '(', ')', '@', ',', '.', '[', ']', '{', '}', '--'];
    Id.FORBIDDEN_REPLACEMENT = '-';
    return Id;
}());
exports.Id = Id;
//# sourceMappingURL=Id.js.map