"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var type_1 = require("../util/type");
var ElementFactoryBase = /** @class */ (function () {
    function ElementFactoryBase(dataService) {
        this.dataService = dataService;
    }
    ElementFactoryBase.prototype.loadContents = function (parent) {
        var _this = this;
        return this.dataService.readContents(parent.url, true).then(function (contents) { return _this.contents = contents; });
    };
    ElementFactoryBase.prototype.getContentsOfType = function (type) {
        return this.contents.filter(function (element) { return type_1.Type.is(element, type); });
    };
    return ElementFactoryBase;
}());
exports.ElementFactoryBase = ElementFactoryBase;
//# sourceMappingURL=element-factory-base.js.map