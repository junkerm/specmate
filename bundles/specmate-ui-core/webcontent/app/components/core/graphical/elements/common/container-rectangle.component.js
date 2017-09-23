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
var ContainerRectangle = (function () {
    function ContainerRectangle() {
        this.centerHorizontal = true;
        this.centerVertical = true;
    }
    ContainerRectangle.prototype.linePosition = function (lineNumber) {
        if (!this.lines) {
            return { x: 0, y: 0 };
        }
        var x = this.centerHorizontal ? this.center.x : this.position.x;
        var y = this.centerVertical ? this.center.y - (this.textHeight / 2) : this.position.y;
        var lineArr = this.lines.toArray();
        for (var i = 0; i < lineArr.length && i <= lineNumber; i++) {
            y += lineArr[i].height;
        }
        return { x: x, y: y };
    };
    Object.defineProperty(ContainerRectangle.prototype, "center", {
        get: function () {
            return {
                x: this.position.x + (this.dimensions.width / 2),
                y: this.position.y + (this.dimensions.height / 2)
            };
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ContainerRectangle.prototype, "textHeight", {
        get: function () {
            if (!this.lines) {
                return 0;
            }
            var height = this.lines.reduce(function (accHeight, item) { return accHeight + item.height; }, 0);
            return height;
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        core_1.ViewChildren('line'),
        __metadata("design:type", core_1.QueryList)
    ], ContainerRectangle.prototype, "lines", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Object)
    ], ContainerRectangle.prototype, "position", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Object)
    ], ContainerRectangle.prototype, "dimensions", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Boolean)
    ], ContainerRectangle.prototype, "selected", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Boolean)
    ], ContainerRectangle.prototype, "valid", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Array)
    ], ContainerRectangle.prototype, "text", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Number)
    ], ContainerRectangle.prototype, "cornerRadius", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Boolean)
    ], ContainerRectangle.prototype, "centerHorizontal", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Boolean)
    ], ContainerRectangle.prototype, "centerVertical", void 0);
    ContainerRectangle = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: '[container-rectangle]',
            templateUrl: 'container-rectangle.component.svg',
            styleUrls: ['container-rectangle.component.css']
        })
    ], ContainerRectangle);
    return ContainerRectangle;
}());
exports.ContainerRectangle = ContainerRectangle;
//# sourceMappingURL=container-rectangle.component.js.map