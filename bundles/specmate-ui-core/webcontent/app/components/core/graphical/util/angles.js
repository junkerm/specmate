"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var Angles = (function () {
    function Angles() {
    }
    Angles.angle = function (l) {
        return Angles.calcAngle(l.lineEndX - l.lineStartX, l.lineEndY - l.lineStartY);
    };
    Angles.calcAngle = function (dx, dy) {
        return Math.atan2(dy, dx) * 180.0 / Math.PI;
    };
    return Angles;
}());
exports.Angles = Angles;
//# sourceMappingURL=angles.js.map