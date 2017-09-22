"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var angles_1 = require("../../util/angles");
var ConnectionLine = (function () {
    function ConnectionLine() {
    }
    Object.defineProperty(ConnectionLine.prototype, "center", {
        get: function () {
            return {
                x: (this.source.x + this.target.x) / 2,
                y: (this.source.y + this.target.y) / 2
            };
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ConnectionLine.prototype, "angle", {
        get: function () {
            var angle = this.rawAngle;
            if (angle <= 90 && angle >= -90) {
                return angle;
            }
            return angle + 180;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ConnectionLine.prototype, "rawAngle", {
        get: function () {
            return angles_1.Angles.angle(this.source.x, this.source.y, this.target.x, this.target.y);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ConnectionLine.prototype, "length", {
        get: function () {
            var dx = this.target.x - this.source.x;
            var dy = this.target.y - this.source.y;
            return Math.sqrt(dx * dx + dy * dy);
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        core_1.Input(),
        __metadata("design:type", Object)
    ], ConnectionLine.prototype, "source", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Object)
    ], ConnectionLine.prototype, "target", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Boolean)
    ], ConnectionLine.prototype, "selected", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Boolean)
    ], ConnectionLine.prototype, "valid", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", String)
    ], ConnectionLine.prototype, "text", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Boolean)
    ], ConnectionLine.prototype, "arrowTip", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Boolean)
    ], ConnectionLine.prototype, "fillArrowTip", void 0);
    ConnectionLine = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: '[connection-line]',
            templateUrl: 'connection-line.component.svg',
            styleUrls: ['connection-line.component.css']
        })
    ], ConnectionLine);
    return ConnectionLine;
}());
exports.ConnectionLine = ConnectionLine;
//# sourceMappingURL=connection-line.component.js.map