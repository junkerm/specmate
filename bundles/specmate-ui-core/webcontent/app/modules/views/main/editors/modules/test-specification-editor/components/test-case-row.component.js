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
var core_1 = require("@angular/core");
var TestCase_1 = require("../../../../../../../model/TestCase");
var type_1 = require("../../../../../../../util/type");
var url_1 = require("../../../../../../../util/url");
var specmate_data_service_1 = require("../../../../../../data/modules/data-service/services/specmate-data.service");
var confirmation_modal_service_1 = require("../../../../../../notification/modules/modals/services/confirmation-modal.service");
var navigator_service_1 = require("../../../../../../navigation/modules/navigator/services/navigator.service");
var TestProcedure_1 = require("../../../../../../../model/TestProcedure");
var test_procedure_factory_1 = require("../../../../../../../factory/test-procedure-factory");
var TestParameter_1 = require("../../../../../../../model/TestParameter");
var id_1 = require("../../../../../../../util/id");
var ParameterAssignment_1 = require("../../../../../../../model/ParameterAssignment");
var TestCaseRow = /** @class */ (function () {
    function TestCaseRow(dataService, modal, navigator) {
        this.dataService = dataService;
        this.modal = modal;
        this.navigator = navigator;
    }
    Object.defineProperty(TestCaseRow.prototype, "testCase", {
        get: function () {
            return this._testCase;
        },
        set: function (testCase) {
            var _this = this;
            if (!type_1.Type.is(testCase, TestCase_1.TestCase)) {
                return;
            }
            this._testCase = testCase;
            this.dataService.readContents(this.testCase.url)
                .then(function (contents) { return _this.contents = contents; })
                .then(function () { return _this.dataService.readContents(url_1.Url.parent(_this.testCase.url)); })
                .then(function (contents) { return _this.testSpecificationContents = contents; });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestCaseRow.prototype, "testProcedure", {
        /** Retrieves a test procedure from the test case contents, if none exists, returns undefined */
        get: function () {
            if (!this.contents) {
                return undefined;
            }
            return this.contents.find(function (element) { return type_1.Type.is(element, TestProcedure_1.TestProcedure); });
        },
        enumerable: true,
        configurable: true
    });
    /** Deletes the test case. */
    TestCaseRow.prototype.delete = function () {
        var _this = this;
        this.modal.open('Do you really want to delete ' + this.testCase.name + '?')
            .then(function () { return _this.dataService.deleteElement(_this.testCase.url, true, id_1.Id.uuid); })
            .catch(function () { });
    };
    /** Asks for confirmation to save all change, creates a new test procedure and then navigates to it. */
    TestCaseRow.prototype.createTestProcedure = function () {
        var _this = this;
        var factory = new test_procedure_factory_1.TestProcedureFactory(this.dataService);
        this.modal.confirmSave()
            .then(function () { return factory.create(_this.testCase, true); })
            .then(function (testProcedure) { return _this.navigator.navigate(testProcedure); })
            .catch(function () { });
    };
    Object.defineProperty(TestCaseRow.prototype, "testParameters", {
        get: function () {
            if (!this.testSpecificationContents) {
                return undefined;
            }
            return this.testSpecificationContents
                .filter(function (element) { return type_1.Type.is(element, TestParameter_1.TestParameter); })
                .map(function (element) { return element; });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestCaseRow.prototype, "inputParameters", {
        get: function () {
            if (!this.testParameters) {
                return undefined;
            }
            var parameters = this.testParameters.filter(function (element) { return element.type === 'INPUT'; });
            return parameters;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestCaseRow.prototype, "outputParameters", {
        get: function () {
            if (!this.testParameters) {
                return undefined;
            }
            var parameters = this.testParameters.filter(function (element) { return element.type === 'OUTPUT'; });
            return parameters;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestCaseRow.prototype, "assignments", {
        get: function () {
            if (!this.contents) {
                return undefined;
            }
            return this.contents
                .filter(function (element) { return type_1.Type.is(element, ParameterAssignment_1.ParameterAssignment); })
                .map(function (element) { return element; });
        },
        enumerable: true,
        configurable: true
    });
    TestCaseRow.prototype.getAssignment = function (testParameter) {
        if (!this.assignments) {
            return undefined;
        }
        return this.assignments.find(function (paramAssignment) { return paramAssignment.parameter.url === testParameter.url; });
    };
    Object.defineProperty(TestCaseRow.prototype, "isVisible", {
        get: function () {
            return type_1.Type.is(this.testCase, TestCase_1.TestCase);
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        core_1.Input(),
        __metadata("design:type", TestCase_1.TestCase),
        __metadata("design:paramtypes", [TestCase_1.TestCase])
    ], TestCaseRow.prototype, "testCase", null);
    TestCaseRow = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: '[test-case-row]',
            templateUrl: 'test-case-row.component.html'
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService, confirmation_modal_service_1.ConfirmationModal, navigator_service_1.NavigatorService])
    ], TestCaseRow);
    return TestCaseRow;
}());
exports.TestCaseRow = TestCaseRow;
//# sourceMappingURL=test-case-row.component.js.map