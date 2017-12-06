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
var TestProcedure_1 = require("../model/TestProcedure");
var id_1 = require("../util/id");
var url_1 = require("../util/url");
var config_1 = require("../config/config");
var test_step_factory_1 = require("./test-step-factory");
var TestProcedureFactory = /** @class */ (function (_super) {
    __extends(TestProcedureFactory, _super);
    function TestProcedureFactory() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    TestProcedureFactory.prototype.create = function (parent, commit, compoundId) {
        var _this = this;
        compoundId = compoundId || id_1.Id.uuid;
        var id = id_1.Id.uuid;
        var url = url_1.Url.build([parent.url, id]);
        var testProcedure = new TestProcedure_1.TestProcedure();
        testProcedure.name = config_1.Config.TESTPROCEDURE_NAME;
        testProcedure.description = config_1.Config.TESTPROCEDURE_DESCRIPTION;
        testProcedure.id = id;
        testProcedure.url = url;
        testProcedure.isRegressionTest = false;
        return this.dataService.createElement(testProcedure, true, compoundId)
            .then(function () { return _this.createTestCase(testProcedure, compoundId); })
            .then(function () { return commit ? _this.dataService.commit("Create") : Promise.resolve(); })
            .then(function () { return testProcedure; });
    };
    TestProcedureFactory.prototype.createTestCase = function (testProcedure, compoundId) {
        var factory = new test_step_factory_1.TestStepFactory(this.dataService);
        return factory.create(testProcedure, false, compoundId);
    };
    return TestProcedureFactory;
}(element_factory_base_1.ElementFactoryBase));
exports.TestProcedureFactory = TestProcedureFactory;
//# sourceMappingURL=test-procedure-factory.js.map