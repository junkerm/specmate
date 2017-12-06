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
var core_1 = require("@angular/core");
var simple_input_form_base_1 = require("../../../../../../forms/modules/generic-form/base/simple-input-form-base");
var TestStep_1 = require("../../../../../../../model/TestStep");
var url_1 = require("../../../../../../../util/url");
var type_1 = require("../../../../../../../util/type");
var TestParameter_1 = require("../../../../../../../model/TestParameter");
var proxy_1 = require("../../../../../../../model/support/proxy");
var ParameterAssignment_1 = require("../../../../../../../model/ParameterAssignment");
var id_1 = require("../../../../../../../util/id");
var specmate_data_service_1 = require("../../../../../../data/modules/data-service/services/specmate-data.service");
var TestStepRow = (function (_super) {
    __extends(TestStepRow, _super);
    function TestStepRow(dataService) {
        var _this = _super.call(this) || this;
        _this.dataService = dataService;
        return _this;
    }
    Object.defineProperty(TestStepRow.prototype, "testStep", {
        get: function () {
            return this.modelElement;
        },
        set: function (testStep) {
            var _this = this;
            var testStepUrl = testStep.url;
            var testProcedureUrl = url_1.Url.parent(testStepUrl);
            var testCaseUrl = url_1.Url.parent(testProcedureUrl);
            var testSpecificationUrl = url_1.Url.parent(testCaseUrl);
            this.dataService.readContents(testSpecificationUrl)
                .then(function (contents) { return _this.testSpecificationContents = contents; })
                .then(function () { return _this.dataService.readContents(testProcedureUrl); })
                .then(function (contents) { return _this.testProcedureContents = contents; })
                .then(function () { return _this.modelElement = testStep; });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestStepRow.prototype, "fields", {
        get: function () {
            return ['description', 'expectedOutcome'];
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestStepRow.prototype, "testSteps", {
        get: function () {
            if (!this.testProcedureContents) {
                return undefined;
            }
            return this.testProcedureContents.filter(function (element) { return type_1.Type.is(element, TestStep_1.TestStep); }).map(function (element) { return element; });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestStepRow.prototype, "testParameters", {
        get: function () {
            if (!this.testSpecificationContents) {
                return undefined;
            }
            return this.testSpecificationContents.filter(function (element) { return type_1.Type.is(element, TestParameter_1.TestParameter); }).map(function (element) { return element; });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestStepRow.prototype, "parameterAssignments", {
        get: function () {
            if (!this.testSpecificationContents) {
                return undefined;
            }
            return this.testSpecificationContents.filter(function (element) { return type_1.Type.is(element, ParameterAssignment_1.ParameterAssignment); }).map(function (element) { return element; });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestStepRow.prototype, "selectedTestParameter", {
        get: function () {
            var _this = this;
            if (!this.testStep || !this.testStep.referencedTestParameters || this.testStep.referencedTestParameters.length <= 0) {
                return undefined;
            }
            return this.testParameters.find(function (testParameter) { return testParameter.url === _this.testStep.referencedTestParameters[0].url; });
        },
        set: function (testParameter) {
            if (!testParameter) {
                this.testStep.referencedTestParameters = [];
                this.dataService.updateElement(this.testStep, true, id_1.Id.uuid);
                return;
            }
            if (!this.testStep.referencedTestParameters) {
                this.testStep.referencedTestParameters = [];
            }
            this.testStep.referencedTestParameters[0] = new proxy_1.Proxy();
            this.testStep.referencedTestParameters[0].url = testParameter.url;
            this.dataService.updateElement(this.testStep, true, id_1.Id.uuid);
        },
        enumerable: true,
        configurable: true
    });
    TestStepRow.prototype.delete = function () {
        var _this = this;
        var compoundId = id_1.Id.uuid;
        this.dataService.deleteElement(this.testStep.url, true, compoundId)
            .then(function () { return _this.dataService.sanitizeContentPositions(_this.testSteps, true, compoundId); });
    };
    TestStepRow.prototype.getTestParameter = function (url) {
        if (!this.testParameters) {
            return undefined;
        }
        return this.testParameters.find(function (testParameter) { return testParameter.url === url; });
    };
    TestStepRow.prototype.getParameterAssignment = function (testParameter) {
        if (!this.parameterAssignments) {
            return undefined;
        }
        return this.parameterAssignments.find(function (parameterAssignment) { return parameterAssignment.parameter.url === testParameter.url; });
    };
    TestStepRow.prototype.getPosition = function (testStep) {
        return parseInt(String(testStep.position)) + 1;
    };
    __decorate([
        core_1.Input(),
        __metadata("design:type", TestStep_1.TestStep),
        __metadata("design:paramtypes", [TestStep_1.TestStep])
    ], TestStepRow.prototype, "testStep", null);
    TestStepRow = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: '[test-step-row]',
            templateUrl: 'test-step-row.component.html'
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService])
    ], TestStepRow);
    return TestStepRow;
}(simple_input_form_base_1.SimpleInputFormBase));
exports.TestStepRow = TestStepRow;
//# sourceMappingURL=test-step-row.component.js.map