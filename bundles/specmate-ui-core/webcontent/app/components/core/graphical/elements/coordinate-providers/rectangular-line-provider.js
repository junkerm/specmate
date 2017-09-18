"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var RectangularLineProvider = (function () {
    function RectangularLineProvider(nodeWidth, nodeHeight) {
        this.nodeWidth = nodeWidth;
        this.nodeHeight = nodeHeight;
    }
    Object.defineProperty(RectangularLineProvider.prototype, "center", {
        get: function () {
            return { x: 0, y: 0 };
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(RectangularLineProvider.prototype, "lineStart", {
        get: function () {
            return { x: 0, y: 0 };
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(RectangularLineProvider.prototype, "lineEnd", {
        get: function () {
            return { x: 0, y: 0 };
        },
        enumerable: true,
        configurable: true
    });
    return RectangularLineProvider;
}());
exports.RectangularLineProvider = RectangularLineProvider;
//# sourceMappingURL=rectangular-line-provider.js.map