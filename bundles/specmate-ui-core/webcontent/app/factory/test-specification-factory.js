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
var TestSpecification_1 = require("../model/TestSpecification");
var id_1 = require("../util/id");
var url_1 = require("../util/url");
var config_1 = require("../config/config");
var test_case_factory_1 = require("./test-case-factory");
var TestSpecificationFactory = /** @class */ (function (_super) {
    __extends(TestSpecificationFactory, _super);
    function TestSpecificationFactory() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    TestSpecificationFactory.prototype.create = function (parent, commit, compoundId) {
        var _this = this;
        compoundId = compoundId || id_1.Id.uuid;
        var testSpec = new TestSpecification_1.TestSpecification();
        testSpec.id = id_1.Id.uuid;
        testSpec.url = url_1.Url.build([parent.url, testSpec.id]);
        testSpec.name = config_1.Config.TESTSPEC_NAME;
        testSpec.description = config_1.Config.TESTSPEC_DESCRIPTION;
        return this.dataService.createElement(testSpec, true, compoundId)
            .then(function () { return _this.createTestCase(testSpec); })
            .then(function () { return commit ? _this.dataService.commit('Create') : Promise.resolve(); })
            .then(function () { return testSpec; });
    };
    TestSpecificationFactory.prototype.createTestCase = function (testSpec) {
        var factory = new test_case_factory_1.TestCaseFactory(this.dataService, false);
        return factory.create(testSpec, false);
    };
    return TestSpecificationFactory;
}(element_factory_base_1.ElementFactoryBase));
exports.TestSpecificationFactory = TestSpecificationFactory;
//# sourceMappingURL=test-specification-factory.js.map