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
var connection_tool_base_1 = require("../../tools/connection-tool-base");
var CEGModel_1 = require("../../../../../../../../model/CEGModel");
var ceg_connection_factory_1 = require("../../../../../../../../factory/ceg-connection-factory");
var CEGConnectionTool = /** @class */ (function (_super) {
    __extends(CEGConnectionTool, _super);
    function CEGConnectionTool() {
        var _this = _super !== null && _super.apply(this, arguments) || this;
        _this.modelType = CEGModel_1.CEGModel;
        _this.name = 'Add Connection';
        _this.icon = 'sitemap';
        return _this;
    }
    CEGConnectionTool.prototype.getFactory = function (e1, e2) {
        return new ceg_connection_factory_1.CEGConnectionFactory(e1, e2, this.dataService);
    };
    return CEGConnectionTool;
}(connection_tool_base_1.ConnectionToolBase));
exports.CEGConnectionTool = CEGConnectionTool;
//# sourceMappingURL=ceg-connection-tool.js.map