"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var Angles = (function () {
    function Angles() {
    }
    Angles.angle = function (l) {
        return Angles.calcAngle(l.x2 - l.x1, l.y2 - l.y1);
    };
    Angles.calcAngle = function (dx, dy) {
        return Math.atan2(dy, dx) * 180.0 / Math.PI;
    };
    return Angles;
}());
exports.Angles = Angles;
//# sourceMappingURL=angles.js.map