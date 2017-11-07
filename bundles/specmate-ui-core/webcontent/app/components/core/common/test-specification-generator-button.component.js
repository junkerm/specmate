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
var test_specification_generator_1 = require("./test-specification-generator");
var core_1 = require("@angular/core");
var TestSpecificationGeneratorButton = (function () {
    function TestSpecificationGeneratorButton() {
    }
    TestSpecificationGeneratorButton.prototype.generate = function () {
        this.generator.generateTestSpecification(this.model);
    };
    Object.defineProperty(TestSpecificationGeneratorButton.prototype, "enabled", {
        get: function () {
            return this.generator.canCreateTestSpecification(this.model);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TestSpecificationGeneratorButton.prototype, "errors", {
        get: function () {
            return this.generator.getErrors(this.model);
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        core_1.Input(),
        __metadata("design:type", test_specification_generator_1.TestSpecificationGenerator)
    ], TestSpecificationGeneratorButton.prototype, "generator", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Object)
    ], TestSpecificationGeneratorButton.prototype, "model", void 0);
    TestSpecificationGeneratorButton = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'test-specification-generator-button',
            templateUrl: 'test-specification-generator-button.component.html',
            styleUrls: ['test-specification-generator-button.component.css']
        })
    ], TestSpecificationGeneratorButton);
    return TestSpecificationGeneratorButton;
}());
exports.TestSpecificationGeneratorButton = TestSpecificationGeneratorButton;
//# sourceMappingURL=test-specification-generator-button.component.js.map