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
    Object.defineProperty(GraphicalConnectionBase.prototype, "x1", {
        get: function () {
            return this.c1.x;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "y1", {
        get: function () {
            return this.c1.y;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "x2", {
        get: function () {
            return this.c2.x;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "y2", {
        get: function () {
            return this.c2.y;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "c1", {
        get: function () {
            return this.getC(this.sourceNode);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "c2", {
        get: function () {
            return this.getC(this.targetNode);
        },
        enumerable: true,
        configurable: true
    });
    GraphicalConnectionBase.prototype.getC = function (node) {
        return coords_1.Coords.getCenter(node.x, node.y, this.nodeWidth, this.nodeHeight);
    };
    Object.defineProperty(GraphicalConnectionBase.prototype, "centerX", {
        get: function () {
            return (this.x1 + this.x2) / 2.0;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "centerY", {
        get: function () {
            return (this.y1 + this.y2) / 2.0;
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
                return this.x2 - this.nodeWidth / 2;
            }
            else if (this.isRight) {
                return this.x2 + this.nodeWidth / 2;
            }
            else if (this.isTop) {
                return this.x2 - ((this.nodeHeight / 2) / Math.tan(this.angle / 180 * Math.PI));
            }
            else if (this.isBelow) {
                return this.x2 + ((this.nodeHeight / 2) / Math.tan(this.angle / 180 * Math.PI));
            }
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "arrowY", {
        get: function () {
            if (this.isLeft) {
                return this.y2 - ((this.nodeWidth / 2) * Math.tan(this.angle / 180 * Math.PI));
            }
            else if (this.isRight) {
                return this.y2 + ((this.nodeWidth / 2) * Math.tan(this.angle / 180 * Math.PI));
            }
            else if (this.isTop) {
                return this.y2 - this.nodeHeight / 2;
            }
            else if (this.isBelow) {
                return this.y2 + this.nodeHeight / 2;
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