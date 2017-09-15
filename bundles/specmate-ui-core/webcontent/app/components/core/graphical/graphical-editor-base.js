"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var config_1 = require("../../../config/config");
var GraphicalEditorBase = (function () {
    function GraphicalEditorBase() {
    }
    Object.defineProperty(GraphicalEditorBase.prototype, "editorDimensions", {
        get: function () {
            var dynamicWidth = config_1.Config.CEG_EDITOR_WIDTH;
            var dynamicHeight = config_1.Config.EDITOR_HEIGHT;
            var nodes = this.contents.filter(function (element) {
                return element.x !== undefined && element.y !== undefined;
            });
            for (var i = 0; i < nodes.length; i++) {
                var nodeX = nodes[i].x + (config_1.Config.CEG_NODE_WIDTH);
                if (dynamicWidth < nodeX) {
                    dynamicWidth = nodeX;
                }
                var nodeY = nodes[i].y + (config_1.Config.CEG_NODE_HEIGHT);
                if (dynamicHeight < nodeY) {
                    dynamicHeight = nodeY;
                }
            }
            return { width: dynamicWidth, height: dynamicHeight };
        },
        enumerable: true,
        configurable: true
    });
    return GraphicalEditorBase;
}());
exports.GraphicalEditorBase = GraphicalEditorBase;
//# sourceMappingURL=graphical-editor-base.js.map