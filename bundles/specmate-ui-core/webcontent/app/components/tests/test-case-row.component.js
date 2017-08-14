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
var config_1 = require("../../config/config");
var confirmation_modal_service_1 = require("../core/forms/confirmation-modal.service");
var Type_1 = require("../../util/Type");
var TestProcedure_1 = require("../../model/TestProcedure");
var specmate_data_service_1 = require("../../services/specmate-data.service");
var core_1 = require("@angular/core");
var Url_1 = require("../../util/Url");
var test_case_component_base_1 = require("./test-case-component-base");
var navigator_service_1 = require("../../services/navigator.service");
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
        /** Retrieves a test procedure from the test case contents, if no exists, returns undefined */
        get: function () {
            return this.contents.find(function (c) { return Type_1.Type.is(c, TestProcedure_1.TestProcedure); });
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
        if (this.dataService.hasCommits) {
            this.modal.open("To create a new test procedure, the test specification has to be saved. " +
                "Do you want to save now and create a new test procedure, or do you want to abort?")
                .then(function () { return _this.dataService.commit("Save Test Specification"); })
                .then(function () { return _this.doCreateTestProcedure(); });
        }
        else {
            this.doCreateTestProcedure();
        }
    };
    /** Creates a new test procedure and navigates to the new test procedure. */
    TestCaseRow.prototype.doCreateTestProcedure = function () {
        var _this = this;
        var id = Id_1.Id.uuid;
        var url = Url_1.Url.build([this.testCase.url, id]);
        var testProcedure = new TestProcedure_1.TestProcedure();
        testProcedure.name = config_1.Config.TESTPROCEDURE_NAME;
        testProcedure.id = id;
        testProcedure.url = url;
        testProcedure.isRegressionTest = false;
        this.dataService.createElement(testProcedure, true, Id_1.Id.uuid).then(function () {
            return _this.dataService.commit("Create");
        }).then(function () {
            return _this.navigator.navigate(testProcedure);
        });
    };
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