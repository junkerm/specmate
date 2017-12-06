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
var positionable_element_factory_base_1 = require("./positionable-element-factory-base");
var ProcessStep_1 = require("../model/ProcessStep");
var id_1 = require("../util/id");
var url_1 = require("../util/url");
var config_1 = require("../config/config");
var ProcessStepFactory = (function (_super) {
    __extends(ProcessStepFactory, _super);
    function ProcessStepFactory() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    ProcessStepFactory.prototype.create = function (parent, commit, compoundId) {
        compoundId = compoundId || id_1.Id.uuid;
        var id = id_1.Id.uuid;
        var url = url_1.Url.build([parent.url, id]);
        var node = new ProcessStep_1.ProcessStep();
        node.name = config_1.Config.PROCESS_NEW_STEP_NAME;
        node.description = config_1.Config.PROCESS_NEW_STEP_DESCRIPTION;
        node.id = id;
        node.url = url;
        node.x = this.coords.x;
        node.y = this.coords.y;
        return this.dataService.createElement(node, true, compoundId).then(function () { return node; });
    };
    return ProcessStepFactory;
}(positionable_element_factory_base_1.PositionableElementFactoryBase));
exports.ProcessStepFactory = ProcessStepFactory;
//# sourceMappingURL=process-step-factory.js.map