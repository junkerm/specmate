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
var specmate_view_base_1 = require("../views/specmate-view-base");
var TestSpecification_1 = require("../../../model/TestSpecification");
var Type_1 = require("../../../util/Type");
var CEGModel_1 = require("../../../model/CEGModel");
var Process_1 = require("../../../model/Process");
var CEGNode_1 = require("../../../model/CEGNode");
var config_1 = require("../../../config/config");
var ProcessStart_1 = require("../../../model/ProcessStart");
var ProcessEnd_1 = require("../../../model/ProcessEnd");
var ProcessDecision_1 = require("../../../model/ProcessDecision");
var ProcessStep_1 = require("../../../model/ProcessStep");
var ProcessConnection_1 = require("../../../model/ProcessConnection");
var Sort_1 = require("../../../util/Sort");
var Id_1 = require("../../../util/Id");
var Url_1 = require("../../../util/Url");
var TestSpecificationGenerator = (function (_super) {
    __extends(TestSpecificationGenerator, _super);
    function TestSpecificationGenerator(dataService, modal, route, navigator, editorCommonControlService) {
        var _this = _super.call(this, dataService, navigator, route, modal, editorCommonControlService) || this;
        _this.errorMessageTestSpecMap = {};
        return _this;
    }
    TestSpecificationGenerator.prototype.onElementResolved = function (element) {
        var _this = this;
        this.resolveRequirement(element).then(function (requirement) { return _this.init(requirement); });
    };
    Object.defineProperty(TestSpecificationGenerator.prototype, "generator", {
        get: function () {
            return this;
        },
        enumerable: true,
        configurable: true
    });
    TestSpecificationGenerator.prototype.init = function (requirement) {
        var _this = this;
        this.requirement = requirement;
        this.dataService.readContents(this.requirement.url).then(function (contents) {
            _this.requirementContents = contents;
            for (var i = 0; i < _this.requirementContents.length; i++) {
                var currentElement = _this.requirementContents[i];
                if (Type_1.Type.is(currentElement, CEGModel_1.CEGModel) || Type_1.Type.is(currentElement, Process_1.Process)) {
                    _this.initCanCreateTestSpec(currentElement);
                }
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
        return (Type_1.Type.is(element, CEGNode_1.CEGNode));
    };
    TestSpecificationGenerator.hasNodes = function (contents) {
        return contents.filter(function (element) { return TestSpecificationGenerator.isNode(element); }).length > 0;
    };
    TestSpecificationGenerator.prototype.addErrorMessage = function (element, message) {
        var url = element.url;
        if (!this.errorMessageTestSpecMap[url]) {
            this.errorMessageTestSpecMap[url] = [];
        }
        this.errorMessageTestSpecMap[url].push(message);
    };
    TestSpecificationGenerator.prototype.doCheckCanCreateTestSpec = function (currentElement, contents) {
        this.errorMessageTestSpecMap[currentElement.url] = [];
        if (Type_1.Type.is(currentElement, CEGModel_1.CEGModel)) {
            if (this.checkForSingleNodes(contents)) {
                this.addErrorMessage(currentElement, config_1.Config.ERROR_UNCONNECTED_NODE);
            }
            if (this.checkForDuplicateIOVariable(contents)) {
                this.addErrorMessage(currentElement, config_1.Config.ERROR_DUPLICATE_IO_VARIABLE);
            }
            if (contents.findIndex(function (element) { return Type_1.Type.is(element, CEGNode_1.CEGNode); }) === -1) {
                this.addErrorMessage(currentElement, config_1.Config.ERROR_EMPTY_MODEL);
            }
        }
        else if (Type_1.Type.is(currentElement, Process_1.Process)) {
            var hasSingleStartNode = contents.filter(function (element) { return Type_1.Type.is(element, ProcessStart_1.ProcessStart); }).length == 1;
            if (!hasSingleStartNode) {
                this.addErrorMessage(currentElement, config_1.Config.ERROR_NOT_ONE_START_NODE);
            }
            var hasEndNodes = contents.filter(function (element) { return Type_1.Type.is(element, ProcessEnd_1.ProcessEnd); }).length > 0;
            if (!hasEndNodes) {
                this.addErrorMessage(currentElement, config_1.Config.ERROR_NO_END_NODE);
            }
            var processNodes = contents.filter(function (element) { return Type_1.Type.is(element, ProcessEnd_1.ProcessEnd) || Type_1.Type.is(element, ProcessStart_1.ProcessStart) || Type_1.Type.is(element, ProcessDecision_1.ProcessDecision) || Type_1.Type.is(element, ProcessStep_1.ProcessStep); });
            var hasNodeWithoutIncomingConnections = processNodes.find(function (element) { return (!element.incomingConnections || (element.incomingConnections && element.incomingConnections.length == 0)) && !Type_1.Type.is(element, ProcessStart_1.ProcessStart); }) !== undefined;
            if (hasNodeWithoutIncomingConnections) {
                this.addErrorMessage(currentElement, config_1.Config.ERROR_NODE_WITHOUT_INCOMING);
            }
            var hasNodeWithoutOutgoingConnections = processNodes.find(function (element) { return (!element.outgoingConnections || (element.outgoingConnections && element.outgoingConnections.length == 0)) && !Type_1.Type.is(element, ProcessEnd_1.ProcessEnd); }) !== undefined;
            if (hasNodeWithoutOutgoingConnections) {
                this.addErrorMessage(currentElement, config_1.Config.ERROR_NODE_WITHOUT_OUTGOING);
            }
            var processSteps = processNodes.filter(function (element) { return Type_1.Type.is(element, ProcessStep_1.ProcessStep); });
            if (processSteps.length === 0) {
                this.addErrorMessage(currentElement, config_1.Config.ERROR_NO_STEPS);
            }
            var processConnections = contents.filter(function (element) { return Type_1.Type.is(element, ProcessConnection_1.ProcessConnection); });
            var decisionNodes_1 = processNodes.filter(function (element) { return Type_1.Type.is(element, ProcessDecision_1.ProcessDecision); });
            var decisionConnections = processConnections.filter(function (connection) { return decisionNodes_1.find(function (node) { return node.url === connection.source.url; }) !== undefined; });
            var hasMissingConditions = decisionConnections.find(function (connection) { return connection.condition === undefined || connection.condition === null || connection.condition === ''; }) !== undefined;
            if (hasMissingConditions) {
                this.addErrorMessage(currentElement, config_1.Config.ERROR_MISSING_CONDITION);
            }
        }
    };
    TestSpecificationGenerator.prototype.checkForSingleNodes = function (contents) {
        return contents.some(function (element) {
            var isNode = TestSpecificationGenerator.isNode(element);
            if (!isNode) {
                return false;
            }
            var node = element;
            var hasIncomingConnections = node.incomingConnections && node.incomingConnections.length > 0;
            var hasOutgoingConnections = node.outgoingConnections && node.outgoingConnections.length > 0;
            return !hasIncomingConnections && !hasOutgoingConnections;
        });
    };
    TestSpecificationGenerator.prototype.checkForDuplicateIOVariable = function (contents) {
        var variableMap = {};
        for (var _i = 0, contents_1 = contents; _i < contents_1.length; _i++) {
            var content = contents_1[_i];
            if (!TestSpecificationGenerator.isNode(content)) {
                continue;
            }
            var node = content;
            var type = void 0;
            if (!node.incomingConnections || node.incomingConnections.length <= 0) {
                type = "input";
            }
            else if (!node.outgoingConnections || node.outgoingConnections.length <= 0) {
                type = "output";
            }
            else {
                type = "intermediate";
            }
            var existing = variableMap[node.variable];
            if (existing) {
                if (existing === "input" && type === "output" || existing === "output" && type === "input") {
                    return true;
                }
            }
            else {
                variableMap[node.variable] = type;
            }
        }
        return false;
    };
    TestSpecificationGenerator.prototype.readAllTestSpecifications = function () {
        var _this = this;
        this.dataService.performQuery(this.requirement.url, 'listRecursive', { class: TestSpecification_1.TestSpecification.className }).then(function (testSpecifications) { return _this.allTestSpecifications = Sort_1.Sort.sortArray(testSpecifications); });
    };
    TestSpecificationGenerator.prototype.canCreateTestSpecification = function (model) {
        return this.errorMessageTestSpecMap[model.url] === undefined || this.errorMessageTestSpecMap[model.url].length === 0;
    };
    TestSpecificationGenerator.prototype.generateTestSpecification = function (model) {
        var _this = this;
        if (!this.requirementContents) {
            return;
        }
        if (!this.canCreateTestSpecification(model)) {
            return;
        }
        var testSpec = new TestSpecification_1.TestSpecification();
        testSpec.id = Id_1.Id.uuid;
        testSpec.url = Url_1.Url.build([model.url, testSpec.id]);
        testSpec.name = config_1.Config.TESTSPEC_NAME;
        testSpec.description = config_1.Config.TESTSPEC_DESCRIPTION;
        this.modal.confirmSave()
            .then(function () { return _this.dataService.createElement(testSpec, false, Id_1.Id.uuid); })
            .then(function () { return _this.dataService.performOperations(testSpec.url, 'generateTests'); })
            .then(function () { return _this.dataService.readContents(testSpec.url); })
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
    TestSpecificationGenerator.prototype.getErrors = function (model) {
        return this.errorMessageTestSpecMap[model.url];
    };
    return TestSpecificationGenerator;
}(specmate_view_base_1.SpecmateViewBase));
exports.TestSpecificationGenerator = TestSpecificationGenerator;
//# sourceMappingURL=test-specification-generator.js.map