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
var Id_1 = require("../../util/Id");
var confirmation_modal_service_1 = require("../../services/notification/confirmation-modal.service");
var Type_1 = require("../../util/Type");
var TestCase_1 = require("../../model/TestCase");
var TestProcedure_1 = require("../../model/TestProcedure");
var specmate_data_service_1 = require("../../services/data/specmate-data.service");
var core_1 = require("@angular/core");
var test_case_component_base_1 = require("./test-case-component-base");
var navigator_service_1 = require("../../services/navigation/navigator.service");
var test_procedure_factory_1 = require("../../factory/test-procedure-factory");
var TestCaseRow = (function (_super) {
    __extends(TestCaseRow, _super);
    /** constructor */
    function TestCaseRow(dataService, modal, navigator) {
        var _this = _super.call(this, dataService) || this;
        _this.modal = modal;
        _this.navigator = navigator;
        return _this;
    }
    Object.defineProperty(TestCaseRow.prototype, "testProcedure", {
        /** Retrieves a test procedure from the test case contents, if none exists, returns undefined */
        get: function () {
            if (!this.contents) {
                return undefined;
            }
            return this.contents.find(function (element) { return Type_1.Type.is(element, TestProcedure_1.TestProcedure); });
        },
        enumerable: true,
        configurable: true
    });
    /** Deletes the test case. */
    TestCaseRow.prototype.delete = function () {
        var _this = this;
        this.modal.open("Do you really want to delete " + this.testCase.name + "?")
            .then(function () { return _this.dataService.deleteElement(_this.testCase.url, true, Id_1.Id.uuid); })
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
    Object.defineProperty(TestCaseRow.prototype, "isVisible", {
        get: function () {
            return Type_1.Type.is(this.testCase, TestCase_1.TestCase);
        },
        enumerable: true,
        configurable: true
    });
    TestCaseRow = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: '[test-case-row]',
            templateUrl: 'test-case-row.component.html'
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService, confirmation_modal_service_1.ConfirmationModal, navigator_service_1.NavigatorService])
    ], TestCaseRow);
    return TestCaseRow;
}(test_case_component_base_1.TestCaseComponentBase));
exports.TestCaseRow = TestCaseRow;
//# sourceMappingURL=test-case-row.component.js.map