"use strict";
var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
Object.defineProperty(exports, "__esModule", { value: true });
var angles_1 = require("../../util/angles");
var line_coords_provider_base_1 = require("./line-coords-provider-base");
var RectangularLineCoordsProvider = (function (_super) {
    __extends(RectangularLineCoordsProvider, _super);
    function RectangularLineCoordsProvider(source, target, dimensions) {
        var _this = _super.call(this, source, target) || this;
        _this.dimensions = dimensions;
        return _this;
    }
    RectangularLineCoordsProvider.prototype.getLineEnd = function () {
        if (this.isLeft) {
            return {
                x: this.target.x - this.dimensions.width / 2,
                y: this.target.y - ((this.dimensions.width / 2) * Math.tan(this.angle / 180 * Math.PI))
            };
        }
        else if (this.isRight) {
            return {
                x: this.target.x + this.dimensions.width / 2,
                y: this.target.y + ((this.dimensions.width / 2) * Math.tan(this.angle / 180 * Math.PI))
            };
        }
        else if (this.isTop) {
            return {
                x: this.target.x - ((this.dimensions.height / 2) / Math.tan(this.angle / 180 * Math.PI)),
                y: this.target.y - this.dimensions.height / 2
            };
        }
        else if (this.isBelow) {
            return {
                x: this.target.x + ((this.dimensions.height / 2) / Math.tan(this.angle / 180 * Math.PI)),
                y: this.target.y + this.dimensions.height / 2
            };
        }
    };
    RectangularLineCoordsProvider.prototype.getLineStart = function () {
        if (this.isRight) {
            return {
                x: this.source.x - this.dimensions.width / 2,
                y: this.source.y - ((this.dimensions.width / 2) * Math.tan(this.angle / 180 * Math.PI))
            };
        }
        else if (this.isLeft) {
            return {
                x: this.source.x + this.dimensions.width / 2,
                y: this.source.y + ((this.dimensions.width / 2) * Math.tan(this.angle / 180 * Math.PI))
            };
        }
        else if (this.isBelow) {
            return {
                x: this.source.x - ((this.dimensions.height / 2) / Math.tan(this.angle / 180 * Math.PI)),
                y: this.source.y - this.dimensions.height / 2
            };
        }
        else if (this.isTop) {
            return {
                x: this.source.x + ((this.dimensions.height / 2) / Math.tan(this.angle / 180 * Math.PI)),
                y: this.source.y + this.dimensions.height / 2
            };
        }
    };
    RectangularLineCoordsProvider.prototype.getNodeCenter = function (node) {
        return {
            x: node.x + this.dimensions.width / 2,
            y: node.y + this.dimensions.height / 2
        };
    };
    Object.defineProperty(RectangularLineCoordsProvider.prototype, "alpha1", {
        get: function () {
            return angles_1.Angles.calcAngle(-this.dimensions.width, -this.dimensions.height);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(RectangularLineCoordsProvider.prototype, "isLeft", {
        get: function () {
            return this.angle >= -(180 + this.alpha1) && this.angle <= (180 + this.alpha1);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(RectangularLineCoordsProvider.prototype, "isRight", {
        get: function () {
            return (this.angle >= -this.alpha1 && this.angle <= 180) || (this.angle >= -180 && this.angle <= this.alpha1);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(RectangularLineCoordsProvider.prototype, "isTop", {
        get: function () {
            return this.angle >= 180 + this.alpha1 && this.angle <= -this.alpha1;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(RectangularLineCoordsProvider.prototype, "isBelow", {
        get: function () {
            return this.angle >= this.alpha1 && this.angle <= -(180 + this.alpha1);
        },
        enumerable: true,
        configurable: true
    });
    return RectangularLineCoordsProvider;
}(line_coords_provider_base_1.LineCoordsProviderBase));
exports.RectangularLineCoordsProvider = RectangularLineCoordsProvider;
//# sourceMappingURL=rectangular-line-coords-provider.js.map