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
Object.defineProperty(exports, "__esModule", { value: true });
var CEGEffectNode_1 = require("../../model/CEGEffectNode");
var CEGCauseNode_1 = require("../../model/CEGCauseNode");
var CEGNode_1 = require("../../model/CEGNode");
var Type_1 = require("../../util/Type");
var Sort_1 = require("../../util/Sort");
var config_1 = require("../../config/config");
var Url_1 = require("../../util/Url");
var Id_1 = require("../../util/Id");
var TestSpecification_1 = require("../../model/TestSpecification");
var CEGModel_1 = require("../../model/CEGModel");
var specmate_view_base_1 = require("../core/views/specmate-view-base");
var TestSpecificationGenerator = (function (_super) {
    __extends(TestSpecificationGenerator, _super);
    function TestSpecificationGenerator(dataService, modal, route, navigator, editorCommonControlService) {
        var _this = _super.call(this, dataService, navigator, route, modal, editorCommonControlService) || this;
        _this.canGenerateTestSpecMap = {};
        return _this;
    }
    TestSpecificationGenerator.prototype.onElementResolved = function (element) {
        var _this = this;
        this.resolveRequirement(element).then(function (requirement) { return _this.init(requirement); });
    };
    TestSpecificationGenerator.prototype.init = function (requirement) {
        var _this = this;
        this.requirement = requirement;
        this.dataService.readContents(this.requirement.url).then(function (contents) {
            _this.requirementContents = contents;
            for (var i = 0; i < _this.requirementContents.length; i++) {
                var currentElement = _this.requirementContents[i];
                if (!Type_1.Type.is(currentElement, CEGModel_1.CEGModel)) {
                    continue;
                }
                _this.initCanCreateTestSpec(currentElement);
            }
        });
        this.readAllTestSpecifications();
    };
    TestSpecificationGenerator.prototype.initCanCreateTestSpec = function (currentElement) {
        var _this = this;
        this.dataService.readContents(currentElement.url).then(function (contents) {
            _this.doCheckCanCreateTestSpec(currentElement, contents);
        });
    };
    TestSpecificationGenerator.isNode = function (element) {
        return (Type_1.Type.is(element, CEGNode_1.CEGNode) || Type_1.Type.is(element, CEGCauseNode_1.CEGCauseNode) || Type_1.Type.is(element, CEGEffectNode_1.CEGEffectNode));
    };
    TestSpecificationGenerator.hasNodes = function (contents) {
        return contents.filter(function (element) { return TestSpecificationGenerator.isNode(element); }).length > 0;
    };
    TestSpecificationGenerator.prototype.doCheckCanCreateTestSpec = function (currentElement, contents) {
        var hasSingleNode = contents.some(function (element) {
            var isNode = TestSpecificationGenerator.isNode(element);
            if (!isNode) {
                return false;
            }
            var node = element;
            var hasIncomingConnections = node.incomingConnections && node.incomingConnections.length > 0;
            var hasOutgoingConnections = node.outgoingConnections && node.outgoingConnections.length > 0;
            return !hasIncomingConnections && !hasOutgoingConnections;
        });
        this.canGenerateTestSpecMap[currentElement.url] = !hasSingleNode && TestSpecificationGenerator.hasNodes(contents);
    };
    TestSpecificationGenerator.prototype.readAllTestSpecifications = function () {
        var _this = this;
        this.dataService.performQuery(this.requirement.url, 'listRecursive', { class: TestSpecification_1.TestSpecification.className }).then(function (testSpecifications) { return _this.allTestSpecifications = Sort_1.Sort.sortArray(testSpecifications); });
    };
    TestSpecificationGenerator.prototype.canCreateTestSpecification = function (ceg) {
        return this.canGenerateTestSpecMap[ceg.url];
    };
    TestSpecificationGenerator.prototype.generateTestSpecification = function (ceg) {
        var _this = this;
        if (!this.requirementContents) {
            return;
        }
        if (!this.canCreateTestSpecification(ceg)) {
            return;
        }
        var testSpec = new TestSpecification_1.TestSpecification();
        testSpec.id = Id_1.Id.uuid;
        testSpec.url = Url_1.Url.build([ceg.url, testSpec.id]);
        testSpec.name = config_1.Config.TESTSPEC_NAME;
        testSpec.description = config_1.Config.TESTSPEC_DESCRIPTION;
        this.modal.confirmSave()
            .then(function () { return _this.dataService.createElement(testSpec, true, Id_1.Id.uuid); })
            .then(function () { return _this.dataService.commit('Create'); })
            .then(function () { return _this.dataService.performOperations(testSpec.url, 'generateTests'); })
            .then(function () { return _this.navigator.navigate(testSpec); })
            .catch(function () { });
    };
    TestSpecificationGenerator.prototype.createTestSpecification = function () {
        var _this = this;
        if (!this.requirementContents) {
            return;
        }
        var testSpec = new TestSpecification_1.TestSpecification();
        testSpec.id = Id_1.Id.uuid;
        testSpec.url = Url_1.Url.build([this.requirement.url, testSpec.id]);
        testSpec.name = config_1.Config.TESTSPEC_NAME;
        testSpec.description = config_1.Config.TESTSPEC_DESCRIPTION;
        this.dataService.createElement(testSpec, true, Id_1.Id.uuid)
            .then(function () { return _this.dataService.commit('Create'); })
            .then(function () { return _this.navigator.navigate(testSpec); });
    };
    return TestSpecificationGenerator;
}(specmate_view_base_1.SpecmateViewBase));
exports.TestSpecificationGenerator = TestSpecificationGenerator;
//# sourceMappingURL=test-specification-generator.js.map