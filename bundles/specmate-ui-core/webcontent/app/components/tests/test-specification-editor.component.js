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
var confirmation_modal_service_1 = require("../../services/notification/confirmation-modal.service");
var navigator_service_1 = require("../../services/navigation/navigator.service");
var router_1 = require("@angular/router");
var Type_1 = require("../../util/Type");
var TestParameter_1 = require("../../model/TestParameter");
var TestCase_1 = require("../../model/TestCase");
var specmate_data_service_1 = require("../../services/data/specmate-data.service");
var core_1 = require("@angular/core");
var editor_common_control_service_1 = require("../../services/common-controls/editor-common-control.service");
var draggable_supporting_view_base_1 = require("../core/views/draggable-supporting-view-base");
var ng2_dragula_1 = require("ng2-dragula");
var test_input_parameter_factory_1 = require("../../factory/test-input-parameter-factory");
var test_output_parameter_factory_1 = require("../../factory/test-output-parameter-factory");
var test_case_factory_1 = require("../../factory/test-case-factory");
var TestSpecificationEditor = (function (_super) {
    __extends(TestSpecificationEditor, _super);
    /** Constructor */
    function TestSpecificationEditor(dataService, navigator, route, modal, editorCommonControlService, dragulaService) {
        return _super.call(this, dataService, navigator, route, modal, editorCommonControlService, dragulaService) || this;
    }
    Object.defineProperty(TestSpecificationEditor.prototype, "relevantElements", {
        get: function () {
            return this.contents.filter(function (element) { return Type_1.Type.is(element, TestCase_1.TestCase); });
        },
        enumerable: true,
        configurable: true
    });
    TestSpecificationEditor.prototype.onElementResolved = function (element) {
        _super.prototype.onElementResolved.call(this, element);
        this.testSpecification = element;
    };
    Object.defineProperty(TestSpecificationEditor.prototype, "inputParameters", {
        /** getter for the input parameters */
        get: function () {
            return this.testParameters.filter(function (testParameter) { return testParameter.type === 'INPUT'; });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestSpecificationEditor.prototype, "outputParameters", {
        /** getter for the output parameters */
        get: function () {
            return this.testParameters.filter(function (testParameter) { return testParameter.type === 'OUTPUT'; });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestSpecificationEditor.prototype, "testParameters", {
        /** getter for all parameters */
        get: function () {
            return this.contents.filter(function (element) { return Type_1.Type.is(element, TestParameter_1.TestParameter); }).map(function (element) { return element; });
        },
        enumerable: true,
        configurable: true
    });
    /** Adds a new test case (row) */
    TestSpecificationEditor.prototype.addTestCaseRow = function () {
        var factory = new test_case_factory_1.TestCaseFactory(this.dataService, true);
        factory.create(this.testSpecification, false);
    };
    /** Adds a new input column */
    TestSpecificationEditor.prototype.addInputColumn = function () {
        var factory = new test_input_parameter_factory_1.TestInputParameterFactory(this.dataService);
        factory.create(this.testSpecification, false);
    };
    /** Adds a new output column  */
    TestSpecificationEditor.prototype.addOutputColumn = function () {
        var factory = new test_output_parameter_factory_1.TestOutputParameterFactory(this.dataService);
        factory.create(this.testSpecification, false);
    };
    /** Returns true if the element is a TestCase - Important in UI. */
    TestSpecificationEditor.prototype.isTestCase = function (element) {
        return Type_1.Type.is(element, TestCase_1.TestCase);
    };
    Object.defineProperty(TestSpecificationEditor.prototype, "isValid", {
        /** Return true if all user inputs are valid  */
        get: function () {
            return true;
        },
        enumerable: true,
        configurable: true
    });
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
            editor_common_control_service_1.EditorCommonControlService,
            ng2_dragula_1.DragulaService])
    ], TestSpecificationEditor);
    return TestSpecificationEditor;
}(draggable_supporting_view_base_1.DraggableSupportingViewBase));
exports.TestSpecificationEditor = TestSpecificationEditor;
//# sourceMappingURL=test-specification-editor.component.js.map