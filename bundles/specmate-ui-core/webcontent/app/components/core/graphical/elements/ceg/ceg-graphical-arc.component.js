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
var config_1 = require("../../../../../config/config");
var angles_1 = require("../../util/angles");
var CEGNode_1 = require("../../../../../model/CEGNode");
var graphical_element_base_1 = require("../graphical-element-base");
var CEGGraphicalArc = (function (_super) {
    __extends(CEGGraphicalArc, _super);
    function CEGGraphicalArc() {
        var _this = _super !== null && _super.apply(this, arguments) || this;
        _this.nodeType = CEGNode_1.CEGNode;
        _this.radius = config_1.Config.CEG_NODE_ARC_DIST;
        _this.startConnectionIndex = -1;
        _this.endConnectionIndex = -1;
        _this.startPoints = {};
        _this.endPoints = {};
        return _this;
    }
    Object.defineProperty(CEGGraphicalArc.prototype, "connections", {
        get: function () {
            if (!this.node) {
                return [];
            }
            return this._connections;
        },
        set: function (connections) {
            var _this = this;
            if (!connections) {
                return;
            }
            if (!this.node) {
                return;
            }
            this._connections = connections.filter(function (connection) { return connection.target.url === _this.node.url; })
                .sort(function (c1, c2) { return _this.normalize(_this.getAngle(c2)) - _this.normalize(_this.getAngle(c1)); });
            ;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalArc.prototype, "element", {
        get: function () {
            return this.node;
        },
        enumerable: true,
        configurable: true
    });
    CEGGraphicalArc.prototype.getAngle = function (connection) {
        var startPoint = this.getStartPoint(connection);
        var endPoint = this.getEndPoint(connection);
        return angles_1.Angles.angle(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
    };
    CEGGraphicalArc.prototype.determineConnections = function () {
        if (!this.connections || this.connections.length === 0) {
            return;
        }
        var maxAngleDiff = -1;
        for (var i = 0; i < this.connections.length; i++) {
            var isLastElement = i === (this.connections.length - 1);
            var startIndex = i;
            var endIndex = isLastElement ? 0 : i + 1;
            var angleDiff = this.calcAngleDiff(this.getAngle(this.connections[endIndex]), this.getAngle(this.connections[startIndex]));
            if (angleDiff > maxAngleDiff) {
                maxAngleDiff = angleDiff;
                this.startConnectionIndex = endIndex;
                this.endConnectionIndex = startIndex;
            }
        }
    };
    CEGGraphicalArc.prototype.getStartPoint = function (connection) {
        if (!this.nodes || !connection) {
            return { x: 0, y: 0 };
        }
        if (!this.startPoints[connection.url]) {
            this.startPoints[connection.url] = this.nodes.find(function (node) { return node.url === connection.source.url; });
        }
        return this.startPoints[connection.url];
    };
    CEGGraphicalArc.prototype.getEndPoint = function (connection) {
        if (!this.nodes || !connection) {
            return { x: 0, y: 0 };
        }
        if (!this.endPoints[connection.url]) {
            this.endPoints[connection.url] = this.nodes.find(function (node) { return node.url === connection.target.url; });
        }
        return this.endPoints[connection.url];
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
            if (!this.connections) {
                return { x: 0, y: 0 };
            }
            var endPoint = this.getEndPoint(this.connections[0]);
            return {
                x: endPoint.x,
                y: endPoint.y
            };
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalArc.prototype, "startConnection", {
        get: function () {
            this.determineConnections();
            if (this.connections === undefined || this.connections.length === 0 || this.startConnectionIndex < 0) {
                return undefined;
            }
            return this.connections[this.startConnectionIndex];
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalArc.prototype, "endConnection", {
        get: function () {
            this.determineConnections();
            if (this.connections === undefined || this.connections.length === 0 || this.endConnectionIndex < 0) {
                return undefined;
            }
            return this.connections[this.endConnectionIndex];
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalArc.prototype, "isVisible", {
        get: function () {
            return this.node && this.node.incomingConnections && this.node.incomingConnections.length > 1;
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
            var angle = this.getAngle(this.startConnection);
            return this.normalize(angle);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalArc.prototype, "endAngle", {
        get: function () {
            var angle = this.getAngle(this.endConnection);
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
        __metadata("design:type", CEGNode_1.CEGNode)
    ], CEGGraphicalArc.prototype, "node", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Array)
    ], CEGGraphicalArc.prototype, "nodes", void 0);
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
}(graphical_element_base_1.GraphicalElementBase));
exports.CEGGraphicalArc = CEGGraphicalArc;
//# sourceMappingURL=ceg-graphical-arc.component.js.map