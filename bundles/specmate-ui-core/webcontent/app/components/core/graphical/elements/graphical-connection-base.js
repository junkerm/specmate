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
var graphical_element_base_1 = require("./graphical-element-base");
var angles_1 = require("../util/angles");
var coords_1 = require("../util/coords");
var GraphicalConnectionBase = (function (_super) {
    __extends(GraphicalConnectionBase, _super);
    function GraphicalConnectionBase() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    Object.defineProperty(GraphicalConnectionBase.prototype, "element", {
        get: function () {
            return this.connection;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "lineStartX", {
        get: function () {
            return this.sourceNodeCenter.x;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "lineStartY", {
        get: function () {
            return this.sourceNodeCenter.y;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "lineEndX", {
        get: function () {
            return this.targetNodeCenter.x;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "lineEndY", {
        get: function () {
            return this.targetNodeCenter.y;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "sourceNodeCenter", {
        get: function () {
            return this.getNodeCenter(this.sourceNode);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "targetNodeCenter", {
        get: function () {
            return this.getNodeCenter(this.targetNode);
        },
        enumerable: true,
        configurable: true
    });
    GraphicalConnectionBase.prototype.getNodeCenter = function (node) {
        return coords_1.Coords.getCenter(node.x, node.y, this.nodeWidth, this.nodeHeight);
    };
    Object.defineProperty(GraphicalConnectionBase.prototype, "lineCenterX", {
        get: function () {
            return (this.lineStartX + this.lineEndX) / 2.0;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "lineCenterY", {
        get: function () {
            return (this.lineStartY + this.lineEndY) / 2.0;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "alpha1", {
        get: function () {
            return angles_1.Angles.calcAngle(-this.nodeWidth, -this.nodeHeight);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "isLeft", {
        get: function () {
            return this.angle >= -(180 + this.alpha1) && this.angle <= (180 + this.alpha1);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "isRight", {
        get: function () {
            return (this.angle >= -this.alpha1 && this.angle <= 180) || (this.angle >= -180 && this.angle <= this.alpha1);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "isTop", {
        get: function () {
            return this.angle >= 180 + this.alpha1 && this.angle <= -this.alpha1;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "isBelow", {
        get: function () {
            return this.angle >= this.alpha1 && this.angle <= -(180 + this.alpha1);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "arrowX", {
        get: function () {
            if (this.isLeft) {
                return this.lineEndX - this.nodeWidth / 2;
            }
            else if (this.isRight) {
                return this.lineEndX + this.nodeWidth / 2;
            }
            else if (this.isTop) {
                return this.lineEndX - ((this.nodeHeight / 2) / Math.tan(this.angle / 180 * Math.PI));
            }
            else if (this.isBelow) {
                return this.lineEndX + ((this.nodeHeight / 2) / Math.tan(this.angle / 180 * Math.PI));
            }
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "arrowY", {
        get: function () {
            if (this.isLeft) {
                return this.lineEndY - ((this.nodeWidth / 2) * Math.tan(this.angle / 180 * Math.PI));
            }
            else if (this.isRight) {
                return this.lineEndY + ((this.nodeWidth / 2) * Math.tan(this.angle / 180 * Math.PI));
            }
            else if (this.isTop) {
                return this.lineEndY - this.nodeHeight / 2;
            }
            else if (this.isBelow) {
                return this.lineEndY + this.nodeHeight / 2;
            }
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "angle", {
        get: function () {
            return angles_1.Angles.angle(this);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "sourceNode", {
        get: function () {
            return this.getNode(this.connection.source);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "targetNode", {
        get: function () {
            return this.getNode(this.connection.target);
        },
        enumerable: true,
        configurable: true
    });
    GraphicalConnectionBase.prototype.getNode = function (proxy) {
        if (!proxy) {
            throw new Error('Tried to get element for undefined proxy!');
        }
        return this.nodes.filter(function (containedNode) { return containedNode.url === proxy.url; })[0];
    };
    return GraphicalConnectionBase;
}(graphical_element_base_1.GraphicalElementBase));
exports.GraphicalConnectionBase = GraphicalConnectionBase;
//# sourceMappingURL=graphical-connection-base.js.map