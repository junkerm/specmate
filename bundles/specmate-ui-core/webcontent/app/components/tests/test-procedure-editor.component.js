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
var TestParameter_1 = require("../../model/TestParameter");
var TestSpecification_1 = require("../../model/TestSpecification");
var Type_1 = require("../../util/Type");
var TestCase_1 = require("../../model/TestCase");
var Url_1 = require("../../util/Url");
var Requirement_1 = require("../../model/Requirement");
var CEGModel_1 = require("../../model/CEGModel");
var router_1 = require("@angular/router");
var editor_common_control_service_1 = require("../../services/editor-common-control.service");
var specmate_data_service_1 = require("../../services/specmate-data.service");
var core_1 = require("@angular/core");
var TestProcedureEditor = (function () {
    /** Constructor */
    function TestProcedureEditor(dataService, route, editorCommonControlService) {
        this.dataService = dataService;
        this.route = route;
        this.editorCommonControlService = editorCommonControlService;
    }
    Object.defineProperty(TestProcedureEditor.prototype, "inputParameters", {
        /** getter for the input parameters of the parent test specification */
        get: function () {
            return this.testSpecContents.filter(function (c) {
                return Type_1.Type.is(c, TestParameter_1.TestParameter) && c.type === "INPUT";
            });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestProcedureEditor.prototype, "outputParameters", {
        /** getter for the output parameters of the parent test specification */
        get: function () {
            return this.testSpecContents.filter(function (c) {
                return Type_1.Type.is(c, TestParameter_1.TestParameter) && c.type === "OUTPUT";
            });
        },
        enumerable: true,
        configurable: true
    });
    TestProcedureEditor.prototype.ngOnInit = function () {
        var _this = this;
        this.editorCommonControlService.showCommonControls = true;
        this.dataService.clearCommits();
        this.route.params
            .switchMap(function (params) { return _this.dataService.readElement(Url_1.Url.fromParams(params)); })
            .subscribe(function (testProcedure) {
            _this.testProcedure = testProcedure;
            _this.readContents();
            _this.readParents();
        });
    };
    /** Rads to the contents of the test specification  */
    TestProcedureEditor.prototype.readContents = function () {
        var _this = this;
        if (this.testProcedure) {
            this.dataService.readContents(this.testProcedure.url).then(function (contents) {
                _this.contents = contents;
            });
        }
    };
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
    TestProcedureEditor.prototype.readParentRequirementFromCEG = function (cegUrl) {
        var _this = this;
        this.dataService.readElement(cegUrl).then(function (element) {
            if (Type_1.Type.is(element, Requirement_1.Requirement)) {
                _this.requirement = element;
            }
        });
    };
    TestProcedureEditor = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'test-procedure-editor',
            templateUrl: 'test-procedure-editor.component.html',
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService,
            router_1.ActivatedRoute,
            editor_common_control_service_1.EditorCommonControlService])
    ], TestProcedureEditor);
    return TestProcedureEditor;
}());
exports.TestProcedureEditor = TestProcedureEditor;
//# sourceMappingURL=test-procedure-editor.component.js.map