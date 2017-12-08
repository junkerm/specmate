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
var element_factory_base_1 = require("./element-factory-base");
var ParameterAssignment_1 = require("../model/ParameterAssignment");
var id_1 = require("../util/id");
var proxy_1 = require("../model/support/proxy");
var config_1 = require("../config/config");
var url_1 = require("../util/url");
var ParameterAssignmentFactory = /** @class */ (function (_super) {
    __extends(ParameterAssignmentFactory, _super);
    function ParameterAssignmentFactory(dataService, testParameter) {
        var _this = _super.call(this, dataService) || this;
        _this.testParameter = testParameter;
        return _this;
    }
    ParameterAssignmentFactory.prototype.create = function (parent, commit, compoundId) {
        var _this = this;
        compoundId = compoundId || id_1.Id.uuid;
        var parameterAssignment = new ParameterAssignment_1.ParameterAssignment();
        var id = id_1.Id.uuid;
        var paramProxy = new proxy_1.Proxy();
        paramProxy.url = this.testParameter.url;
        parameterAssignment.parameter = paramProxy;
        parameterAssignment.condition = config_1.Config.TESTPARAMETERASSIGNMENT_DEFAULT_CONDITION;
        parameterAssignment.value = config_1.Config.TESTPARAMETERASSIGNMENT_DEFAULT_VALUE;
        parameterAssignment.name = config_1.Config.TESTPARAMETERASSIGNMENT_NAME;
        parameterAssignment.id = id;
        parameterAssignment.url = url_1.Url.build([parent.url, id]);
        var assignmentProxy = new proxy_1.Proxy();
        assignmentProxy.url = parameterAssignment.url;
        this.testParameter.assignments.push(assignmentProxy);
        return this.dataService.createElement(parameterAssignment, true, compoundId)
            .then(function () { return commit ? _this.dataService.commit('Create') : Promise.resolve(); })
            .then(function () { return parameterAssignment; });
    };
    return ParameterAssignmentFactory;
}(element_factory_base_1.ElementFactoryBase));
exports.ParameterAssignmentFactory = ParameterAssignmentFactory;
//# sourceMappingURL=parameter-assignment-factory.js.map