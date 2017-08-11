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
var confirmation_modal_service_1 = require("../core/forms/confirmation-modal.service");
var navigator_service_1 = require("../../services/navigator.service");
var router_1 = require("@angular/router");
var test_case_row_component_1 = require("./test-case-row.component");
var proxy_1 = require("../../model/support/proxy");
var ParameterAssignment_1 = require("../../model/ParameterAssignment");
var Id_1 = require("../../util/Id");
var config_1 = require("../../config/config");
var generic_form_component_1 = require("../core/forms/generic-form.component");
var CEGModel_1 = require("../../model/CEGModel");
var Type_1 = require("../../util/Type");
var TestParameter_1 = require("../../model/TestParameter");
var TestCase_1 = require("../../model/TestCase");
var Url_1 = require("../../util/Url");
var specmate_data_service_1 = require("../../services/specmate-data.service");
var Requirement_1 = require("../../model/Requirement");
var core_1 = require("@angular/core");
var editor_common_control_service_1 = require("../../services/editor-common-control.service");
var specmate_view_base_1 = require("../core/views/specmate-view-base");
var TestSpecificationEditor = (function (_super) {
    __extends(TestSpecificationEditor, _super);
    /** Constructor */
    function TestSpecificationEditor(dataService, navigator, route, modal, editorCommonControlService) {
        var _this = _super.call(this, dataService, navigator, route, modal, editorCommonControlService) || this;
        /** The type of a test case (used for filtering) */
        _this.testCaseType = TestCase_1.TestCase;
        /** The type of a test parameter (used for filtering) */
        _this.parameterType = TestParameter_1.TestParameter;
        return _this;
    }
    TestSpecificationEditor.prototype.onElementResolved = function (element) {
        this.testSpecification = element;
        this.readContents();
        this.readParents();
    };
    Object.defineProperty(TestSpecificationEditor.prototype, "inputParameters", {
        /** getter for the input parameters */
        get: function () {
            return this.contents.filter(function (c) {
                return Type_1.Type.is(c, TestParameter_1.TestParameter) && c.type === "INPUT";
            });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestSpecificationEditor.prototype, "outputParameters", {
        /** getter for the output parameters */
        get: function () {
            return this.contents.filter(function (c) {
                return Type_1.Type.is(c, TestParameter_1.TestParameter) && c.type === "OUTPUT";
            });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestSpecificationEditor.prototype, "allParameters", {
        /** getter for all parameters */
        get: function () {
            return this.inputParameters.concat(this.outputParameters);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestSpecificationEditor.prototype, "testCases", {
        /** getter for the test cases */
        get: function () {
            return this.contents.filter(function (c) {
                return Type_1.Type.is(c, TestCase_1.TestCase);
            });
        },
        enumerable: true,
        configurable: true
    });
    /** Reads to the contents of the test specification  */
    TestSpecificationEditor.prototype.readContents = function () {
        var _this = this;
        if (this.testSpecification) {
            this.dataService.readContents(this.testSpecification.url).then(function (contents) {
                _this.contents = contents;
            });
        }
    };
    /** Reads the CEG and requirements parents of the test specficiation */
    TestSpecificationEditor.prototype.readParents = function () {
        var _this = this;
        if (this.testSpecification) {
            this.dataService.readElement(Url_1.Url.parent(this.testSpecification.url)).then(function (element) {
                if (Type_1.Type.is(element, CEGModel_1.CEGModel)) {
                    _this.cegModel = element;
                    _this.readCEGParent();
                }
                else if (Type_1.Type.is(element, Requirement_1.Requirement)) {
                    _this.requirement = element;
                }
            });
        }
    };
    /** Reads the requirement parent of the CEG model */
    TestSpecificationEditor.prototype.readCEGParent = function () {
        var _this = this;
        if (this.cegModel) {
            this.dataService.readElement(Url_1.Url.parent(this.cegModel.url)).then(function (element) {
                if (Type_1.Type.is(element, Requirement_1.Requirement)) {
                    _this.requirement = element;
                }
            });
        }
    };
    /** Creates a new test paramter */
    TestSpecificationEditor.prototype.createNewTestParameter = function () {
        var id = Id_1.Id.uuid;
        var url = Url_1.Url.build([this.testSpecification.url, id]);
        var parameter = new TestParameter_1.TestParameter();
        parameter.name = config_1.Config.TESTPARAMETER_NAME;
        parameter.id = id;
        parameter.url = url;
        return parameter;
    };
    /** Adds a new input column */
    TestSpecificationEditor.prototype.addInputColumn = function () {
        this.addColumn("INPUT");
    };
    /** Adds a new output column  */
    TestSpecificationEditor.prototype.addOutputColumn = function () {
        this.addColumn("OUTPUT");
    };
    /** Adds a new Column. Values for type are 'OUTPUT' and 'INPUT'. */
    TestSpecificationEditor.prototype.addColumn = function (type) {
        var _this = this;
        var compoundId = Id_1.Id.uuid;
        var parameter = this.createNewTestParameter();
        parameter.type = type;
        this.dataService.createElement(parameter, true, compoundId);
        var createParameterAssignmentTask = Promise.resolve();
        this.testCases.forEach(function (testCase) {
            createParameterAssignmentTask = createParameterAssignmentTask.then(function () {
                return _this.createNewParameterAssignment(testCase, parameter, compoundId).then(function () {
                    _this.testCaseRows.find(function (testCaseRow) { return testCaseRow.testCase === testCase; }).loadContents(true);
                });
            });
        });
        createParameterAssignmentTask.then(function () { });
    };
    /** Creates a new test case */
    TestSpecificationEditor.prototype.createNewTestCase = function () {
        var _this = this;
        var id = Id_1.Id.uuid;
        var url = Url_1.Url.build([this.testSpecification.url, id]);
        var testCase = new TestCase_1.TestCase();
        testCase.name = config_1.Config.TESTCASE_NAME;
        testCase.id = id;
        testCase.url = url;
        var compoundId = Id_1.Id.uuid;
        this.dataService.createElement(testCase, true, compoundId).then(function () {
            var createParameterAssignmentTask = Promise.resolve();
            var _loop_1 = function (i) {
                createParameterAssignmentTask = createParameterAssignmentTask.then(function () {
                    return _this.createNewParameterAssignment(testCase, _this.allParameters[i], compoundId);
                });
            };
            for (var i = 0; i < _this.allParameters.length; i++) {
                _loop_1(i);
            }
            return createParameterAssignmentTask.then();
        });
    };
    /** Creates a new Parameter Assignment and stores it virtually. */
    TestSpecificationEditor.prototype.createNewParameterAssignment = function (testCase, parameter, compoundId) {
        var parameterAssignment = new ParameterAssignment_1.ParameterAssignment();
        var id = Id_1.Id.uuid;
        var paramProxy = new proxy_1.Proxy();
        paramProxy.url = parameter.url;
        parameterAssignment.parameter = paramProxy;
        parameterAssignment.condition = config_1.Config.TESTPARAMETERASSIGNMENT_DEFAULT_CONDITION;
        parameterAssignment.value = config_1.Config.TESTPARAMETERASSIGNMENT_DEFAULT_VALUE;
        parameterAssignment.name = config_1.Config.TESTPARAMETERASSIGNMENT_NAME;
        parameterAssignment.id = id;
        parameterAssignment.url = Url_1.Url.build([testCase.url, id]);
        return this.dataService.createElement(parameterAssignment, true, compoundId);
    };
    Object.defineProperty(TestSpecificationEditor.prototype, "isValid", {
        /** Return true if all user inputs are valid  */
        get: function () {
            if (!this.genericForm) {
                return true;
            }
            return this.genericForm.isValid;
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        core_1.ViewChild(generic_form_component_1.GenericForm),
        __metadata("design:type", generic_form_component_1.GenericForm)
    ], TestSpecificationEditor.prototype, "genericForm", void 0);
    __decorate([
        core_1.ViewChildren(test_case_row_component_1.TestCaseRow),
        __metadata("design:type", core_1.QueryList)
    ], TestSpecificationEditor.prototype, "testCaseRows", void 0);
    TestSpecificationEditor = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'test-specification-editor',
            templateUrl: 'test-specification-editor.component.html',
            styleUrls: ['test-specification-editor.component.css']
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService,
            navigator_service_1.NavigatorService,
            router_1.ActivatedRoute,
            confirmation_modal_service_1.ConfirmationModal,
            editor_common_control_service_1.EditorCommonControlService])
    ], TestSpecificationEditor);
    return TestSpecificationEditor;
}(specmate_view_base_1.SpecmateViewBase));
exports.TestSpecificationEditor = TestSpecificationEditor;
//# sourceMappingURL=test-specification-editor.component.js.map