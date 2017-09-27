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
var generic_form_component_1 = require("../../forms/generic-form.component");
var core_1 = require("@angular/core");
var Type_1 = require("../../../util/Type");
var CEGNode_1 = require("../../../model/CEGNode");
var CEGConnection_1 = require("../../../model/CEGConnection");
var GraphicalElementDetails = (function () {
    function GraphicalElementDetails() {
    }
    Object.defineProperty(GraphicalElementDetails.prototype, "isNode", {
        get: function () {
            return Type_1.Type.is(this.element, CEGNode_1.CEGNode);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalElementDetails.prototype, "isConnection", {
        get: function () {
            return Type_1.Type.is(this.element, CEGConnection_1.CEGConnection);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalElementDetails.prototype, "isValid", {
        get: function () {
            if (!this.form) {
                return true;
            }
            return this.form.isValid;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalElementDetails.prototype, "hiddenFields", {
        get: function () {
            if (this.hasMoreThanOneIncomingConnections) {
                return [];
            }
            return ['type'];
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalElementDetails.prototype, "hasMoreThanOneIncomingConnections", {
        get: function () {
            if (!this.element || !this.element.incomingConnections) {
                return false;
            }
            return this.element.incomingConnections.length > 1;
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        core_1.Input(),
        __metadata("design:type", CEGNode_1.CEGNode)
    ], GraphicalElementDetails.prototype, "element", void 0);
    __decorate([
        core_1.ViewChild(generic_form_component_1.GenericForm),
        __metadata("design:type", generic_form_component_1.GenericForm)
    ], GraphicalElementDetails.prototype, "form", void 0);
    GraphicalElementDetails = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'graphical-element-details',
            templateUrl: 'graphical-element-details.component.html'
        }),
        __metadata("design:paramtypes", [])
    ], GraphicalElementDetails);
    return GraphicalElementDetails;
}());
exports.GraphicalElementDetails = GraphicalElementDetails;
//# sourceMappingURL=graphical-element-details.component.js.map