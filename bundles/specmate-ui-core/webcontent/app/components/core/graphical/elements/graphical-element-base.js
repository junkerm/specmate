"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var Type_1 = require("../../../../util/Type");
var GraphicalElementBase = (function () {
    function GraphicalElementBase(selectedElementService) {
        this.selectedElementService = selectedElementService;
    }
    Object.defineProperty(GraphicalElementBase.prototype, "isVisible", {
        get: function () {
            return Type_1.Type.is(this.element, this.nodeType);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalElementBase.prototype, "selected", {
        get: function () {
            return this.selectedElementService.isSelected(this.element);
        },
        enumerable: true,
        configurable: true
    });
    return GraphicalElementBase;
}());
exports.GraphicalElementBase = GraphicalElementBase;
//# sourceMappingURL=graphical-element-base.js.map