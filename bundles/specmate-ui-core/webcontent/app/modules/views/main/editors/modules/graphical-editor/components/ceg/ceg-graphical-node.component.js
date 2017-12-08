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
var CEGNode_1 = require("../../../../../../../../model/CEGNode");
var config_1 = require("../../../../../../../../config/config");
var specmate_data_service_1 = require("../../../../../../../data/modules/data-service/services/specmate-data.service");
var selected_element_service_1 = require("../../../../../../side/modules/selected-element/services/selected-element.service");
var validation_service_1 = require("../../../../../../../forms/modules/validation/services/validation.service");
var CEGGraphicalNode = /** @class */ (function (_super) {
    __extends(CEGGraphicalNode, _super);
    function CEGGraphicalNode(dataService, selectedElementService, validationService) {
        var _this = _super.call(this, selectedElementService, validationService) || this;
        _this.dataService = dataService;
        _this.nodeType = CEGNode_1.CEGNode;
        return _this;
    }
    Object.defineProperty(CEGGraphicalNode.prototype, "dimensions", {
        get: function () {
            return {
                width: config_1.Config.CEG_NODE_WIDTH,
                height: config_1.Config.CEG_NODE_HEIGHT
            };
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalNode.prototype, "element", {
        get: function () {
            return this.node;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalNode.prototype, "title", {
        get: function () {
            return this.node.variable + ' ' + this.node.condition;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalNode.prototype, "type", {
        get: function () {
            return this.node.type;
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        core_1.Input(),
        __metadata("design:type", CEGNode_1.CEGNode)
    ], CEGGraphicalNode.prototype, "node", void 0);
    CEGGraphicalNode = __decorate([
        core_1.Component({
            moduleId: module.id.toString(),
            selector: '[ceg-graphical-node]',
            templateUrl: 'ceg-graphical-node.component.svg',
            styleUrls: ['ceg-graphical-node.component.css']
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService,
            selected_element_service_1.SelectedElementService,
            validation_service_1.ValidationService])
    ], CEGGraphicalNode);
    return CEGGraphicalNode;
}(draggable_element_base_1.DraggableElementBase));
exports.CEGGraphicalNode = CEGGraphicalNode;
//# sourceMappingURL=ceg-graphical-node.component.js.map