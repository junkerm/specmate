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
var core_1 = require("@angular/core");
var CEGModel_1 = require("../../../../../model/CEGModel");
var Process_1 = require("../../../../../model/Process");
var specmate_data_service_1 = require("../../../../data/modules/data-service/services/specmate-data.service");
var confirmation_modal_service_1 = require("../../../../notification/modules/modals/services/confirmation-modal.service");
var navigator_service_1 = require("../../../../navigation/modules/navigator/services/navigator.service");
var TestSpecification_1 = require("../../../../../model/TestSpecification");
var id_1 = require("../../../../../util/id");
var url_1 = require("../../../../../util/url");
var config_1 = require("../../../../../config/config");
var type_1 = require("../../../../../util/type");
var CEGNode_1 = require("../../../../../model/CEGNode");
var ProcessStart_1 = require("../../../../../model/ProcessStart");
var ProcessEnd_1 = require("../../../../../model/ProcessEnd");
var ProcessStep_1 = require("../../../../../model/ProcessStep");
var ProcessConnection_1 = require("../../../../../model/ProcessConnection");
var ProcessDecision_1 = require("../../../../../model/ProcessDecision");
var TestSpecificationGeneratorButton = /** @class */ (function () {
    function TestSpecificationGeneratorButton(dataService, modal, navigator) {
        this.dataService = dataService;
        this.modal = modal;
        this.navigator = navigator;
        this.errorMessageTestSpecMap = {};
    }
    TestSpecificationGeneratorButton_1 = TestSpecificationGeneratorButton;
    TestSpecificationGeneratorButton.isCEGNode = function (element) {
        return (type_1.Type.is(element, CEGNode_1.CEGNode));
    };
    Object.defineProperty(TestSpecificationGeneratorButton.prototype, "model", {
        get: function () {
            return this._model;
        },
        set: function (model) {
            var _this = this;
            if (!model) {
                return;
            }
            this._model = model;
            this.dataService.readContents(model.url).then(function (contents) {
                _this.contents = contents;
                _this.doCheckCanCreateTestSpec();
            });
        },
        enumerable: true,
        configurable: true
    });
    TestSpecificationGeneratorButton.prototype.ngDoCheck = function () {
        this.doCheckCanCreateTestSpec();
    };
    TestSpecificationGeneratorButton.prototype.generate = function () {
        this.generateTestSpecification(this.model);
    };
    Object.defineProperty(TestSpecificationGeneratorButton.prototype, "enabled", {
        get: function () {
            return this.canCreateTestSpecification(this.model);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestSpecificationGeneratorButton.prototype, "errors", {
        get: function () {
            return this.getErrors(this.model);
        },
        enumerable: true,
        configurable: true
    });
    TestSpecificationGeneratorButton.prototype.generateTestSpecification = function (model) {
        var _this = this;
        if (!this.canCreateTestSpecification(model)) {
            return;
        }
        var testSpec = new TestSpecification_1.TestSpecification();
        testSpec.id = id_1.Id.uuid;
        testSpec.url = url_1.Url.build([model.url, testSpec.id]);
        testSpec.name = config_1.Config.TESTSPEC_NAME;
        testSpec.description = config_1.Config.TESTSPEC_DESCRIPTION;
        this.modal.confirmSave()
            .then(function () { return _this.dataService.createElement(testSpec, true, id_1.Id.uuid); })
            .then(function () { return _this.dataService.commit('Save'); })
            .then(function () { return _this.dataService.performOperations(testSpec.url, 'generateTests'); })
            .then(function () { return _this.dataService.readContents(testSpec.url); })
            .then(function () { return _this.navigator.navigate(testSpec); })
            .catch(function () { });
    };
    TestSpecificationGeneratorButton.prototype.canCreateTestSpecification = function (model) {
        if (!model) {
            return false;
        }
        return this.errorMessageTestSpecMap[model.url] === undefined || this.errorMessageTestSpecMap[model.url].length === 0;
    };
    TestSpecificationGeneratorButton.prototype.getErrors = function (model) {
        if (!model) {
            return undefined;
        }
        return this.errorMessageTestSpecMap[model.url];
    };
    TestSpecificationGeneratorButton.prototype.addErrorMessage = function (element, message) {
        var url = element.url;
        if (!this.errorMessageTestSpecMap[url]) {
            this.errorMessageTestSpecMap[url] = [];
        }
        this.errorMessageTestSpecMap[url].push(message);
    };
    // TODO: Move to separate class (VALIDATORS)
    TestSpecificationGeneratorButton.prototype.doCheckCanCreateTestSpec = function () {
        if (!this._model || !this.contents) {
            return;
        }
        this.errorMessageTestSpecMap[this.model.url] = [];
        if (type_1.Type.is(this.model, CEGModel_1.CEGModel)) {
            if (this.checkForSingleNodes(this.contents)) {
                this.addErrorMessage(this.model, config_1.Config.ERROR_UNCONNECTED_NODE);
            }
            if (this.checkForDuplicateIOVariable(this.contents)) {
                this.addErrorMessage(this.model, config_1.Config.ERROR_DUPLICATE_IO_VARIABLE);
            }
            if (this.checkForDuplicateNodes(this.contents)) {
                this.addErrorMessage(this.model, config_1.Config.ERROR_DUPLICATE_NODE);
            }
            if (this.contents.findIndex(function (element) { return type_1.Type.is(element, CEGNode_1.CEGNode); }) === -1) {
                this.addErrorMessage(this.model, config_1.Config.ERROR_EMPTY_MODEL);
            }
        }
        else if (type_1.Type.is(this.model, Process_1.Process)) {
            var hasSingleStartNode = this.contents.filter(function (element) { return type_1.Type.is(element, ProcessStart_1.ProcessStart); }).length === 1;
            if (!hasSingleStartNode) {
                this.addErrorMessage(this.model, config_1.Config.ERROR_NOT_ONE_START_NODE);
            }
            var hasEndNodes = this.contents.filter(function (element) { return type_1.Type.is(element, ProcessEnd_1.ProcessEnd); }).length > 0;
            if (!hasEndNodes) {
                this.addErrorMessage(this.model, config_1.Config.ERROR_NO_END_NODE);
            }
            var processNodes = this.contents.filter(function (element) {
                return type_1.Type.is(element, ProcessEnd_1.ProcessEnd) ||
                    type_1.Type.is(element, ProcessStart_1.ProcessStart) ||
                    type_1.Type.is(element, ProcessDecision_1.ProcessDecision) ||
                    type_1.Type.is(element, ProcessStep_1.ProcessStep);
            });
            var hasNodeWithoutIncomingConnections = processNodes.find(function (element) {
                return (!element.incomingConnections ||
                    (element.incomingConnections && element.incomingConnections.length === 0)) &&
                    !type_1.Type.is(element, ProcessStart_1.ProcessStart);
            }) !== undefined;
            if (hasNodeWithoutIncomingConnections) {
                this.addErrorMessage(this.model, config_1.Config.ERROR_NODE_WITHOUT_INCOMING);
            }
            var hasNodeWithoutOutgoingConnections = processNodes.find(function (element) {
                return (!element.outgoingConnections ||
                    (element.outgoingConnections && element.outgoingConnections.length === 0)) &&
                    !type_1.Type.is(element, ProcessEnd_1.ProcessEnd);
            }) !== undefined;
            if (hasNodeWithoutOutgoingConnections) {
                this.addErrorMessage(this.model, config_1.Config.ERROR_NODE_WITHOUT_OUTGOING);
            }
            var processSteps = processNodes.filter(function (element) { return type_1.Type.is(element, ProcessStep_1.ProcessStep); });
            if (processSteps.length === 0) {
                this.addErrorMessage(this.model, config_1.Config.ERROR_NO_STEPS);
            }
            var processConnections = this.contents.filter(function (element) { return type_1.Type.is(element, ProcessConnection_1.ProcessConnection); });
            var decisionNodes_1 = processNodes.filter(function (element) { return type_1.Type.is(element, ProcessDecision_1.ProcessDecision); });
            var decisionConnections = processConnections.filter(function (connection) {
                return decisionNodes_1.find(function (node) { return node.url === connection.source.url; }) !== undefined;
            });
            var hasMissingConditions = decisionConnections.find(function (connection) {
                return connection.condition === undefined || connection.condition === null || connection.condition === '';
            }) !== undefined;
            if (hasMissingConditions) {
                this.addErrorMessage(this.model, config_1.Config.ERROR_MISSING_CONDITION);
            }
        }
    };
    TestSpecificationGeneratorButton.prototype.checkForSingleNodes = function (contents) {
        return contents.some(function (element) {
            var isNode = TestSpecificationGeneratorButton_1.isCEGNode(element);
            if (!isNode) {
                return false;
            }
            var node = element;
            var hasIncomingConnections = node.incomingConnections && node.incomingConnections.length > 0;
            var hasOutgoingConnections = node.outgoingConnections && node.outgoingConnections.length > 0;
            return !hasIncomingConnections && !hasOutgoingConnections;
        });
    };
    TestSpecificationGeneratorButton.prototype.checkForDuplicateNodes = function (contents) {
        var nodes = contents.filter(function (element) { return type_1.Type.is(element, CEGNode_1.CEGNode); }).map(function (element) { return element; });
        var _loop_1 = function (i) {
            var currentNode = nodes[i];
            var isDuplicate = nodes.some(function (otherNode) {
                return otherNode.variable === currentNode.variable &&
                    otherNode.condition === currentNode.condition &&
                    otherNode !== currentNode;
            });
            if (isDuplicate) {
                return { value: true };
            }
        };
        for (var i = 0; i < nodes.length; i++) {
            var state_1 = _loop_1(i);
            if (typeof state_1 === "object")
                return state_1.value;
        }
        return false;
    };
    TestSpecificationGeneratorButton.prototype.checkForDuplicateIOVariable = function (contents) {
        var variableMap = {};
        for (var _i = 0, contents_1 = contents; _i < contents_1.length; _i++) {
            var content = contents_1[_i];
            if (!TestSpecificationGeneratorButton_1.isCEGNode(content)) {
                continue;
            }
            var node = content;
            var type = void 0;
            if (!node.incomingConnections || node.incomingConnections.length <= 0) {
                type = 'input';
            }
            else if (!node.outgoingConnections || node.outgoingConnections.length <= 0) {
                type = 'output';
            }
            else {
                type = 'intermediate';
            }
            var existing = variableMap[node.variable];
            if (existing) {
                if (existing === 'input' && type === 'output' || existing === 'output' && type === 'input') {
                    return true;
                }
            }
            else {
                variableMap[node.variable] = type;
            }
        }
        return false;
    };
    __decorate([
        core_1.Input(),
        __metadata("design:type", Object),
        __metadata("design:paramtypes", [Object])
    ], TestSpecificationGeneratorButton.prototype, "model", null);
    TestSpecificationGeneratorButton = TestSpecificationGeneratorButton_1 = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'test-specification-generator-button',
            templateUrl: 'test-specification-generator-button.component.html',
            styleUrls: ['test-specification-generator-button.component.css']
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService, confirmation_modal_service_1.ConfirmationModal, navigator_service_1.NavigatorService])
    ], TestSpecificationGeneratorButton);
    return TestSpecificationGeneratorButton;
    var TestSpecificationGeneratorButton_1;
}());
exports.TestSpecificationGeneratorButton = TestSpecificationGeneratorButton;
//# sourceMappingURL=test-specification-generator-button.component.js.map