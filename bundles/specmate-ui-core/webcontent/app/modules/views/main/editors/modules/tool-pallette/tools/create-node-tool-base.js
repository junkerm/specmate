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
var create_tool_base_1 = require("./create-tool-base");
var draggable_element_base_1 = require("../../graphical-editor/elements/draggable-element-base");
var CreateNodeToolBase = /** @class */ (function (_super) {
    __extends(CreateNodeToolBase, _super);
    function CreateNodeToolBase(parent, dataService, selectedElementService) {
        var _this = _super.call(this, parent, dataService, selectedElementService) || this;
        _this.parent = parent;
        _this.dataService = dataService;
        _this.color = "primary";
        _this.cursor = 'cell';
        _this.done = false;
        return _this;
    }
    CreateNodeToolBase.prototype.click = function (event, zoom) {
        return this.createNewNodeAtCoords({
            x: draggable_element_base_1.DraggableElementBase.roundToGrid(event.offsetX / zoom),
            y: draggable_element_base_1.DraggableElementBase.roundToGrid(event.offsetY / zoom)
        });
    };
    CreateNodeToolBase.prototype.select = function (element) {
        this.selectedElements[0] = element;
        this.selectedElementService.select(element);
        return Promise.resolve();
    };
    CreateNodeToolBase.prototype.createNewNodeAtCoords = function (coords) {
        var _this = this;
        return this.getElementFactory(coords).create(this.parent, false)
            .then(function (node) { return _this.select(node); })
            .then(function () { return _this.done = true; })
            .then(function () { return Promise.resolve(); });
    };
    return CreateNodeToolBase;
}(create_tool_base_1.CreateToolBase));
exports.CreateNodeToolBase = CreateNodeToolBase;
//# sourceMappingURL=create-node-tool-base.js.map