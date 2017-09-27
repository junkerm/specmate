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
var CircularLineCoordsProvider = (function (_super) {
    __extends(CircularLineCoordsProvider, _super);
    function CircularLineCoordsProvider(source, target, radius) {
        var _this = _super.call(this, source, target) || this;
        _this.radius = radius;
        return _this;
    }
    CircularLineCoordsProvider.prototype.getLineStart = function () {
        return this.coords(this.source, true);
    };
    CircularLineCoordsProvider.prototype.getLineEnd = function () {
        return this.coords(this.target, false);
    };
    CircularLineCoordsProvider.prototype.coords = function (node, start) {
        var sig = -1;
        if (start) {
            sig = 1;
        }
        return {
            x: node.x + Math.cos(this.angle / 360 * 2 * Math.PI) * this.radius * sig,
            y: node.y + Math.sin(this.angle / 360 * 2 * Math.PI) * this.radius * sig
        };
    };
    return CircularLineCoordsProvider;
}(line_coords_provider_base_1.LineCoordsProviderBase));
exports.CircularLineCoordsProvider = CircularLineCoordsProvider;
//# sourceMappingURL=circular-line-coords-provider.js.map