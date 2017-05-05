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
var generic_form_component_1 = require('../core/forms/generic-form.component');
var CEGModel_1 = require('../../model/CEGModel');
var Type_1 = require('../../util/Type');
var TestParameter_1 = require('../../model/TestParameter');
var TestCase_1 = require('../../model/TestCase');
var Url_1 = require('../../util/Url');
var router_1 = require('@angular/router');
var specmate_data_service_1 = require('../../services/specmate-data.service');
var Requirement_1 = require('../../model/Requirement');
var core_1 = require('@angular/core');
var TestSpecificationEditor = (function () {
    /** constructor  */
    function TestSpecificationEditor(dataService, router, route) {
        this.dataService = dataService;
        this.router = router;
        this.route = route;
        /** The type of a test case (used for filtering) */
        this.testCaseType = TestCase_1.TestCase;
        /** The type of a test parameter (used for filtering) */
        this.parameterType = TestParameter_1.TestParameter;
    }
    /** Read contents and CEG and requirements parents */
    TestSpecificationEditor.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params
            .switchMap(function (params) { return _this.dataService.readElement(Url_1.Url.fromParams(params)); })
            .subscribe(function (testSpec) {
            _this.testSpecification = testSpec;
            _this.readContents();
            _this.readParents();
        });
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
    TestSpecificationEditor.prototype.save = function () {
        this.dataService.commit("Save");
    };
    __decorate([
        core_1.ViewChild(generic_form_component_1.GenericForm), 
        __metadata('design:type', generic_form_component_1.GenericForm)
    ], TestSpecificationEditor.prototype, "genericForm", void 0);
    TestSpecificationEditor = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'test-specification-editor',
            templateUrl: 'test-specification-editor.component.html'
        }), 
        __metadata('design:paramtypes', [specmate_data_service_1.SpecmateDataService, router_1.Router, router_1.ActivatedRoute])
    ], TestSpecificationEditor);
    return TestSpecificationEditor;
}());
exports.TestSpecificationEditor = TestSpecificationEditor;
//# sourceMappingURL=test-specification-editor.component.js.map