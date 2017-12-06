"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var Strings = /** @class */ (function () {
    function Strings() {
    }
    Strings.truncate = function (str, length, ellipsis) {
        if (!str) {
            return undefined;
        }
        if (!ellipsis) {
            ellipsis = '...';
        }
        if (str.length > length + ellipsis.length) {
            return str.slice(0, length) + ellipsis;
        }
        return str;
    };
    Strings.contains = function (haystack, needle) {
        if (!haystack) {
            return false;
        }
        return haystack.indexOf(needle) >= 0;
    };
    return Strings;
}());
exports.Strings = Strings;
//# sourceMappingURL=strings.js.map