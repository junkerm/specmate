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
var TestStep_1 = require("../model/TestStep");
var Id_1 = require("../util/Id");
var Url_1 = require("../util/Url");
var config_1 = require("../config/config");
var Type_1 = require("../util/Type");
var TestStepFactory = (function (_super) {
    __extends(TestStepFactory, _super);
    function TestStepFactory() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    TestStepFactory.prototype.create = function (parent, commit, compoundId) {
        var _this = this;
        compoundId = compoundId || Id_1.Id.uuid;
        var id = Id_1.Id.uuid;
        var url = Url_1.Url.build([parent.url, id]);
        var position = this.contents ? this.contents.length : 0;
        var testStep = new TestStep_1.TestStep();
        testStep.name = config_1.Config.TESTSTEP_NAME;
        testStep.description = config_1.Config.TESTSTEP_ACTION;
        testStep.expectedOutcome = config_1.Config.TESTSTEP_EXPECTED_OUTCOME;
        testStep.id = id;
        testStep.url = url;
        testStep.position = position;
        testStep.referencedTestParameters = [];
        return this.dataService.readContents(parent.url)
            .then(function (contents) { return testStep.position = contents.filter(function (element) { return Type_1.Type.is(element, TestStep_1.TestStep); }).length; })
            .then(function () { return _this.dataService.createElement(testStep, true, compoundId); })
            .then(function () { return commit ? _this.dataService.commit('Save') : Promise.resolve(); })
            .then(function () { return testStep; });
    };
    return TestStepFactory;
}(element_factory_base_1.ElementFactoryBase));
exports.TestStepFactory = TestStepFactory;
//# sourceMappingURL=test-step-factory.js.map