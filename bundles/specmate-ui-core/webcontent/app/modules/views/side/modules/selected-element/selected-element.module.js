"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var selected_element_service_1 = require("./services/selected-element.service");
var SelectedElementModule = (function () {
    function SelectedElementModule() {
    }
    SelectedElementModule = __decorate([
        core_1.NgModule({
            imports: [],
            declarations: [],
            exports: [],
            providers: [
                // SERVICES
                selected_element_service_1.SelectedElementService
            ],
            bootstrap: []
        })
    ], SelectedElementModule);
    return SelectedElementModule;
}());
exports.SelectedElementModule = SelectedElementModule;
//# sourceMappingURL=selected-element.module.js.map