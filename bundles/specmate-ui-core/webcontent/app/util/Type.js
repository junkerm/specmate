"use strict";
var Type = (function () {
    function Type() {
    }
    Type.is = function (o1, o2) {
        return o1.className === o2.className;
    };
    return Type;
}());
exports.Type = Type;
//# sourceMappingURL=Type.js.map