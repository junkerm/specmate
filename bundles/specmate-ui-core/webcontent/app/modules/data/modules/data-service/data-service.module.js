"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var specmate_data_service_1 = require("./services/specmate-data.service");
var http_1 = require("@angular/common/http");
var log_list_module_1 = require("../../../views/side/modules/log-list/log-list.module");
var DataServiceModule = /** @class */ (function () {
    function DataServiceModule() {
    }
    DataServiceModule = __decorate([
        core_1.NgModule({
            imports: [
                // MODULE IMPORTS
                http_1.HttpClientModule,
                log_list_module_1.LogListModule
            ],
            declarations: [],
            exports: [],
            providers: [
                // SERVICES
                specmate_data_service_1.SpecmateDataService
            ],
            bootstrap: []
        })
    ], DataServiceModule);
    return DataServiceModule;
}());
exports.DataServiceModule = DataServiceModule;
//# sourceMappingURL=data-service.module.js.map