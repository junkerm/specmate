"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var MoveTool = (function () {
    function MoveTool() {
        this.name = 'Select';
        this.icon = 'arrows';
        this.color = 'primary';
        this.cursor = 'move';
        this.selectedElements = [];
        this.done = false;
    }
    MoveTool.prototype.activate = function () {
        this.selectedElements = [];
    };
    MoveTool.prototype.deactivate = function () {
        this.selectedElements = [];
    };
    MoveTool.prototype.click = function (event) {
        return Promise.resolve();
    };
    MoveTool.prototype.select = function (element) {
        this.selectedElements[0] = element;
        var blur = document.activeElement.blur;
        if (blur) {
            document.activeElement.blur();
        }
        return Promise.resolve();
    };
    return MoveTool;
}());
exports.MoveTool = MoveTool;
//# sourceMappingURL=move-tool.js.map