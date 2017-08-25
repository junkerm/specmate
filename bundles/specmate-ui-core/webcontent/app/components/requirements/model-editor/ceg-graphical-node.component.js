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
var Id_1 = require("../../../util/Id");
var specmate_data_service_1 = require("../../../services/specmate-data.service");
var CEGEffectNode_1 = require("../../../model/CEGEffectNode");
var CEGCauseNode_1 = require("../../../model/CEGCauseNode");
var Type_1 = require("../../../util/Type");
var core_1 = require("@angular/core");
var config_1 = require("../../../config/config");
var CEGNode_1 = require("../../../model/CEGNode");
var CEGGraphicalNode = (function () {
    function CEGGraphicalNode(dataService) {
        this.dataService = dataService;
        this.grabbed = false;
        this.width = config_1.Config.CEG_NODE_WIDTH;
        this.height = config_1.Config.CEG_NODE_HEIGHT;
    }
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
    CEGGraphicalNode.prototype.drag = function (e) {
        e.preventDefault();
        if (this.grabbed) {
            var movementX = (this.prevX ? e.offsetX - this.prevX : 0);
            var movementY = (this.prevY ? e.offsetY - this.prevY : 0);
            var destX = this.node.x + movementX;
            var destY = this.node.y + movementY;
            if (this.isMove(movementX, movementY) && this.isWithinBounds(destX, destY)) {
                this.node.x = destX;
                this.node.y = destY;
            }
            this.prevX = e.offsetX;
            this.prevY = e.offsetY;
        }
    };
    CEGGraphicalNode.prototype.leave = function (e) {
        e.preventDefault();
        this.dragEnd();
    };
    CEGGraphicalNode.prototype.grab = function (e) {
        e.preventDefault();
        this.dragStart(e);
    };
    CEGGraphicalNode.prototype.drop = function (e) {
        e.preventDefault();
        this.dragEnd();
    };
    CEGGraphicalNode.prototype.dragStart = function (e) {
        this.grabbed = true;
    };
    CEGGraphicalNode.prototype.dragEnd = function () {
        if (this.grabbed) {
            this.grabbed = false;
            this.prevX = undefined;
            this.prevY = undefined;
            this.dataService.updateElement(this.node, true, Id_1.Id.uuid);
        }
    };
    CEGGraphicalNode.prototype.isMove = function (movementX, movementY) {
        return movementX > 0 || movementX < 0 || movementY > 0 || movementY < 0;
    };
    CEGGraphicalNode.prototype.isWithinBounds = function (destX, destY) {
        return destX >= 0 && destY >= 0;
    };
    Object.defineProperty(CEGGraphicalNode.prototype, "isCauseNode", {
        get: function () {
            return Type_1.Type.is(this.node, CEGCauseNode_1.CEGCauseNode);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalNode.prototype, "isEffectNode", {
        get: function () {
            return Type_1.Type.is(this.node, CEGEffectNode_1.CEGEffectNode);
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        core_1.Input(),
        __metadata("design:type", CEGNode_1.CEGNode)
    ], CEGGraphicalNode.prototype, "node", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Boolean)
    ], CEGGraphicalNode.prototype, "selected", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Boolean)
    ], CEGGraphicalNode.prototype, "valid", void 0);
    CEGGraphicalNode = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: '[ceg-graphical-node]',
            templateUrl: 'ceg-graphical-node.component.svg',
            styleUrls: ['ceg-graphical-node.component.css']
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService])
    ], CEGGraphicalNode);
    return CEGGraphicalNode;
}());
exports.CEGGraphicalNode = CEGGraphicalNode;
//# sourceMappingURL=ceg-graphical-node.component.js.map