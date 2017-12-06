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
var PositionableElementFactoryBase = /** @class */ (function (_super) {
    __extends(PositionableElementFactoryBase, _super);
    function PositionableElementFactoryBase(coords, dataService) {
        var _this = _super.call(this, dataService) || this;
        _this.coords = coords;
        return _this;
    }
    return PositionableElementFactoryBase;
}(element_factory_base_1.ElementFactoryBase));
exports.PositionableElementFactoryBase = PositionableElementFactoryBase;
//# sourceMappingURL=positionable-element-factory-base.js.map