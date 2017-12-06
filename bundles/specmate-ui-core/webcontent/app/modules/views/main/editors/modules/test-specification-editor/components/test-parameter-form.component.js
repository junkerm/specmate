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
var TestParameter_1 = require("../../../../../../../model/TestParameter");
var url_1 = require("../../../../../../../util/url");
var specmate_data_service_1 = require("../../../../../../data/modules/data-service/services/specmate-data.service");
var id_1 = require("../../../../../../../util/id");
var type_1 = require("../../../../../../../util/type");
var TestCase_1 = require("../../../../../../../model/TestCase");
var TestParameterForm = /** @class */ (function (_super) {
    __extends(TestParameterForm, _super);
    function TestParameterForm(dataService) {
        var _this = _super.call(this) || this;
        _this.dataService = dataService;
        return _this;
    }
    Object.defineProperty(TestParameterForm.prototype, "testParameter", {
        get: function () {
            return this.modelElement;
        },
        set: function (testParameter) {
            var _this = this;
            this.modelElement = testParameter;
            this.dataService.readContents(url_1.Url.parent(this.modelElement.url))
                .then(function (contents) { return _this.testSpecificationContents = contents; })
                .then(function () { return _this.loadParameterAssignments(); });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestParameterForm.prototype, "fields", {
        get: function () {
            return ['name'];
        },
        enumerable: true,
        configurable: true
    });
    TestParameterForm.prototype.deleteParameter = function () {
        var _this = this;
        if (!this.deleteColumnEnabled) {
            return;
        }
        var compoundId = id_1.Id.uuid;
        var deleteParameterAssignmentsTask = Promise.resolve();
        var parameterAssignmentsForParameterUrls = this.testParameter.assignments.map(function (proxy) { return proxy.url; });
        var _loop_1 = function (i) {
            deleteParameterAssignmentsTask = deleteParameterAssignmentsTask.then(function () {
                return _this.dataService.deleteElement(parameterAssignmentsForParameterUrls[i], true, compoundId);
            });
        };
        for (var i = 0; i < parameterAssignmentsForParameterUrls.length; i++) {
            _loop_1(i);
        }
        deleteParameterAssignmentsTask.then(function () { return _this.dataService.deleteElement(_this.testParameter.url, true, compoundId); });
    };
    Object.defineProperty(TestParameterForm.prototype, "testCases", {
        get: function () {
            return this.testSpecificationContents.filter(function (element) { return type_1.Type.is(element, TestCase_1.TestCase); }).map(function (element) { return element; });
        },
        enumerable: true,
        configurable: true
    });
    TestParameterForm.prototype.loadParameterAssignments = function () {
        var _this = this;
        var testCases = this.testCases;
        this.parameterAssignments = [];
        var loadParameterAssignmentsTask = Promise.resolve();
        var _loop_2 = function (i) {
            var currentTestCase = testCases[i];
            loadParameterAssignmentsTask = loadParameterAssignmentsTask.then(function () {
                return _this.dataService.readContents(currentTestCase.url)
                    .then(function (contents) { return contents.forEach(function (element) { return _this.parameterAssignments.push(element); }); });
            });
        };
        for (var i = 0; i < testCases.length; i++) {
            _loop_2(i);
        }
        return loadParameterAssignmentsTask;
    };
    Object.defineProperty(TestParameterForm.prototype, "testParameters", {
        get: function () {
            if (!this.testSpecificationContents) {
                return undefined;
            }
            return this.testSpecificationContents.filter(function (element) { return type_1.Type.is(element, TestParameter_1.TestParameter); }).map(function (element) { return element; });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestParameterForm.prototype, "deleteColumnEnabled", {
        /** returns true if deletion of columns is allowed */
        get: function () {
            if (!this.testParameters) {
                return false;
            }
            return this.testParameters.length > 1;
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        core_1.Input(),
        __metadata("design:type", TestParameter_1.TestParameter),
        __metadata("design:paramtypes", [TestParameter_1.TestParameter])
    ], TestParameterForm.prototype, "testParameter", null);
    TestParameterForm = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'test-parameter-form',
            templateUrl: 'test-parameter-form.component.html',
            styleUrls: ['test-parameter-form.component.css']
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService])
    ], TestParameterForm);
    return TestParameterForm;
}(simple_input_form_base_1.SimpleInputFormBase));
exports.TestParameterForm = TestParameterForm;
//# sourceMappingURL=test-parameter-form.component.js.map