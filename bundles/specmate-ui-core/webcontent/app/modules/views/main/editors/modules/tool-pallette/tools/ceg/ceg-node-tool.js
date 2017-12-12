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
var create_node_tool_base_1 = require("../../tools/create-node-tool-base");
var CEGModel_1 = require("../../../../../../../../model/CEGModel");
var ceg_node_factory_1 = require("../../../../../../../../factory/ceg-node-factory");
var CEGNodeTool = /** @class */ (function (_super) {
    __extends(CEGNodeTool, _super);
    function CEGNodeTool(parent, dataService, selectedElementService) {
        var _this = _super.call(this, parent, dataService, selectedElementService) || this;
        _this.modelType = CEGModel_1.CEGModel;
        _this.name = 'Add Node';
        _this.icon = 'plus';
        return _this;
    }
    CEGNodeTool.prototype.getElementFactory = function (coords) {
        return new ceg_node_factory_1.CEGNodeFactory(coords, this.dataService);
    };
    return CEGNodeTool;
}(create_node_tool_base_1.CreateNodeToolBase));
exports.CEGNodeTool = CEGNodeTool;
//# sourceMappingURL=ceg-node-tool.js.map