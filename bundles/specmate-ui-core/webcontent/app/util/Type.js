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
var Type = (function () {
    function Type() {
    }
    Type.is = function (o1, o2) {
        if (o1 && o2 && o1.className && o2.className) {
            return o1.className === o2.className;
        }
        return false;
    };
    Type.getTypeName = function (o) {
        if (o) {
            return o.className;
        }
        return null;
    };
    return Type;
}());
exports.Type = Type;
var OfTypeNamePipe = (function () {
    function OfTypeNamePipe() {
    }
    OfTypeNamePipe.prototype.transform = function (objs, typeName) {
        return objs.filter(function (o) { return Type.getTypeName(o) === typeName; });
    };
    OfTypeNamePipe = __decorate([
        core_1.Pipe({ name: 'ofTypeName' }), 
        __metadata('design:paramtypes', [])
    ], OfTypeNamePipe);
    return OfTypeNamePipe;
}());
exports.OfTypeNamePipe = OfTypeNamePipe;
var OfTypePipe = (function () {
    function OfTypePipe() {
    }
    OfTypePipe.prototype.transform = function (objs, type) {
        return objs.filter(function (o) { return Type.is(o, type); });
    };
    OfTypePipe = __decorate([
        core_1.Pipe({ name: 'ofType' }), 
        __metadata('design:paramtypes', [])
    ], OfTypePipe);
    return OfTypePipe;
}());
exports.OfTypePipe = OfTypePipe;
//# sourceMappingURL=Type.js.map