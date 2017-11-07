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
var GraphicalNodeBase = (function (_super) {
    __extends(GraphicalNodeBase, _super);
    function GraphicalNodeBase() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    Object.defineProperty(GraphicalNodeBase.prototype, "center", {
        get: function () {
            return {
                x: this.topLeft.x + this.dimensions.width / 2,
                y: this.topLeft.y + this.dimensions.height / 2
            };
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalNodeBase.prototype, "topLeft", {
        get: function () {
            return {
                x: Math.max(this.element.x - this.dimensions.width / 2, 0),
                y: Math.max(this.element.y - this.dimensions.height / 2, 0)
            };
        },
        enumerable: true,
        configurable: true
    });
    return GraphicalNodeBase;
}(graphical_element_base_1.GraphicalElementBase));
exports.GraphicalNodeBase = GraphicalNodeBase;
//# sourceMappingURL=graphical-node-base.js.map