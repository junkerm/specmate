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
    Object.defineProperty(GraphicalConnectionBase.prototype, "angle", {
        get: function () {
            if (!this.startLineCoordsProvider) {
                return 0;
            }
            return this.startLineCoordsProvider.angle;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "lineStartX", {
        get: function () {
            if (!this.startLineCoordsProvider) {
                return 0;
            }
            return this.startLineCoordsProvider.lineStart.x;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "lineStartY", {
        get: function () {
            if (!this.startLineCoordsProvider) {
                return 0;
            }
            return this.startLineCoordsProvider.lineStart.y;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "lineEndX", {
        get: function () {
            if (!this.endLineCoordsProvider) {
                return 0;
            }
            return this.endLineCoordsProvider.lineEnd.x;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "lineEndY", {
        get: function () {
            if (!this.endLineCoordsProvider) {
                return 0;
            }
            return this.endLineCoordsProvider.lineEnd.y;
        },
        enumerable: true,
        configurable: true
    });
    return GraphicalConnectionBase;
}(graphical_element_base_1.GraphicalElementBase));
exports.GraphicalConnectionBase = GraphicalConnectionBase;
//# sourceMappingURL=graphical-connection-base.js.map