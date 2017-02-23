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
var specmate_data_service_1 = require('../../services/specmate-data.service');
var ElementTree = (function () {
    function ElementTree(dataService) {
        this.dataService = dataService;
        this.element = {};
        this.expanded = false;
    }
    ElementTree.prototype.ngOnInit = function () {
        var _this = this;
        this.dataService.getContent(this.baseUrl).then(function (element) { _this.element = element; });
        this.dataService.getChildren(this.baseUrl).then(function (children) { _this.elements = children; });
    };
    ElementTree.prototype.toggle = function () {
        this.expanded = !this.expanded;
    };
    __decorate([
        core_1.Input(), 
        __metadata('design:type', String)
    ], ElementTree.prototype, "baseUrl", void 0);
    ElementTree = __decorate([
        core_1.Component({
            moduleId: module.id,
            templateUrl: 'element-tree.component.html',
            selector: 'element-tree'
        }), 
        __metadata('design:paramtypes', [specmate_data_service_1.SpecmateDataService])
    ], ElementTree);
    return ElementTree;
}());
exports.ElementTree = ElementTree;
//# sourceMappingURL=element-tree.component.js.map