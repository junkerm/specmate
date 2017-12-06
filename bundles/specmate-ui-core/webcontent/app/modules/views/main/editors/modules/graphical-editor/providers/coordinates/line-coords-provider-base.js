"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var angles_1 = require("../../util/angles");
var LineCoordsProviderBase = /** @class */ (function () {
    function LineCoordsProviderBase(source, target) {
        this.source = source;
        this.target = target;
    }
    Object.defineProperty(LineCoordsProviderBase.prototype, "center", {
        get: function () {
            return {
                x: (this.lineStart.x + this.lineEnd.x) / 2.0,
                y: (this.lineStart.y + this.lineEnd.y) / 2.0
            };
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(LineCoordsProviderBase.prototype, "lineStart", {
        get: function () {
            return this.getLineStart();
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(LineCoordsProviderBase.prototype, "lineEnd", {
        get: function () {
            return this.getLineEnd();
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(LineCoordsProviderBase.prototype, "angle", {
        get: function () {
            return angles_1.Angles.angle(this.source.x, this.source.y, this.target.x, this.target.y);
        },
        enumerable: true,
        configurable: true
    });
    return LineCoordsProviderBase;
}());
exports.LineCoordsProviderBase = LineCoordsProviderBase;
//# sourceMappingURL=line-coords-provider-base.js.map