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
var Url_1 = require("../../util/Url");
var UrlBreadcrumb = (function () {
    function UrlBreadcrumb() {
    }
    UrlBreadcrumb.prototype.ngOnInit = function () {
        this.setUrlParts();
    };
    UrlBreadcrumb.prototype.ngOnChanges = function () {
        this.setUrlParts();
    };
    UrlBreadcrumb.prototype.setUrlParts = function () {
        this.parts = Url_1.Url.parts(this.url);
    };
    __decorate([
        core_1.Input(),
        __metadata("design:type", String)
    ], UrlBreadcrumb.prototype, "url", void 0);
    UrlBreadcrumb = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'url-breadcrumb',
            templateUrl: 'url-breadcrumb.component.html'
        }),
        __metadata("design:paramtypes", [])
    ], UrlBreadcrumb);
    return UrlBreadcrumb;
}());
exports.UrlBreadcrumb = UrlBreadcrumb;
//# sourceMappingURL=url-breadcrumb.component.js.map