"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var welcome_component_1 = require("./components/welcome.component");
var WelcomePageModule = /** @class */ (function () {
    function WelcomePageModule() {
    }
    WelcomePageModule = __decorate([
        core_1.NgModule({
            imports: [],
            declarations: [
                // COMPONENTS IN THIS MODULE
                welcome_component_1.Welcome
            ],
            exports: [
                // THE COMPONENTS VISIBLE TO THE OUTSIDE
                welcome_component_1.Welcome
            ],
            providers: [],
            bootstrap: []
        })
    ], WelcomePageModule);
    return WelcomePageModule;
}());
exports.WelcomePageModule = WelcomePageModule;
//# sourceMappingURL=welcome-page.module.js.map