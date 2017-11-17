"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var config_1 = require("../../../config/config");
var GraphicalEditorBase = (function () {
    function GraphicalEditorBase() {
        this.isMaximized = false;
        this.isGridShown = true;
        this.zoom = 1;
    }
    GraphicalEditorBase.prototype.zoomIn = function () {
        if (this.canZoomIn) {
            this.zoom += config_1.Config.GRAPHICAL_EDITOR_ZOOM_STEP;
        }
    };
    GraphicalEditorBase.prototype.zoomOut = function () {
        if (this.canZoomOut) {
            this.zoom -= config_1.Config.GRAPHICAL_EDITOR_ZOOM_STEP;
        }
    };
    GraphicalEditorBase.prototype.resetZoom = function () {
        this.zoom = 1;
    };
    Object.defineProperty(GraphicalEditorBase.prototype, "canZoomIn", {
        get: function () {
            return this.zoom < config_1.Config.GRAPHICAL_EDITOR_ZOOM_MAX;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalEditorBase.prototype, "canZoomOut", {
        get: function () {
            return this.zoom > config_1.Config.GRAPHICAL_EDITOR_ZOOM_STEP * 2;
        },
        enumerable: true,
        configurable: true
    });
    GraphicalEditorBase.prototype.maximize = function () {
        this.isMaximized = true;
    };
    GraphicalEditorBase.prototype.unMaximize = function () {
        this.isMaximized = false;
    };
    GraphicalEditorBase.prototype.showGrid = function () {
        this.isGridShown = true;
    };
    GraphicalEditorBase.prototype.hideGrid = function () {
        this.isGridShown = false;
    };
    Object.defineProperty(GraphicalEditorBase.prototype, "editorDimensions", {
        get: function () {
            var dynamicWidth = config_1.Config.GRAPHICAL_EDITOR_WIDTH;
            var dynamicHeight = config_1.Config.GRAPHICAL_EDITOR_HEIGHT;
            var nodes = this.contents.filter(function (element) {
                return element.x !== undefined && element.y !== undefined;
            });
            for (var i = 0; i < nodes.length; i++) {
                var nodeX = nodes[i].x + (config_1.Config.GRAPHICAL_EDITOR_PADDING_HORIZONTAL);
                if (dynamicWidth < nodeX) {
                    dynamicWidth = nodeX;
                }
                var nodeY = nodes[i].y + (config_1.Config.GRAPHICAL_EDITOR_PADDING_VERTICAL);
                if (dynamicHeight < nodeY) {
                    dynamicHeight = nodeY;
                }
            }
            return { width: dynamicWidth, height: dynamicHeight };
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalEditorBase.prototype, "gridSize", {
        get: function () {
            return config_1.Config.GRAPHICAL_EDITOR_GRID_SPACE;
        },
        enumerable: true,
        configurable: true
    });
    return GraphicalEditorBase;
}());
exports.GraphicalEditorBase = GraphicalEditorBase;
//# sourceMappingURL=graphical-editor-base.js.map