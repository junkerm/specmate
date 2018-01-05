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
var Folder_1 = require("../../../../../model/Folder");
var CEGModel_1 = require("../../../../../model/CEGModel");
var Process_1 = require("../../../../../model/Process");
var Requirement_1 = require("../../../../../model/Requirement");
var TestSpecification_1 = require("../../../../../model/TestSpecification");
var TestProcedure_1 = require("../../../../../model/TestProcedure");
var type_1 = require("../../../../../util/type");
var IconSelector = /** @class */ (function () {
    function IconSelector() {
    }
    Object.defineProperty(IconSelector.prototype, "isFolder", {
        get: function () {
            return type_1.Type.is(this.model, Folder_1.Folder);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(IconSelector.prototype, "isCEGModel", {
        get: function () {
            return type_1.Type.is(this.model, CEGModel_1.CEGModel);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(IconSelector.prototype, "isNonRegressionRequirement", {
        get: function () {
            return type_1.Type.is(this.model, Requirement_1.Requirement) && !this.model.isRegressionRequirement;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(IconSelector.prototype, "isRegressionRequirement", {
        get: function () {
            return type_1.Type.is(this.model, Requirement_1.Requirement) && this.model.isRegressionRequirement;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(IconSelector.prototype, "isTestSpecification", {
        get: function () {
            return type_1.Type.is(this.model, TestSpecification_1.TestSpecification);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(IconSelector.prototype, "isProcess", {
        get: function () {
            return type_1.Type.is(this.model, Process_1.Process);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(IconSelector.prototype, "isManualTestSpecification", {
        get: function () {
            return this.isTestSpecification && !this.isElementChildOfModel();
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(IconSelector.prototype, "isGeneratedTestSpecification", {
        get: function () {
            return this.isTestSpecification && this.isElementChildOfModel();
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(IconSelector.prototype, "isTestProcedure", {
        get: function () {
            return type_1.Type.is(this.model, TestProcedure_1.TestProcedure);
        },
        enumerable: true,
        configurable: true
    });
    IconSelector.prototype.isElementChildOfModel = function () {
        return this.parent && (type_1.Type.is(this.parent, CEGModel_1.CEGModel) || type_1.Type.is(this.parent, Process_1.Process));
    };
    __decorate([
        core_1.Input(),
        __metadata("design:type", Object)
    ], IconSelector.prototype, "model", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Boolean)
    ], IconSelector.prototype, "expanded", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Object)
    ], IconSelector.prototype, "parent", void 0);
    IconSelector = __decorate([
        core_1.Component({
            selector: 'icon-selector',
            moduleId: module.id.toString(),
            templateUrl: 'icon-selector.component.html'
        })
    ], IconSelector);
    return IconSelector;
}());
exports.IconSelector = IconSelector;
//# sourceMappingURL=icon-selector.component.js.map