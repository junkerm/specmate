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
var core_1 = require("@angular/core");
var draggable_element_base_1 = require("../../elements/draggable-element-base");
var ProcessDecision_1 = require("../../../../../../../../model/ProcessDecision");
var config_1 = require("../../../../../../../../config/config");
var ProcessStep_1 = require("../../../../../../../../model/ProcessStep");
var specmate_data_service_1 = require("../../../../../../../data/modules/data-service/services/specmate-data.service");
var selected_element_service_1 = require("../../../../../../side/modules/selected-element/services/selected-element.service");
var validation_service_1 = require("../../../../../../../forms/modules/validation/services/validation.service");
var ProcessDecisionGraphicalNode = /** @class */ (function (_super) {
    __extends(ProcessDecisionGraphicalNode, _super);
    function ProcessDecisionGraphicalNode(dataService, selectedElementService, validationService) {
        var _this = _super.call(this, selectedElementService, validationService) || this;
        _this.dataService = dataService;
        _this.nodeType = ProcessDecision_1.ProcessDecision;
        return _this;
    }
    Object.defineProperty(ProcessDecisionGraphicalNode.prototype, "dimensions", {
        get: function () {
            return {
                width: config_1.Config.PROCESS_DECISION_NODE_DIM * 2,
                height: config_1.Config.PROCESS_DECISION_NODE_DIM * 2
            };
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessDecisionGraphicalNode.prototype, "element", {
        get: function () {
            return this.node;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessDecisionGraphicalNode.prototype, "top", {
        get: function () {
            return {
                x: this.topLeft.x + this.dimensions.width / 2,
                y: this.topLeft.y
            };
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessDecisionGraphicalNode.prototype, "right", {
        get: function () {
            return {
                x: this.topLeft.x + this.dimensions.width,
                y: this.topLeft.y + this.dimensions.width / 2
            };
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessDecisionGraphicalNode.prototype, "bottom", {
        get: function () {
            return {
                x: this.topLeft.x + this.dimensions.width / 2,
                y: this.topLeft.y + this.dimensions.width
            };
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessDecisionGraphicalNode.prototype, "left", {
        get: function () {
            return {
                x: this.topLeft.x,
                y: this.topLeft.y + this.dimensions.width / 2
            };
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessDecisionGraphicalNode.prototype, "title", {
        get: function () {
            return this.node.name;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessDecisionGraphicalNode.prototype, "coords", {
        get: function () {
            return [
                this.top.x,
                this.top.y,
                this.right.x,
                this.right.y,
                this.bottom.x,
                this.bottom.y,
                this.left.x,
                this.left.y
            ];
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessDecisionGraphicalNode.prototype, "svgPathString", {
        get: function () {
            return 'M 0 0 M ' + this.coords.join(' ') + ' Z';
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        core_1.Input(),
        __metadata("design:type", ProcessStep_1.ProcessStep)
    ], ProcessDecisionGraphicalNode.prototype, "node", void 0);
    ProcessDecisionGraphicalNode = __decorate([
        core_1.Component({
            moduleId: module.id.toString(),
            selector: '[process-decision-graphical-node]',
            templateUrl: 'process-decision-graphical-node.component.svg',
            styleUrls: ['process-decision-graphical-node.component.css']
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService,
            selected_element_service_1.SelectedElementService,
            validation_service_1.ValidationService])
    ], ProcessDecisionGraphicalNode);
    return ProcessDecisionGraphicalNode;
}(draggable_element_base_1.DraggableElementBase));
exports.ProcessDecisionGraphicalNode = ProcessDecisionGraphicalNode;
//# sourceMappingURL=process-decision-graphical-node.component.js.map