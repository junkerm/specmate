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
var selected_element_service_1 = require("../../../services/editor/selected-element.service");
var hidden_fields_provider_1 = require("../graphical/providers/hidden-fields-provider");
var PropertiesEditor = (function () {
    function PropertiesEditor(selectedElementService) {
        var _this = this;
        this.selectedElementService = selectedElementService;
        selectedElementService.selectionChanged.subscribe(function (element) {
            _this.hiddenFieldsProvider = new hidden_fields_provider_1.HiddenFieldsProvider(element);
            _this._selectedElement = element;
        });
    }
    Object.defineProperty(PropertiesEditor.prototype, "selectedElement", {
        get: function () {
            return this._selectedElement;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(PropertiesEditor.prototype, "hiddenFields", {
        get: function () {
            if (!this.hiddenFieldsProvider) {
                return undefined;
            }
            return this.hiddenFieldsProvider.hiddenFields;
        },
        enumerable: true,
        configurable: true
    });
    PropertiesEditor = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'properties-editor',
            templateUrl: 'properties-editor.component.html',
            styleUrls: ['properties-editor.component.css']
        }),
        __metadata("design:paramtypes", [selected_element_service_1.SelectedElementService])
    ], PropertiesEditor);
    return PropertiesEditor;
}());
exports.PropertiesEditor = PropertiesEditor;
//# sourceMappingURL=properties-editor.component.js.map