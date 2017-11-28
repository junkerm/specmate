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
var specmate_data_service_1 = require("../data/specmate-data.service");
var navigator_service_1 = require("../navigation/navigator.service");
var tool_provider_1 = require("../../components/core/graphical/providers/tool-provider");
var EditorToolsService = (function () {
    function EditorToolsService(dataService, navigator) {
        this.dataService = dataService;
        this.navigator = navigator;
    }
    Object.defineProperty(EditorToolsService.prototype, "currentElement", {
        get: function () {
            return this.navigator.currentElement;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(EditorToolsService.prototype, "toolProvider", {
        get: function () {
            if (!this.providerMap) {
                this.providerMap = {};
            }
            if (!this.providerMap[this.currentElement.url]) {
                this.providerMap[this.currentElement.url] = new tool_provider_1.ToolProvider(this.currentElement, this.dataService);
            }
            return this.providerMap[this.currentElement.url];
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(EditorToolsService.prototype, "tools", {
        get: function () {
            return this.toolProvider.tools;
        },
        enumerable: true,
        configurable: true
    });
    EditorToolsService.prototype.activate = function (tool) {
    };
    EditorToolsService.prototype.deactivate = function (tool) {
    };
    EditorToolsService.prototype.reset = function () {
    };
    EditorToolsService.prototype.activateDefaultTool = function () {
    };
    EditorToolsService = __decorate([
        core_1.Injectable(),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService, navigator_service_1.NavigatorService])
    ], EditorToolsService);
    return EditorToolsService;
}());
exports.EditorToolsService = EditorToolsService;
//# sourceMappingURL=editor-tools.service.js.map