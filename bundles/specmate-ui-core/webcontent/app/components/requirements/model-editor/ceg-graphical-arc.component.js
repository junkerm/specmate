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
var core_1 = require('@angular/core');
var CEGGraphicalArc = (function () {
    function CEGGraphicalArc() {
        this.radius = 100;
    }
    Object.defineProperty(CEGGraphicalArc.prototype, "connections", {
        set: function (connections) {
            var _this = this;
            this._connections = connections.sort(function (c1, c2) { return _this.normalize(c2.angle) - _this.normalize(c1.angle); });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalArc.prototype, "center", {
        get: function () {
            return {
                x: this.anyConnection.x2,
                y: this.anyConnection.y2
            };
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalArc.prototype, "anyConnection", {
        get: function () {
            return this._connections[0];
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalArc.prototype, "firstConnection", {
        get: function () {
            if (this._connections === undefined || this._connections.length === 0) {
                return undefined;
            }
            return this._connections[0];
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalArc.prototype, "lastConnection", {
        get: function () {
            if (this._connections === undefined || this._connections.length === 0) {
                return undefined;
            }
            return this._connections[this._connections.length - 1];
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalArc.prototype, "draw", {
        get: function () {
            return this._connections && this._connections.length > 1;
        },
        enumerable: true,
        configurable: true
    });
    CEGGraphicalArc.prototype.polarToCartesian = function (angleInDegrees) {
        var angleInRadians = angleInDegrees * Math.PI / 180.0;
        return {
            x: this.center.x + (this.radius * Math.cos(angleInRadians)),
            y: this.center.y + (this.radius * Math.sin(angleInRadians))
        };
    };
    CEGGraphicalArc.prototype.normalize = function (angle) {
        if (angle < 0) {
            angle = 360 + angle;
        }
        return angle;
    };
    Object.defineProperty(CEGGraphicalArc.prototype, "startAngle", {
        get: function () {
            var angle = this.firstConnection.angle;
            return this.normalize(angle);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalArc.prototype, "endAngle", {
        get: function () {
            var angle = this.lastConnection.angle;
            return this.normalize(angle);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalArc.prototype, "arcStart", {
        get: function () {
            return this.polarToCartesian(this.startAngle + 180);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalArc.prototype, "arcEnd", {
        get: function () {
            return this.polarToCartesian(this.endAngle + 180);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalArc.prototype, "arcD", {
        get: function () {
            console.log(this.startAngle + "-" + this.endAngle + " = " + (this.startAngle - this.endAngle));
            var diff = this.endAngle - this.startAngle;
            var largeArcFlag = Math.abs(this.endAngle - this.startAngle) <= 180 ? "0" : "1";
            var d = [
                "M", this.arcStart.x, this.arcStart.y,
                "A", this.radius, this.radius, 0, largeArcFlag, 0, this.arcEnd.x, this.arcEnd.y
            ].join(" ");
            return d;
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Array), 
        __metadata('design:paramtypes', [Array])
    ], CEGGraphicalArc.prototype, "connections", null);
    CEGGraphicalArc = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: '[ceg-graphical-arc]',
            templateUrl: 'ceg-graphical-arc.component.svg'
        }), 
        __metadata('design:paramtypes', [])
    ], CEGGraphicalArc);
    return CEGGraphicalArc;
}());
exports.CEGGraphicalArc = CEGGraphicalArc;
//# sourceMappingURL=ceg-graphical-arc.component.js.map