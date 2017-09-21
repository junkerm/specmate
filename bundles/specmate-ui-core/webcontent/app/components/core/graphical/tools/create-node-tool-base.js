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
var Id_1 = require("../../../../util/Id");
var create_tool_base_1 = require("./create-tool-base");
var draggable_element_base_1 = require("../elements/draggable-element-base");
var CreateNodeToolBase = (function (_super) {
    __extends(CreateNodeToolBase, _super);
    function CreateNodeToolBase(parent, dataService) {
        var _this = _super.call(this, parent, dataService) || this;
        _this.parent = parent;
        _this.dataService = dataService;
        _this.color = "primary";
        _this.cursor = 'cell';
        _this.done = false;
        return _this;
    }
    CreateNodeToolBase.prototype.click = function (event) {
        return this.createNewNode({
            x: draggable_element_base_1.DraggableElementBase.roundToGrid(event.offsetX),
            y: draggable_element_base_1.DraggableElementBase.roundToGrid(event.offsetY)
        });
    };
    CreateNodeToolBase.prototype.select = function (element) {
        this.selectedElements[0] = element;
        return Promise.resolve();
    };
    CreateNodeToolBase.prototype.createNewNode = function (coords) {
        var _this = this;
        var node = this.createNode(Id_1.Id.uuid, coords);
        return this.createAndSelect(node).then(function () {
            _this.done = true;
        });
    };
    return CreateNodeToolBase;
}(create_tool_base_1.CreateToolBase));
exports.CreateNodeToolBase = CreateNodeToolBase;
//# sourceMappingURL=create-node-tool-base.js.map