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
var tool_base_1 = require("./tool-base");
var MoveTool = (function (_super) {
    __extends(MoveTool, _super);
    function MoveTool(selectedElementService) {
        var _this = _super.call(this, selectedElementService) || this;
        _this.name = 'Select';
        _this.icon = 'arrows';
        _this.color = 'primary';
        _this.cursor = 'move';
        _this.selectedElements = [];
        _this.done = false;
        return _this;
    }
    MoveTool.prototype.activate = function () {
        this.selectedElements = [];
    };
    MoveTool.prototype.deactivate = function () {
        this.selectedElements = [];
    };
    MoveTool.prototype.click = function (event, zoom) {
        return Promise.resolve();
    };
    MoveTool.prototype.select = function (element) {
        this.selectedElements[0] = element;
        this.selectedElementService.selectedElement = element;
        var blur = document.activeElement.blur;
        if (blur) {
            document.activeElement.blur();
        }
        return Promise.resolve();
    };
    return MoveTool;
}(tool_base_1.ToolBase));
exports.MoveTool = MoveTool;
//# sourceMappingURL=move-tool.js.map