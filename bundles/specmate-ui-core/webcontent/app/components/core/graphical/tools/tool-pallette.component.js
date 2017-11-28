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
var editor_tools_service_1 = require("../../../../services/editor/editor-tools.service");
var confirmation_modal_service_1 = require("../../../../services/notification/confirmation-modal.service");
var specmate_data_service_1 = require("../../../../services/data/specmate-data.service");
var Id_1 = require("../../../../util/Id");
var element_provider_1 = require("../providers/element-provider");
var navigator_service_1 = require("../../../../services/navigation/navigator.service");
var ToolPallette = (function () {
    function ToolPallette(dataService, editorToolsService, navigator, modal) {
        this.dataService = dataService;
        this.editorToolsService = editorToolsService;
        this.navigator = navigator;
        this.modal = modal;
    }
    Object.defineProperty(ToolPallette.prototype, "model", {
        get: function () {
            return this.navigator.currentElement;
        },
        enumerable: true,
        configurable: true
    });
    ToolPallette.prototype.onClick = function (tool, event) {
        event.preventDefault();
        event.stopPropagation();
        this.activate(tool);
    };
    Object.defineProperty(ToolPallette.prototype, "tools", {
        get: function () {
            return this.editorToolsService.tools;
        },
        enumerable: true,
        configurable: true
    });
    ToolPallette.prototype.isActive = function (tool) {
        return this.editorToolsService.isActive(tool);
    };
    ToolPallette.prototype.activate = function (tool) {
        this.editorToolsService.activate(tool);
    };
    ToolPallette.prototype.delete = function () {
        var _this = this;
        this.modal.open('Do you really want to delete all elements in ' + this.model.name + '?')
            .then(function () { return _this.dataService.readContents(_this.model.url, true); })
            .then(function (contents) { return _this.removeAllElements(contents); })
            .catch(function () { });
    };
    ToolPallette.prototype.removeAllElements = function (contents) {
        var elementProvider = new element_provider_1.ElementProvider(this.model, contents);
        var compoundId = Id_1.Id.uuid;
        for (var i = elementProvider.connections.length - 1; i >= 0; i--) {
            this.dataService.deleteElement(elementProvider.connections[i].url, true, compoundId);
        }
        for (var i = elementProvider.nodes.length - 1; i >= 0; i--) {
            this.dataService.deleteElement(elementProvider.nodes[i].url, true, compoundId);
        }
    };
    Object.defineProperty(ToolPallette.prototype, "isVisible", {
        get: function () {
            return this.tools && this.tools.length > 0;
        },
        enumerable: true,
        configurable: true
    });
    ToolPallette = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'tool-pallette',
            templateUrl: 'tool-pallette.component.html',
            styleUrls: ['tool-pallette.component.css']
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService, editor_tools_service_1.EditorToolsService, navigator_service_1.NavigatorService, confirmation_modal_service_1.ConfirmationModal])
    ], ToolPallette);
    return ToolPallette;
}());
exports.ToolPallette = ToolPallette;
//# sourceMappingURL=tool-pallette.component.js.map