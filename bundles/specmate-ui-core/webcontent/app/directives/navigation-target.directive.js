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
var navigator_service_1 = require("../services/navigator.service");
var NavigationTargetDirective = (function () {
    function NavigationTargetDirective(elementRef, navigatorService) {
        this.elementRef = elementRef;
        this.navigatorService = navigatorService;
        elementRef.nativeElement.href = '';
    }
    NavigationTargetDirective.prototype.onClick = function (e) {
        e.preventDefault();
        this.navigatorService.navigate(this.target);
    };
    __decorate([
        core_1.Input('navigationTarget'),
        __metadata("design:type", Object)
    ], NavigationTargetDirective.prototype, "target", void 0);
    __decorate([
        core_1.HostListener('click', ['$event']),
        __metadata("design:type", Function),
        __metadata("design:paramtypes", [Event]),
        __metadata("design:returntype", void 0)
    ], NavigationTargetDirective.prototype, "onClick", null);
    NavigationTargetDirective = __decorate([
        core_1.Directive({ selector: '[navigationTarget]' }),
        __metadata("design:paramtypes", [core_1.ElementRef, navigator_service_1.NavigatorService])
    ], NavigationTargetDirective);
    return NavigationTargetDirective;
}());
exports.NavigationTargetDirective = NavigationTargetDirective;
//# sourceMappingURL=navigation-target.directive.js.map