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
var navigator_service_1 = require("../../services/navigation/navigator.service");
var Id_1 = require("../../util/Id");
var generic_form_component_1 = require("../core/forms/generic-form.component");
var config_1 = require("../../config/config");
var TestStep_1 = require("../../model/TestStep");
var TestParameter_1 = require("../../model/TestParameter");
var TestSpecification_1 = require("../../model/TestSpecification");
var Type_1 = require("../../util/Type");
var TestCase_1 = require("../../model/TestCase");
var Url_1 = require("../../util/Url");
var Requirement_1 = require("../../model/Requirement");
var CEGModel_1 = require("../../model/CEGModel");
var router_1 = require("@angular/router");
var editor_common_control_service_1 = require("../../services/common-controls/editor-common-control.service");
var specmate_data_service_1 = require("../../services/data/specmate-data.service");
var core_1 = require("@angular/core");
var specmate_view_base_1 = require("../core/views/specmate-view-base");
var TestProcedureEditor = (function (_super) {
    __extends(TestProcedureEditor, _super);
    /** Constructor */
    function TestProcedureEditor(dataService, navigator, route, modal, editorCommonControlService) {
        return _super.call(this, dataService, navigator, route, modal, editorCommonControlService) || this;
    }
    Object.defineProperty(TestProcedureEditor.prototype, "testSteps", {
        /** The test steps ordered by position */
        get: function () {
            return this.contents.sort(function (testStep1, testStep2) {
                var position1 = testStep1.position;
                var position2 = testStep2.position;
                return position1 - position2;
            });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestProcedureEditor.prototype, "inputParameters", {
        /** getter for the input parameters of the parent test specification */
        get: function () {
            return this.allParameters.filter(function (param) { return param.type === 'INPUT'; });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestProcedureEditor.prototype, "outputParameters", {
        /** getter for the output parameters of the parent test specification */
        get: function () {
            return this.allParameters.filter(function (param) { return param.type === 'OUTPUT'; });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestProcedureEditor.prototype, "allParameters", {
        /** getter for all test parameters */
        get: function () {
            if (!this.testSpecContents) {
                return [];
            }
            return this.testSpecContents.filter(function (element) { return Type_1.Type.is(element, TestParameter_1.TestParameter); });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestProcedureEditor.prototype, "procedureEditorHeight", {
        get: function () {
            return config_1.Config.EDITOR_HEIGHT;
        },
        enumerable: true,
        configurable: true
    });
    TestProcedureEditor.prototype.onElementResolved = function (element) {
        this.testProcedure = element;
        this.readContents();
        this.readParents();
    };
    /** Reads to the contents of the test specification  */
    TestProcedureEditor.prototype.readContents = function () {
        var _this = this;
        if (this.testProcedure) {
            this.dataService.readContents(this.testProcedure.url).then(function (contents) {
                _this.contents = contents;
            });
        }
    };
    /** Reads the parents of this test procedure */
    TestProcedureEditor.prototype.readParents = function () {
        var testCaseUrl = Url_1.Url.parent(this.testProcedure.url);
        var testSpecUrl = Url_1.Url.parent(testCaseUrl);
        var testSpecParentUrl = Url_1.Url.parent(testSpecUrl);
        this.readParentTestCase(testCaseUrl);
        this.readParentTestSpec(testSpecUrl);
        this.readParentRequirement(testSpecParentUrl);
    };
    /** Reads the parent test case */
    TestProcedureEditor.prototype.readParentTestCase = function (testCaseUrl) {
        var _this = this;
        this.dataService.readElement(testCaseUrl).then(function (element) {
            if (Type_1.Type.is(element, TestCase_1.TestCase)) {
                _this.testCase = element;
            }
        });
    };
    /** Reads the parent test specification */
    TestProcedureEditor.prototype.readParentTestSpec = function (testSpecUrl) {
        var _this = this;
        if (this.testProcedure) {
            this.dataService.readElement(testSpecUrl).then(function (element) {
                if (Type_1.Type.is(element, TestSpecification_1.TestSpecification)) {
                    _this.testSpecification = element;
                }
            });
            this.dataService.readContents(testSpecUrl).then(function (elements) {
                _this.testSpecContents = elements;
            });
        }
    };
    /** Reads the parent requirement */
    TestProcedureEditor.prototype.readParentRequirement = function (testSpecParentUrl) {
        var _this = this;
        this.dataService.readElement(testSpecParentUrl).then(function (element) {
            if (Type_1.Type.is(element, Requirement_1.Requirement)) {
                _this.requirement = element;
            }
            else if (Type_1.Type.is(element, CEGModel_1.CEGModel)) {
                var cegUrl = Url_1.Url.parent(testSpecParentUrl);
                _this.readParentRequirementFromCEG(cegUrl);
            }
        });
    };
    /** Reads the parent requirement using the parent CEG */
    TestProcedureEditor.prototype.readParentRequirementFromCEG = function (cegUrl) {
        var _this = this;
        this.dataService.readElement(cegUrl).then(function (element) {
            if (Type_1.Type.is(element, Requirement_1.Requirement)) {
                _this.requirement = element;
            }
        });
    };
    /** Creates a new test case */
    TestProcedureEditor.prototype.createNewTestStep = function () {
        var id = Id_1.Id.uuid;
        var url = Url_1.Url.build([this.testProcedure.url, id]);
        var position = this.contents ? this.contents.length : 0;
        var testStep = new TestStep_1.TestStep();
        testStep.name = config_1.Config.TESTSTEP_NAME;
        testStep.description = config_1.Config.TESTSTEP_ACTION;
        testStep.expectedOutcome = config_1.Config.TESTSTEP_EXPECTED_OUTCOME;
        testStep.id = id;
        testStep.url = url;
        testStep.position = position;
        testStep.referencedTestParameters = [];
        this.dataService.createElement(testStep, true, Id_1.Id.uuid);
    };
    Object.defineProperty(TestProcedureEditor.prototype, "isValid", {
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
    ], TestProcedureEditor.prototype, "genericForm", void 0);
    TestProcedureEditor = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'test-procedure-editor',
            templateUrl: 'test-procedure-editor.component.html',
            styleUrls: ['test-procedure-editor.component.css']
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService,
            navigator_service_1.NavigatorService,
            router_1.ActivatedRoute,
            confirmation_modal_service_1.ConfirmationModal,
            editor_common_control_service_1.EditorCommonControlService])
    ], TestProcedureEditor);
    return TestProcedureEditor;
}(specmate_view_base_1.SpecmateViewBase));
exports.TestProcedureEditor = TestProcedureEditor;
//# sourceMappingURL=test-procedure-editor.component.js.map