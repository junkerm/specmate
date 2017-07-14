"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var Objects = (function () {
    function Objects() {
    }
    Objects.clone = function (source, target) {
        if (source === target) {
            return;
        }
        var actualTarget = target;
        if (target === undefined) {
            actualTarget = Objects.getFreshInstance(source);
        }
        for (var name_1 in source) {
            actualTarget[name_1] = Objects.getFreshInstance(source[name_1]);
            if (Objects.isObject(source[name_1])) {
                Objects.clone(source[name_1], actualTarget[name_1]);
            }
            else if (Objects.isArray(source[name_1])) {
                for (var i = 0; i < source.length; i++) {
                    actualTarget[name_1][i] = Objects.clone(source[name_1][i]);
                }
            }
            else {
                actualTarget[name_1] = source[name_1];
            }
        }
        return actualTarget;
    };
    Objects.getFreshInstance = function (element) {
        if (Objects.isArray(element)) {
            return [];
        }
        else if (Objects.isObject(element)) {
            return {};
        }
        return '';
    };
    Objects.isObject = function (element) {
        return typeof (element) === 'object' || typeof (element) === 'function';
    };
    Objects.isArray = function (element) {
        return Array.isArray(element);
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