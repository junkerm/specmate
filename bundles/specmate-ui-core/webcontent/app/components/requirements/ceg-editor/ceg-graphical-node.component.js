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
var core_1 = require('@angular/core');
var router_1 = require('@angular/router');
var config_1 = require('../../../config/config');
var CEGNode_1 = require('../../../model/CEGNode');
var d3_ng2_service_1 = require('d3-ng2-service');
var Type_1 = require("../../../util/Type");
var CEGCauseNode_1 = require("../../../model/CEGCauseNode");
var CEGEffectNode_1 = require("../../../model/CEGEffectNode");
var CEGGraphicalNode = (function () {
    function CEGGraphicalNode(d3Service, elementRef, router, route) {
        var _this = this;
        this.d3Service = d3Service;
        this.elementRef = elementRef;
        this.router = router;
        this.route = route;
        this.width = config_1.Config.CEG_NODE_WIDTH;
        this.height = config_1.Config.CEG_NODE_HEIGHT;
        this.d3 = d3Service.getD3();
        this.d3.select(this.elementRef.nativeElement)
            .call(this.d3.drag().on('drag', function () { return _this.drag(); }));
    }
    CEGGraphicalNode.prototype.drag = function () {
        if (this.isWithinBounds) {
            this.node.x += this.d3.event.dx;
            this.node.y += this.d3.event.dy;
        }
    };
    Object.defineProperty(CEGGraphicalNode.prototype, "isWithinBounds", {
        get: function () {
            var destX = this.node.x + this.d3.event.dx;
            var destY = this.node.y + this.d3.event.dy;
            return destX >= 0 &&
                destX + config_1.Config.CEG_NODE_WIDTH <= this.editorSizeX &&
                destY >= 0 &&
                destY + config_1.Config.CEG_NODE_HEIGHT <= this.editorSizeY;
        },
        enumerable: true,
        configurable: true
    });
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
    Object.defineProperty(CEGGraphicalNode.prototype, "editorSizeX", {
        get: function () {
            return this.elementRef.nativeElement.parentNode.getBoundingClientRect().width;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalNode.prototype, "editorSizeY", {
        get: function () {
            return this.elementRef.nativeElement.parentNode.getBoundingClientRect().height;
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        core_1.Input(), 
        __metadata('design:type', CEGNode_1.CEGNode)
    ], CEGGraphicalNode.prototype, "node", void 0);
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Boolean)
    ], CEGGraphicalNode.prototype, "selected", void 0);
    CEGGraphicalNode = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: '[ceg-graphical-node]',
            templateUrl: 'ceg-graphical-node.component.svg',
            styleUrls: ['ceg-graphical-node.component.css']
        }), 
        __metadata('design:paramtypes', [d3_ng2_service_1.D3Service, core_1.ElementRef, router_1.Router, router_1.ActivatedRoute])
    ], CEGGraphicalNode);
    return CEGGraphicalNode;
}());
exports.CEGGraphicalNode = CEGGraphicalNode;
//# sourceMappingURL=ceg-graphical-node.component.js.map