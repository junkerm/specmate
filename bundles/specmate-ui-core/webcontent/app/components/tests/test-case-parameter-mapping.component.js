"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
Object.defineProperty(exports, "__esModule", { value: true });
var ParameterAssignment_1 = require("../../model/ParameterAssignment");
var TestParameter_1 = require("../../model/TestParameter");
var Type_1 = require("../../util/Type");
var TestStep_1 = require("../../model/TestStep");
var specmate_data_service_1 = require("../../services/data/specmate-data.service");
var core_1 = require("@angular/core");
var TestProcedure_1 = require("../../model/TestProcedure");
var Url_1 = require("../../util/Url");
var TestCaseParameterMapping = (function () {
    function TestCaseParameterMapping(dataService) {
        this.dataService = dataService;
    }
    Object.defineProperty(TestCaseParameterMapping.prototype, "testProcedure", {
        get: function () {
            return this._testProcedure;
        },
        set: function (testProcedure) {
            if (!testProcedure) {
                return;
            }
            this._testProcedure = testProcedure;
            this.initContents();
        },
        enumerable: true,
        configurable: true
    });
    TestCaseParameterMapping.prototype.initContents = function () {
        var _this = this;
        this.dataService.readContents(this.testProcedure.url)
            .then(function (contents) { return _this.testProcedureContents = contents; })
            .then(function () { return _this.dataService.readElement(Url_1.Url.parent(_this.testProcedure.url)); })
            .then(function (element) { return _this.testCase = element; })
            .then(function () { return _this.dataService.readContents(_this.testCase.url); })
            .then(function (contents) { return _this.testCaseContents = contents; })
            .then(function () { return _this.dataService.readElement(Url_1.Url.parent(_this.testCase.url)); })
            .then(function (element) { return _this.testSpecification = element; })
            .then(function () { return _this.dataService.readContents(_this.testSpecification.url); })
            .then(function (contents) { return _this.testSpecificationContents = contents; });
    };
    TestCaseParameterMapping.prototype.getTestParametersOfType = function (type) {
        if (!this.testParameters) {
            return undefined;
        }
        return this.filterEmptyParameterAssignments(this.testParameters.filter(function (testParameter) { return testParameter.type === type; }));
    };
    Object.defineProperty(TestCaseParameterMapping.prototype, "inputParameters", {
        get: function () {
            return this.getTestParametersOfType('INPUT');
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestCaseParameterMapping.prototype, "outputParameters", {
        get: function () {
            return this.getTestParametersOfType('OUTPUT');
        },
        enumerable: true,
        configurable: true
    });
    TestCaseParameterMapping.prototype.filterEmptyParameterAssignments = function (params) {
        var _this = this;
        return params.filter(function (param) {
            var paramAssignment = _this.getAssignment(param);
            return paramAssignment && paramAssignment.condition && paramAssignment.condition !== '';
        });
    };
    TestCaseParameterMapping.prototype.referencingTestSteps = function (testParameter) {
        if (!this.testProcedureContents) {
            return [];
        }
        return this.testProcedureContents
            .filter(function (element) { return Type_1.Type.is(element, TestStep_1.TestStep); })
            .filter(function (testStep) { return testStep.referencedTestParameters.findIndex(function (proxy) { return proxy.url === testParameter.url; }) >= 0; });
    };
    Object.defineProperty(TestCaseParameterMapping.prototype, "testParameters", {
        get: function () {
            if (!this.testSpecificationContents) {
                return undefined;
            }
            return this.testSpecificationContents.filter(function (element) { return Type_1.Type.is(element, TestParameter_1.TestParameter); }).map(function (element) { return element; });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestCaseParameterMapping.prototype, "assignments", {
        get: function () {
            if (!this.testCaseContents) {
                return undefined;
            }
            return this.testCaseContents.filter(function (element) { return Type_1.Type.is(element, ParameterAssignment_1.ParameterAssignment); }).map(function (element) { return element; });
        },
        enumerable: true,
        configurable: true
    });
    TestCaseParameterMapping.prototype.getAssignment = function (testParameter) {
        if (!this.assignments) {
            return undefined;
        }
        return this.assignments.find(function (paramAssignment) { return paramAssignment.parameter.url === testParameter.url; });
    };
    TestCaseParameterMapping.prototype.getStepNumber = function (testStep) {
        return parseInt(String(testStep.position)) + 1;
    };
    __decorate([
        core_1.Input(),
        __metadata("design:type", TestProcedure_1.TestProcedure),
        __metadata("design:paramtypes", [TestProcedure_1.TestProcedure])
    ], TestCaseParameterMapping.prototype, "testProcedure", null);
    TestCaseParameterMapping = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'test-case-parameter-mapping',
            templateUrl: 'test-case-parameter-mapping.component.html'
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService])
    ], TestCaseParameterMapping);
    return TestCaseParameterMapping;
}());
exports.TestCaseParameterMapping = TestCaseParameterMapping;
//# sourceMappingURL=test-case-parameter-mapping.component.js.map