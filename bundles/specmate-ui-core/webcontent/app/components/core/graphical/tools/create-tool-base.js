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
var type_aware_tool_base_1 = require("./type-aware-tool-base");
var CreateToolBase = (function (_super) {
    __extends(CreateToolBase, _super);
    function CreateToolBase(parent, dataService, selectedElementService) {
        var _this = _super.call(this, selectedElementService) || this;
        _this.parent = parent;
        _this.dataService = dataService;
        _this.selectedElements = [];
        return _this;
    }
    CreateToolBase.prototype.activate = function () {
        this.done = false;
        this.selectedElements = [];
    };
    CreateToolBase.prototype.deactivate = function () {
        this.selectedElements = [];
    };
    CreateToolBase.prototype.createAndSelect = function (element) {
        var _this = this;
        return this.dataService.createElement(element, true, Id_1.Id.uuid).then(function () {
            _this.selectedElements = [element];
            _this.selectedElementService.selectedElement = element;
            _this.done = true;
        });
    };
    return CreateToolBase;
}(type_aware_tool_base_1.TypeAwareToolBase));
exports.CreateToolBase = CreateToolBase;
//# sourceMappingURL=create-tool-base.js.map