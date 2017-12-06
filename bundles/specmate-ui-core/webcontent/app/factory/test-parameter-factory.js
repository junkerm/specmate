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
var TestParameter_1 = require("../model/TestParameter");
var id_1 = require("../util/id");
var url_1 = require("../util/url");
var config_1 = require("../config/config");
var TestCase_1 = require("../model/TestCase");
var parameter_assignment_factory_1 = require("./parameter-assignment-factory");
var TestParameterFactory = /** @class */ (function (_super) {
    __extends(TestParameterFactory, _super);
    function TestParameterFactory() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    TestParameterFactory.prototype.create = function (parent, commit, compoundId) {
        var _this = this;
        compoundId = compoundId || id_1.Id.uuid;
        var id = id_1.Id.uuid;
        var url = url_1.Url.build([parent.url, id]);
        var parameter = new TestParameter_1.TestParameter();
        parameter.name = config_1.Config.TESTPARAMETER_NAME;
        parameter.id = id;
        parameter.url = url;
        parameter.type = this.parameterType;
        parameter.assignments = [];
        return this.dataService.createElement(parameter, true, compoundId)
            .then(function () { return _this.loadContents(parent); })
            .then(function () { return _this.createParameterAssignments(parameter, compoundId); })
            .then(function () { return commit ? _this.dataService.commit('Create') : Promise.resolve(); })
            .then(function () { return parameter; });
    };
    Object.defineProperty(TestParameterFactory.prototype, "testCases", {
        get: function () {
            return this.getContentsOfType(TestCase_1.TestCase);
        },
        enumerable: true,
        configurable: true
    });
    TestParameterFactory.prototype.createParameterAssignments = function (testParameter, compoundId) {
        var _this = this;
        var createParameterAssignmentTask = Promise.resolve();
        var testCases = this.testCases;
        var _loop_1 = function (i) {
            createParameterAssignmentTask = createParameterAssignmentTask.then(function () {
                var currentTestCase = testCases[i];
                var parameterAssignmentFactory = new parameter_assignment_factory_1.ParameterAssignmentFactory(_this.dataService, testParameter);
                return parameterAssignmentFactory.create(currentTestCase, false, compoundId);
            }).then(function () { return Promise.resolve(); });
        };
        for (var i = 0; i < testCases.length; i++) {
            _loop_1(i);
        }
        return createParameterAssignmentTask;
    };
    return TestParameterFactory;
}(element_factory_base_1.ElementFactoryBase));
exports.TestParameterFactory = TestParameterFactory;
//# sourceMappingURL=test-parameter-factory.js.map