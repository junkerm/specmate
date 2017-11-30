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
var navigator_service_1 = require("../navigation/navigator.service");
var Type_1 = require("../../util/Type");
var Requirement_1 = require("../../model/Requirement");
var SelectedElementService = (function () {
    function SelectedElementService(navigator) {
        var _this = this;
        this.navigator = navigator;
        navigator.hasNavigated.subscribe(function (element) {
            if (_this.isSelectable(element)) {
                _this.select(element);
            }
            else {
                _this.deselect();
            }
        });
    }
    Object.defineProperty(SelectedElementService.prototype, "selectionChanged", {
        get: function () {
            if (!this._selectionChanged) {
                this._selectionChanged = new core_1.EventEmitter();
            }
            return this._selectionChanged;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(SelectedElementService.prototype, "hasSelection", {
        get: function () {
            return this.selectedElement !== undefined;
        },
        enumerable: true,
        configurable: true
    });
    SelectedElementService.prototype.isSelected = function (element) {
        return this.selectedElement === element;
    };
    Object.defineProperty(SelectedElementService.prototype, "selectedElement", {
        get: function () {
            return this._selectedElement;
        },
        set: function (selectedElement) {
            this._selectedElement = selectedElement;
            this.selectionChanged.emit(this.selectedElement);
        },
        enumerable: true,
        configurable: true
    });
    SelectedElementService.prototype.deselect = function () {
        this.selectedElement = undefined;
    };
    SelectedElementService.prototype.select = function (element) {
        this.selectedElement = element;
    };
    SelectedElementService.prototype.isSelectable = function (element) {
        return !Type_1.Type.is(element, Requirement_1.Requirement);
    };
    SelectedElementService = __decorate([
        core_1.Injectable(),
        __metadata("design:paramtypes", [navigator_service_1.NavigatorService])
    ], SelectedElementService);
    return SelectedElementService;
}());
exports.SelectedElementService = SelectedElementService;
//# sourceMappingURL=selected-element.service.js.map