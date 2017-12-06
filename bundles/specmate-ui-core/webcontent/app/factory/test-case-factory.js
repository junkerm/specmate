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
var TestCase_1 = require("../model/TestCase");
var id_1 = require("../util/id");
var url_1 = require("../util/url");
var config_1 = require("../config/config");
var TestParameter_1 = require("../model/TestParameter");
var parameter_assignment_factory_1 = require("./parameter-assignment-factory");
var TestCaseFactory = (function (_super) {
    __extends(TestCaseFactory, _super);
    function TestCaseFactory(dataService, preLoadContents) {
        var _this = _super.call(this, dataService) || this;
        _this.preLoadContents = preLoadContents;
        return _this;
    }
    TestCaseFactory.prototype.create = function (parent, commit, compoundId) {
        var _this = this;
        compoundId = compoundId || id_1.Id.uuid;
        var id = id_1.Id.uuid;
        var url = url_1.Url.build([parent.url, id]);
        var testCase = new TestCase_1.TestCase();
        testCase.name = config_1.Config.TESTCASE_NAME;
        testCase.id = id;
        testCase.url = url;
        var preloadTask;
        if (this.preLoadContents) {
            preloadTask = this.loadContents(parent);
        }
        else {
            preloadTask = Promise.resolve().then(function () { return testCase.position = 0; }).then(function () { return _this.contents = []; });
        }
        return preloadTask.then(function (contents) { return testCase.position = _this.otherTestCases.length; })
            .then(function () { return _this.dataService.createElement(testCase, true, compoundId); })
            .then(function () { return _this.createParameterAssignments(testCase, compoundId); })
            .then(function () { return commit ? _this.dataService.commit('Create Test Case') : Promise.resolve(); })
            .then(function () { return testCase; });
    };
    Object.defineProperty(TestCaseFactory.prototype, "otherTestCases", {
        get: function () {
            return this.getContentsOfType(TestCase_1.TestCase);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestCaseFactory.prototype, "testParameters", {
        get: function () {
            return this.getContentsOfType(TestParameter_1.TestParameter);
        },
        enumerable: true,
        configurable: true
    });
    TestCaseFactory.prototype.createParameterAssignments = function (testCase, compoundId) {
        var _this = this;
        var createParameterAssignmentTask = Promise.resolve();
        var testParameters = this.testParameters;
        var _loop_1 = function (i) {
            createParameterAssignmentTask = createParameterAssignmentTask.then(function () {
                var currentTestParameter = testParameters[i];
                var parameterAssignmentFactory = new parameter_assignment_factory_1.ParameterAssignmentFactory(_this.dataService, currentTestParameter);
                return parameterAssignmentFactory.create(testCase, false, compoundId);
            }).then(function () { return Promise.resolve(); });
        };
        for (var i = 0; i < testParameters.length; i++) {
            _loop_1(i);
        }
        return createParameterAssignmentTask;
    };
    return TestCaseFactory;
}(element_factory_base_1.ElementFactoryBase));
exports.TestCaseFactory = TestCaseFactory;
//# sourceMappingURL=test-case-factory.js.map