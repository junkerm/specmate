"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
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
    Arrays.contains = function (array, element) {
        if (!array) {
            return false;
        }
        return array.indexOf(element) >= 0;
    };
    return Arrays;
}());
exports.Arrays = Arrays;
//# sourceMappingURL=Arrays.js.map