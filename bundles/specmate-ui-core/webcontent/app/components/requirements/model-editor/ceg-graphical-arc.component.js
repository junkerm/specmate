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
var config_1 = require("../../../config/config");
var core_1 = require("@angular/core");
var CEGGraphicalArc = (function () {
    function CEGGraphicalArc() {
        this.radius = config_1.Config.CEG_NODE_ARC_DIST;
        this.startConnectionIndex = -1;
        this.endConnectionIndex = -1;
        this.strs = [];
    }
    Object.defineProperty(CEGGraphicalArc.prototype, "connections", {
        set: function (connections) {
            var _this = this;
            if (connections.length < 2) {
                this._connections = connections;
            }
            this._connections = connections.sort(function (c1, c2) { return _this.normalize(c2.angle) - _this.normalize(c1.angle); });
            this.determineConnections();
        },
        enumerable: true,
        configurable: true
    });
    CEGGraphicalArc.prototype.determineConnections = function () {
        if (!this._connections || this._connections.length === 0) {
            return;
        }
        var maxAngleDiff = -1;
        for (var i = 0; i < this._connections.length; i++) {
            var isLastElement = i === (this._connections.length - 1);
            var startIndex = i;
            var endIndex = isLastElement ? 0 : i + 1;
            var angleDiff = this.calcAngleDiff(this._connections[endIndex].angle, this._connections[startIndex].angle);
            if (angleDiff > maxAngleDiff) {
                maxAngleDiff = angleDiff;
                this.startConnectionIndex = endIndex;
                this.endConnectionIndex = startIndex;
            }
        }
    };
    CEGGraphicalArc.prototype.calcAngleDiff = function (angle1, angle2) {
        angle1 = this.normalize(angle1);
        angle2 = this.normalize(angle2);
        if (angle2 < angle1) {
            return this.normalize((360 - angle1) + angle2);
        }
        return this.normalize(angle2 - angle1);
    };
    Object.defineProperty(CEGGraphicalArc.prototype, "marker", {
        get: function () {
            var diff = this.calcAngleDiff(this.endAngle, this.startAngle);
            var angle = this.startAngle - (diff / 2.0);
            return this.polarToCartesian(angle + 180, this.radius - 10);
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
    Object.defineProperty(CEGGraphicalArc.prototype, "startConnection", {
        get: function () {
            if (this._connections === undefined || this._connections.length === 0 || this.startConnectionIndex < 0) {
                return undefined;
            }
            return this._connections[this.startConnectionIndex];
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalArc.prototype, "endConnection", {
        get: function () {
            if (this._connections === undefined || this._connections.length === 0 || this.endConnectionIndex < 0) {
                return undefined;
            }
            return this._connections[this.endConnectionIndex];
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
    CEGGraphicalArc.prototype.polarToCartesian = function (angleInDegrees, radius) {
        if (!radius) {
            radius = this.radius;
        }
        var angleInRadians = angleInDegrees * Math.PI / 180.0;
        return {
            x: this.center.x + (radius * Math.cos(angleInRadians)),
            y: this.center.y + (radius * Math.sin(angleInRadians))
        };
    };
    CEGGraphicalArc.prototype.normalize = function (angle) {
        if (angle < 0) {
            angle = 360 + angle;
        }
        return angle % 360;
    };
    Object.defineProperty(CEGGraphicalArc.prototype, "startAngle", {
        get: function () {
            var angle = this.startConnection.angle;
            return this.normalize(angle);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalArc.prototype, "endAngle", {
        get: function () {
            var angle = this.endConnection.angle;
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
            var diff = this.calcAngleDiff(this.startAngle, this.endAngle);
            var largeArcFlag = Math.abs(diff) <= 180 ? 1 : 0;
            return [
                'M', this.arcStart.x, this.arcStart.y,
                'A', this.radius, this.radius, 0, largeArcFlag, 0, this.arcEnd.x, this.arcEnd.y
            ].join(' ');
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        core_1.Input(),
        __metadata("design:type", Array),
        __metadata("design:paramtypes", [Array])
    ], CEGGraphicalArc.prototype, "connections", null);
    __decorate([
        core_1.Input(),
        __metadata("design:type", String)
    ], CEGGraphicalArc.prototype, "type", void 0);
    CEGGraphicalArc = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: '[ceg-graphical-arc]',
            templateUrl: 'ceg-graphical-arc.component.svg'
        })
    ], CEGGraphicalArc);
    return CEGGraphicalArc;
}());
exports.CEGGraphicalArc = CEGGraphicalArc;
//# sourceMappingURL=ceg-graphical-arc.component.js.map