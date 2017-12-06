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
var id_1 = require("../util/id");
var url_1 = require("../util/url");
var ModelFactoryBase = (function (_super) {
    __extends(ModelFactoryBase, _super);
    function ModelFactoryBase() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    ModelFactoryBase.prototype.create = function (parent, commit, compoundId) {
        var _this = this;
        var element = this.simpleModel;
        element.id = id_1.Id.uuid;
        element.url = url_1.Url.build([parent.url, element.id]);
        element.name = this.name;
        element.description = this.description;
        return this.dataService.createElement(element, true, id_1.Id.uuid)
            .then(function () { return commit ? _this.dataService.commit('Create') : Promise.resolve(); })
            .then(function () { return element; });
    };
    return ModelFactoryBase;
}(element_factory_base_1.ElementFactoryBase));
exports.ModelFactoryBase = ModelFactoryBase;
//# sourceMappingURL=model-factory-base.js.map