"use strict";
var Objects = (function () {
    function Objects() {
    }
    Objects.clone = function (source, target) {
        for (var name_1 in source) {
            if (typeof (source[name_1]) !== 'object' && typeof (source[name_1]) !== 'function') {
                target[name_1] = source[name_1];
            }
            else {
                target[name_1] = {};
                this.clone(source[name_1], target[name_1]);
            }
        }
    };
    Objects.equals = function (o1, o2) {
        if (o1 && o2) {
            for (var name_2 in o1) {
                if (!o2[name_2] || typeof (o1[name_2]) !== typeof (o2[name_2])) {
                    return false;
                }
                else if (typeof (o1[name_2]) !== 'object' && typeof (o1[name_2]) !== 'function') {
                    if (o1[name_2] !== o2[name_2]) {
                        return false;
                    }
                }
                else {
                    if (!this.equals(o1[name_2], o2[name_2])) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    };
    return Objects;
}());
exports.Objects = Objects;
//# sourceMappingURL=Objects.js.map