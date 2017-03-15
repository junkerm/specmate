"use strict";
var MoveTool = (function () {
    function MoveTool() {
        this.name = 'Move & Select';
        this.icon = 'arrows';
        this.color = 'primary';
        this.selectedElements = [];
    }
    MoveTool.prototype.activate = function () {
        this.selectedElements = [];
    };
    MoveTool.prototype.deactivate = function () {
        this.selectedElements = [];
    };
    MoveTool.prototype.click = function (event) { };
    MoveTool.prototype.select = function (element) {
        console.log("SELECT");
        this.selectedElements[0] = element;
    };
    return MoveTool;
}());
exports.MoveTool = MoveTool;
//# sourceMappingURL=move-tool.js.map