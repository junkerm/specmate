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
var ArrowTip = /** @class */ (function () {
    function ArrowTip() {
    }
    __decorate([
        core_1.Input(),
        __metadata("design:type", Object)
    ], ArrowTip.prototype, "position", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Boolean)
    ], ArrowTip.prototype, "selected", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Boolean)
    ], ArrowTip.prototype, "valid", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Number)
    ], ArrowTip.prototype, "angle", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Boolean)
    ], ArrowTip.prototype, "fill", void 0);
    ArrowTip = __decorate([
        core_1.Component({
            moduleId: module.id.toString(),
            selector: '[arrow-tip]',
            templateUrl: 'arrow-tip.component.svg',
            styleUrls: ['arrow-tip.component.css']
        })
    ], ArrowTip);
    return ArrowTip;
}());
exports.ArrowTip = ArrowTip;
//# sourceMappingURL=arrow-tip.component.js.map