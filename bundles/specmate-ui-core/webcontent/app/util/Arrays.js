"use strict";
var Arrays = (function () {
    function Arrays() {
    }
    Arrays.remove = function (array, element) {
        var index = array.indexOf(element);
        array.splice(index, 1);
    };
    return Arrays;
}());
exports.Arrays = Arrays;
//# sourceMappingURL=Arrays.js.map