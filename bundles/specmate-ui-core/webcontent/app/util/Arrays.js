"use strict";
var Arrays = (function () {
    function Arrays() {
    }
    Arrays.remove = function (array, element) {
        if (!array) {
            return;
        }
        var index = array.indexOf(element);
        if (index >= 0) {
            array.splice(index, 1);
        }
    };
    return Arrays;
}());
exports.Arrays = Arrays;
//# sourceMappingURL=Arrays.js.map