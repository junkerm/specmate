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
var navigator_service_1 = require("../../../services/navigation/navigator.service");
var Type_1 = require("../../../util/Type");
var TestSpecification_1 = require("../../../model/TestSpecification");
var CEGModel_1 = require("../../../model/CEGModel");
var Process_1 = require("../../../model/Process");
var specmate_data_service_1 = require("../../../services/data/specmate-data.service");
var Url_1 = require("../../../util/Url");
var LinksActions = (function () {
    function LinksActions(dataService, navigator) {
        var _this = this;
        this.dataService = dataService;
        this.navigator = navigator;
        navigator.hasNavigated.subscribe(function (element) {
            if (Type_1.Type.is(element, CEGModel_1.CEGModel) || Type_1.Type.is(element, Process_1.Process)) {
                _this._model = element;
                _this.dataService.readContents(_this._model.url).then(function (contents) { return _this._contents = contents; });
                _this.dataService.readElement(Url_1.Url.parent(_this._model.url)).then(function (element) { return _this._requirement = element; });
            }
            else {
                _this.clear();
            }
        });
    }
    LinksActions.prototype.clear = function () {
        this._model = undefined;
        this._requirement = undefined;
        this._contents = undefined;
    };
    Object.defineProperty(LinksActions.prototype, "model", {
        get: function () {
            return this._model;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(LinksActions.prototype, "requirement", {
        get: function () {
            return this._requirement;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(LinksActions.prototype, "testSpecifications", {
        get: function () {
            if (!this._contents) {
                return undefined;
            }
            return this._contents.filter(function (element) { return Type_1.Type.is(element, TestSpecification_1.TestSpecification); });
        },
        enumerable: true,
        configurable: true
    });
    LinksActions = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'links-actions',
            templateUrl: 'links-actions.component.html',
            styleUrls: ['links-actions.component.css']
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService, navigator_service_1.NavigatorService])
    ], LinksActions);
    return LinksActions;
}());
exports.LinksActions = LinksActions;
//# sourceMappingURL=links-actions.component.js.map