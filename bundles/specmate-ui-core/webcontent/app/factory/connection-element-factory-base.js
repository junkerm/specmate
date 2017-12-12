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
var element_factory_base_1 = require("./element-factory-base");
var ConnectionElementFactoryBase = /** @class */ (function (_super) {
    __extends(ConnectionElementFactoryBase, _super);
    function ConnectionElementFactoryBase(source, target, dataService) {
        var _this = _super.call(this, dataService) || this;
        _this.source = source;
        _this.target = target;
        return _this;
    }
    return ConnectionElementFactoryBase;
}(element_factory_base_1.ElementFactoryBase));
exports.ConnectionElementFactoryBase = ConnectionElementFactoryBase;
//# sourceMappingURL=connection-element-factory-base.js.map