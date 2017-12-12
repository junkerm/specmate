"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var Type = /** @class */ (function () {
    function Type() {
    }
    Type.is = function (o1, o2) {
        if (o1 && o2 && o1.className && o2.className) {
            return o1.className === o2.className;
        }
        return false;
    };
    Type.of = function (o) {
        if (o) {
            return o.className;
        }
        return null;
    };
    return Type;
}());
exports.Type = Type;
var OfTypeNamePipe = /** @class */ (function () {
    function OfTypeNamePipe() {
    }
    OfTypeNamePipe.prototype.transform = function (objs, typeName) {
        if (objs) {
            return objs.filter(function (o) { return Type.of(o) === typeName; });
        }
        return [];
    };
    OfTypeNamePipe = __decorate([
        core_1.Pipe({ name: 'ofTypeName', pure: false })
    ], OfTypeNamePipe);
    return OfTypeNamePipe;
}());
exports.OfTypeNamePipe = OfTypeNamePipe;
var OfTypePipe = /** @class */ (function () {
    function OfTypePipe() {
    }
    OfTypePipe.prototype.transform = function (objs, type) {
        if (objs) {
            return objs.filter(function (o) { return Type.is(o, type); });
        }
        return [];
    };
    OfTypePipe = __decorate([
        core_1.Pipe({ name: 'ofType', pure: false })
    ], OfTypePipe);
    return OfTypePipe;
}());
exports.OfTypePipe = OfTypePipe;
//# sourceMappingURL=type.js.map