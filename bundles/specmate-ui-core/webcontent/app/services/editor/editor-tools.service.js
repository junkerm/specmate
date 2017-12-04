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
var selected_element_service_1 = require("./selected-element.service");
var EditorToolsService = (function () {
    function EditorToolsService(dataService, navigator, selectedElementService) {
        var _this = this;
        this.dataService = dataService;
        this.navigator = navigator;
        this.selectedElementService = selectedElementService;
        this.init(this.navigator.currentElement);
        this.navigator.hasNavigated.subscribe(function (model) { return _this.init(model); });
    }
    EditorToolsService.prototype.init = function (model) {
        if (!model) {
            return;
        }
        this.model = model;
        this.activateDefaultTool();
    };
    Object.defineProperty(EditorToolsService.prototype, "toolProvider", {
        get: function () {
            if (!this.model) {
                return undefined;
            }
            if (!this.providerMap) {
                this.providerMap = {};
            }
            if (!this.providerMap[this.model.url]) {
                this.providerMap[this.model.url] = new tool_provider_1.ToolProvider(this.model, this.dataService, this.selectedElementService);
            }
            return this.providerMap[this.model.url];
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(EditorToolsService.prototype, "tools", {
        get: function () {
            if (!this.toolProvider) {
                return undefined;
            }
            return this.toolProvider.tools;
        },
        enumerable: true,
        configurable: true
    });
    EditorToolsService.prototype.isActive = function (tool) {
        return this.activeTool === tool;
    };
    EditorToolsService.prototype.activate = function (tool) {
        if (!tool) {
            return;
        }
        if (this.activeTool) {
            this.activeTool.deactivate();
        }
        this.activeTool = tool;
        this.activeTool.activate();
    };
    EditorToolsService.prototype.deactivate = function (tool) {
        tool.deactivate();
    };
    EditorToolsService.prototype.reset = function () {
        if (this.activeTool) {
            this.activeTool.deactivate();
            this.activeTool.activate();
        }
    };
    EditorToolsService.prototype.activateDefaultTool = function () {
        var _this = this;
        this.dataService.readContents(this.model.url, true)
            .then(function (contents) {
            var defaultTool = _this.toolProvider.getDefaultTool(contents);
            _this.activate(defaultTool);
        });
    };
    Object.defineProperty(EditorToolsService.prototype, "cursor", {
        get: function () {
            if (this.activeTool) {
                return this.activeTool.cursor;
            }
            return 'auto';
        },
        enumerable: true,
        configurable: true
    });
    EditorToolsService = __decorate([
        core_1.Injectable(),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService, navigator_service_1.NavigatorService, selected_element_service_1.SelectedElementService])
    ], EditorToolsService);
    return EditorToolsService;
}());
exports.EditorToolsService = EditorToolsService;
//# sourceMappingURL=editor-tools.service.js.map