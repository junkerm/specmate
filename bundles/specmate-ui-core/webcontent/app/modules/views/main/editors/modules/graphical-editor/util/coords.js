"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var Coords = (function () {
    function Coords() {
    }
    Coords.getCenter = function (x, y, width, height) {
        var cx = Number.parseFloat((x + width / 2) + '');
        var cy = Number.parseFloat((y + height / 2) + '');
        return {
            x: cx,
            y: cy
        };
    };
    return Coords;
}());
exports.Coords = Coords;
//# sourceMappingURL=coords.js.map