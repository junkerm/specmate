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
var line_coords_provider_base_1 = require("./line-coords-provider-base");
var DiamondLineCoordsProvider = (function (_super) {
    __extends(DiamondLineCoordsProvider, _super);
    function DiamondLineCoordsProvider(source, target, dimension) {
        var _this = _super.call(this, source, target) || this;
        _this.dimension = dimension;
        return _this;
    }
    DiamondLineCoordsProvider.prototype.getLineStart = function () {
        var x = this.x(this.target, this.source);
        var y = this.y(this.target, this.source);
        return {
            x: this.source.x + x,
            y: this.source.y + y
        };
    };
    DiamondLineCoordsProvider.prototype.getLineEnd = function () {
        var x = this.x(this.source, this.target);
        var y = this.y(this.source, this.target);
        return {
            x: this.target.x + x,
            y: this.target.y + y
        };
    };
    DiamondLineCoordsProvider.prototype.a1 = function (source, target) {
        if (this.q1(source, target) || this.q3(source, target)) {
            return 1;
        }
        return -1;
    };
    DiamondLineCoordsProvider.prototype.c1 = function (source, target) {
        if (this.q1(source, target) || this.q2(source, target)) {
            return this.dimension;
        }
        return -this.dimension;
    };
    DiamondLineCoordsProvider.prototype.a2 = function (source, target) {
        return (source.y - target.y) / (source.x - target.x);
    };
    DiamondLineCoordsProvider.prototype.c2 = function (source, target) {
        return 0;
    };
    DiamondLineCoordsProvider.prototype.x = function (source, target) {
        return (this.c2(source, target) - this.c1(source, target)) / (this.a1(source, target) - this.a2(source, target));
    };
    DiamondLineCoordsProvider.prototype.y = function (source, target) {
        return this.a1(source, target) * this.x(source, target) + this.c1(source, target);
    };
    DiamondLineCoordsProvider.prototype.q1 = function (source, target) {
        return source.x <= target.x && source.y > target.y;
    };
    DiamondLineCoordsProvider.prototype.q2 = function (source, target) {
        return source.x > target.x && source.y >= target.y;
    };
    DiamondLineCoordsProvider.prototype.q3 = function (source, target) {
        return source.x >= target.x && source.y < target.y;
    };
    DiamondLineCoordsProvider.prototype.q4 = function (source, target) {
        return source.x < target.x && source.y <= target.y;
    };
    return DiamondLineCoordsProvider;
}(line_coords_provider_base_1.LineCoordsProviderBase));
exports.DiamondLineCoordsProvider = DiamondLineCoordsProvider;
//# sourceMappingURL=diamond-line-coords-provider.js.map