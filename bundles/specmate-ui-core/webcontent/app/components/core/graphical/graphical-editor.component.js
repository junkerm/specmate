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
var confirmation_modal_service_1 = require("../../../services/notification/confirmation-modal.service");
var Type_1 = require("../../../util/Type");
var core_1 = require("@angular/core");
var config_1 = require("../../../config/config");
var CEGModel_1 = require("../../../model/CEGModel");
var specmate_data_service_1 = require("../../../services/data/specmate-data.service");
var element_provider_1 = require("./providers/element-provider");
var name_provider_1 = require("./providers/name-provider");
var Process_1 = require("../../../model/Process");
var tool_provider_1 = require("./providers/tool-provider");
var editor_tools_service_1 = require("../../../services/editor/editor-tools.service");
var selected_element_service_1 = require("../../../services/editor/selected-element.service");
var validation_service_1 = require("../../../services/validation/validation.service");
var view_controller_service_1 = require("../../../services/view/view-controller.service");
var GraphicalEditor = (function () {
    function GraphicalEditor(dataService, modal, editorToolsService, selectedElementService, validationService, viewController) {
        this.dataService = dataService;
        this.modal = modal;
        this.editorToolsService = editorToolsService;
        this.selectedElementService = selectedElementService;
        this.validationService = validationService;
        this.viewController = viewController;
        this.isGridShown = true;
        this.zoom = 1;
    }
    Object.defineProperty(GraphicalEditor.prototype, "model", {
        get: function () {
            return this._model;
        },
        set: function (model) {
            this.toolProvider = new tool_provider_1.ToolProvider(model, this.dataService, this.selectedElementService);
            this.nameProvider = new name_provider_1.NameProvider(model);
            this._model = model;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalEditor.prototype, "contents", {
        get: function () {
            return this._contents;
        },
        set: function (contents) {
            this._contents = contents;
            this.elementProvider = new element_provider_1.ElementProvider(this.model, this._contents);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalEditor.prototype, "isValid", {
        get: function () {
            return this.validationService.isValid(this.model) && this.validationService.allValid(this.contents);
        },
        enumerable: true,
        configurable: true
    });
    GraphicalEditor.prototype.maximize = function () {
        this.viewController.maximizeEditor();
    };
    GraphicalEditor.prototype.unmaximize = function () {
        this.viewController.unmaximizeEditor();
    };
    Object.defineProperty(GraphicalEditor.prototype, "isMaximized", {
        get: function () {
            return this.viewController.isEditorMaximized;
        },
        enumerable: true,
        configurable: true
    });
    GraphicalEditor.prototype.zoomIn = function () {
        if (this.canZoomIn) {
            this.zoom += config_1.Config.GRAPHICAL_EDITOR_ZOOM_STEP;
        }
    };
    GraphicalEditor.prototype.zoomOut = function () {
        if (this.canZoomOut) {
            this.zoom -= config_1.Config.GRAPHICAL_EDITOR_ZOOM_STEP;
        }
    };
    GraphicalEditor.prototype.resetZoom = function () {
        this.zoom = 1;
    };
    Object.defineProperty(GraphicalEditor.prototype, "canZoomIn", {
        get: function () {
            return this.zoom < config_1.Config.GRAPHICAL_EDITOR_ZOOM_MAX;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalEditor.prototype, "canZoomOut", {
        get: function () {
            return this.zoom > config_1.Config.GRAPHICAL_EDITOR_ZOOM_STEP * 2;
        },
        enumerable: true,
        configurable: true
    });
    GraphicalEditor.prototype.showGrid = function () {
        this.isGridShown = true;
    };
    GraphicalEditor.prototype.hideGrid = function () {
        this.isGridShown = false;
    };
    Object.defineProperty(GraphicalEditor.prototype, "editorDimensions", {
        get: function () {
            var dynamicWidth = config_1.Config.GRAPHICAL_EDITOR_WIDTH;
            var dynamicHeight = config_1.Config.GRAPHICAL_EDITOR_HEIGHT;
            var nodes = this.contents.filter(function (element) {
                return element.x !== undefined && element.y !== undefined;
            });
            for (var i = 0; i < nodes.length; i++) {
                var nodeX = nodes[i].x + (config_1.Config.GRAPHICAL_EDITOR_PADDING_HORIZONTAL);
                if (dynamicWidth < nodeX) {
                    dynamicWidth = nodeX;
                }
                var nodeY = nodes[i].y + (config_1.Config.GRAPHICAL_EDITOR_PADDING_VERTICAL);
                if (dynamicHeight < nodeY) {
                    dynamicHeight = nodeY;
                }
            }
            return { width: dynamicWidth, height: dynamicHeight };
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalEditor.prototype, "gridSize", {
        get: function () {
            return config_1.Config.GRAPHICAL_EDITOR_GRID_SPACE;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalEditor.prototype, "connections", {
        get: function () {
            if (!this.elementProvider) {
                return [];
            }
            return this.elementProvider.connections;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalEditor.prototype, "nodes", {
        get: function () {
            if (!this.elementProvider) {
                return [];
            }
            return this.elementProvider.nodes;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalEditor.prototype, "name", {
        get: function () {
            if (!this.nameProvider) {
                return '';
            }
            return this.nameProvider.name;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalEditor.prototype, "cursor", {
        get: function () {
            return this.editorToolsService.cursor;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalEditor.prototype, "isCEGModel", {
        get: function () {
            return Type_1.Type.is(this.model, CEGModel_1.CEGModel);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalEditor.prototype, "isProcessModel", {
        get: function () {
            return Type_1.Type.is(this.model, Process_1.Process);
        },
        enumerable: true,
        configurable: true
    });
    GraphicalEditor.prototype.select = function (element, event) {
        var _this = this;
        event.preventDefault();
        event.stopPropagation();
        if (this.editorToolsService.activeTool) {
            this.editorToolsService.activeTool.select(element).then(function () {
                if (_this.editorToolsService.activeTool.done) {
                    _this.editorToolsService.activateDefaultTool();
                }
            });
        }
    };
    GraphicalEditor.prototype.click = function (evt) {
        var _this = this;
        this.selectedElementService.selectedElement = this.model;
        if (this.editorToolsService.activeTool) {
            this.editorToolsService.activeTool.click(evt, this.zoom).then(function () {
                if (_this.editorToolsService.activeTool.done) {
                    _this.editorToolsService.activateDefaultTool();
                }
            });
        }
    };
    __decorate([
        core_1.Input(),
        __metadata("design:type", Object),
        __metadata("design:paramtypes", [Object])
    ], GraphicalEditor.prototype, "model", null);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Array),
        __metadata("design:paramtypes", [Array])
    ], GraphicalEditor.prototype, "contents", null);
    GraphicalEditor = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'graphical-editor',
            templateUrl: 'graphical-editor.component.html',
            styleUrls: ['graphical-editor.component.css'],
            changeDetection: core_1.ChangeDetectionStrategy.Default
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService, confirmation_modal_service_1.ConfirmationModal, editor_tools_service_1.EditorToolsService, selected_element_service_1.SelectedElementService, validation_service_1.ValidationService, view_controller_service_1.ViewControllerService])
    ], GraphicalEditor);
    return GraphicalEditor;
}());
exports.GraphicalEditor = GraphicalEditor;
//# sourceMappingURL=graphical-editor.component.js.map