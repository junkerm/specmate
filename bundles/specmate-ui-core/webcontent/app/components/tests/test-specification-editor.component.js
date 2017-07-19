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
var router_1 = require("@angular/router");
var specmate_data_service_1 = require("../../services/specmate-data.service");
var Requirement_1 = require("../../model/Requirement");
var core_1 = require("@angular/core");
var editor_common_control_service_1 = require("../../services/editor-common-control.service");
var TestSpecificationEditor = (function () {
    /** constructor  */
    function TestSpecificationEditor(dataService, router, route, editorCommonControlService) {
        this.dataService = dataService;
        this.router = router;
        this.route = route;
        this.editorCommonControlService = editorCommonControlService;
        /** The type of a test case (used for filtering) */
        this.testCaseType = TestCase_1.TestCase;
        /** The type of a test parameter (used for filtering) */
        this.parameterType = TestParameter_1.TestParameter;
    }
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
    /** Read contents and CEG and requirements parents */
    TestSpecificationEditor.prototype.ngOnInit = function () {
        var _this = this;
        this.editorCommonControlService.showCommonControls = true;
        this.dataService.clearCommits();
        this.route.params
            .switchMap(function (params) { return _this.dataService.readElement(Url_1.Url.fromParams(params)); })
            .subscribe(function (testSpec) {
            _this.testSpecification = testSpec;
            _this.readContents();
            _this.readParents();
        });
    };
    TestSpecificationEditor.prototype.ngDoCheck = function (args) {
        this.editorCommonControlService.isCurrentEditorValid = this.isValid;
    };
    /** Rads to the contents of the test specification  */
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
    TestSpecificationEditor.prototype.createNewTestParameter = function (id) {
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
        this.getNewTestParameterId().then(function (id) {
            var parameter = _this.createNewTestParameter(id);
            parameter.type = type;
            _this.dataService.createElement(parameter, true);
            var createParameterAssignmentTask = Promise.resolve();
            _this.testCases.forEach(function (testCase) {
                createParameterAssignmentTask = createParameterAssignmentTask.then(function () {
                    return _this.createNewParameterAssignment(testCase, parameter).then(function () {
                        _this.testCaseRows.find(function (testCaseRow) { return testCaseRow.testCase === testCase; }).loadAssignmentMap(true);
                    });
                });
            });
        });
    };
    /** Creates a new id  */
    TestSpecificationEditor.prototype.getNewId = function (base) {
        return this.dataService.readContents(this.testSpecification.url, true).then(function (contents) { return Id_1.Id.generate(contents, base); });
    };
    TestSpecificationEditor.prototype.getNewTestParameterId = function () {
        return this.getNewId(config_1.Config.TESTPARAMETER_BASE_ID);
    };
    TestSpecificationEditor.prototype.getNewTestCaseId = function () {
        return this.getNewId(config_1.Config.TESTCASE_BASE_ID);
    };
    TestSpecificationEditor.prototype.getNewParameterAssignmentId = function (testCase) {
        return this.dataService.readContents(testCase.url, true).then(function (contents) { return Id_1.Id.generate(contents, config_1.Config.TESTPARAMETERASSIGNMENT_BASE_ID); });
    };
    /** Creates a new test case */
    TestSpecificationEditor.prototype.createNewTestCase = function (id) {
        var _this = this;
        this.getNewTestCaseId().then(function (id) {
            var url = Url_1.Url.build([_this.testSpecification.url, id]);
            var testCase = new TestCase_1.TestCase();
            testCase.name = config_1.Config.TESTCASE_NAME;
            testCase.id = id;
            testCase.url = url;
            _this.dataService.createElement(testCase, true).then(function () {
                var createParameterAssignmentTask = Promise.resolve();
                _this.allParameters.forEach(function (parameter) {
                    createParameterAssignmentTask = createParameterAssignmentTask.then(function () {
                        return _this.createNewParameterAssignment(testCase, parameter);
                    });
                });
            });
        });
    };
    /** Creates a new Parameter Assignment and stores it virtually. */
    TestSpecificationEditor.prototype.createNewParameterAssignment = function (testCase, parameter) {
        var _this = this;
        return this.getNewParameterAssignmentId(testCase).then(function (id) {
            var parameterAssignment = new ParameterAssignment_1.ParameterAssignment();
            var paramProxy = new proxy_1.Proxy();
            paramProxy.url = parameter.url;
            parameterAssignment.parameter = paramProxy;
            parameterAssignment.value = config_1.Config.TESTPARAMETERASSIGNMENT_DEFAULT_VALUE;
            parameterAssignment.name = config_1.Config.TESTPARAMETERASSIGNMENT_NAME;
            parameterAssignment.id = id;
            parameterAssignment.url = Url_1.Url.build([testCase.url, id]);
            return parameterAssignment;
        }).then(function (parameterAssignment) {
            return _this.dataService.createElement(parameterAssignment, true);
        });
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
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService, router_1.Router, router_1.ActivatedRoute, editor_common_control_service_1.EditorCommonControlService])
    ], TestSpecificationEditor);
    return TestSpecificationEditor;
}());
exports.TestSpecificationEditor = TestSpecificationEditor;
//# sourceMappingURL=test-specification-editor.component.js.map