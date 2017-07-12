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
var config_1 = require('../../config/config');
var confirmation_modal_service_1 = require('../core/forms/confirmation-modal.service');
var Type_1 = require('../../util/Type');
var ParameterAssignment_1 = require('../../model/ParameterAssignment');
var TestCase_1 = require('../../model/TestCase');
var TestProcedure_1 = require('../../model/TestProcedure');
var specmate_data_service_1 = require('../../services/specmate-data.service');
var core_1 = require('@angular/core');
var router_1 = require('@angular/router');
var Url_1 = require('../../util/Url');
var angular2_uuid_1 = require('angular2-uuid');
var TestCaseRow = (function () {
    /** constructor */
    function TestCaseRow(dataService, router, route, modal) {
        this.dataService = dataService;
        this.router = router;
        this.route = route;
        this.modal = modal;
    }
    Object.defineProperty(TestCaseRow.prototype, "testProcedure", {
        /** Retrieves a test procedure from the test case contents, if no exists, returns undefined */
        get: function () {
            return this.contents.find(function (c) { return Type_1.Type.is(c, TestProcedure_1.TestProcedure); });
        },
        enumerable: true,
        configurable: true
    });
    TestCaseRow.prototype.ngOnInit = function () {
        this.loadContents();
    };
    /** We initialize the assignments here. */
    TestCaseRow.prototype.loadContents = function (virtual) {
        var _this = this;
        this.dataService.readContents(this.testCase.url, virtual).then(function (contents) {
            _this.contents = contents;
            _this.assignments = contents.filter(function (c) { return Type_1.Type.is(c, ParameterAssignment_1.ParameterAssignment); }).map(function (c) { return c; });
            _this.assignmentMap = _this.deriveAssignmentMap(_this.assignments);
        });
    };
    /** Derives the parameter assignments matching to the display parameters in the right order */
    TestCaseRow.prototype.deriveAssignmentMap = function (assignments) {
        var assignmentMap = {};
        for (var _i = 0, _a = this.assignments; _i < _a.length; _i++) {
            var assignment = _a[_i];
            assignmentMap[assignment.parameter.url] = assignment;
        }
        return assignmentMap;
    };
    /** Deletes the test case. */
    TestCaseRow.prototype.delete = function () {
        var _this = this;
        this.modal.open("Do you really want to delete " + this.testCase.name + "?")
            .then(function () { return _this.dataService.deleteElement(_this.testCase.url, true); })
            .catch(function () { });
    };
    /** Asks for confirmation to save all change, creates a new test procedure and then navigates to it. */
    TestCaseRow.prototype.createTestProcedure = function () {
        var _this = this;
        if (this.dataService.hasCommits) {
            this.modal.open("To create a new test procedure, the test specification has to saved. " +
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
        var id = this.getNewTestProcedureId();
        var url = Url_1.Url.build([this.testCase.url, id]);
        var testProcedure = new TestProcedure_1.TestProcedure();
        testProcedure.name = config_1.Config.TESTPROCEDURE_NAME;
        testProcedure.id = id;
        testProcedure.url = url;
        this.dataService.createElement(testProcedure, true).then(function () {
            _this.dataService.commit("new Test Procedure").then(function () {
                return _this.router.navigate(['/tests', { outlets: { 'main': [url, 'tpe'] } }]);
            });
        });
    };
    /** Creates a new ID for a test procedure */
    TestCaseRow.prototype.getNewTestProcedureId = function () {
        return angular2_uuid_1.UUID.UUID();
    };
    __decorate([
        core_1.Input(), 
        __metadata('design:type', TestCase_1.TestCase)
    ], TestCaseRow.prototype, "testCase", void 0);
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Array)
    ], TestCaseRow.prototype, "inputParameters", void 0);
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Array)
    ], TestCaseRow.prototype, "outputParameters", void 0);
    TestCaseRow = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: '[test-case-row]',
            templateUrl: 'test-case-row.component.html'
        }), 
        __metadata('design:paramtypes', [specmate_data_service_1.SpecmateDataService, router_1.Router, router_1.ActivatedRoute, confirmation_modal_service_1.ConfirmationModal])
    ], TestCaseRow);
    return TestCaseRow;
}());
exports.TestCaseRow = TestCaseRow;
//# sourceMappingURL=test-case-row.component.js.map