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
var CEGNode_1 = require('../../../model/CEGNode');
var d3_ng2_service_1 = require('d3-ng2-service');
var CEGGraphicalNode = (function () {
    function CEGGraphicalNode(d3Service, elementRef) {
        var _this = this;
        this.d3Service = d3Service;
        this.elementRef = elementRef;
        this.dragging = false;
        this.d3 = d3Service.getD3();
        this.d3.select(this.elementRef.nativeElement).call(this.d3.drag()
            .on('drag', function () { return _this.drag(); }));
    }
    CEGGraphicalNode.prototype.drag = function () {
        this.x += this.d3.event.dx;
        this.y += this.d3.event.dy;
        this.textX += this.d3.event.dx;
        this.textY += this.d3.event.dy;
        console.log(this.elementRef);
    };
    __decorate([
        core_1.Input(), 
        __metadata('design:type', CEGNode_1.CEGNode)
    ], CEGGraphicalNode.prototype, "node", void 0);
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Number)
    ], CEGGraphicalNode.prototype, "x", void 0);
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Number)
    ], CEGGraphicalNode.prototype, "y", void 0);
    CEGGraphicalNode = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: '[ceg-graphical-node]',
            templateUrl: 'ceg-graphical-node.component.svg'
        }), 
        __metadata('design:paramtypes', [d3_ng2_service_1.D3Service, core_1.ElementRef])
    ], CEGGraphicalNode);
    return CEGGraphicalNode;
}());
exports.CEGGraphicalNode = CEGGraphicalNode;
//# sourceMappingURL=ceg-graphical-node.component.js.map