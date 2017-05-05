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
var ceg_editor_component_1 = require('./ceg-editor.component');
var core_1 = require('@angular/core');
var router_1 = require('@angular/router');
var specmate_data_service_1 = require('../../../services/specmate-data.service');
var CEGNode_1 = require('../../../model/CEGNode');
var CEGCauseNode_1 = require('../../../model/CEGCauseNode');
var CEGEffectNode_1 = require('../../../model/CEGEffectNode');
var CEGConnection_1 = require('../../../model/CEGConnection');
var Url_1 = require('../../../util/Url');
var Type_1 = require('../../../util/Type');
require('rxjs/add/operator/switchMap');
require('rxjs/add/operator/reduce');
var generic_form_component_1 = require("../../core/forms/generic-form.component");
var confirmation_modal_service_1 = require("../../core/forms/confirmation-modal.service");
var ModelEditor = (function () {
    function ModelEditor(dataService, router, route, modal) {
        this.dataService = dataService;
        this.router = router;
        this.route = route;
        this.modal = modal;
    }
    ModelEditor.prototype.ngOnInit = function () {
        var _this = this;
        this.dataService.clearCommits();
        this.route.params
            .switchMap(function (params) { return _this.dataService.readElement(Url_1.Url.fromParams(params)); })
            .subscribe(function (model) {
            _this.model = model;
            _this.dataService.readContents(_this.model.url).then(function (contents) {
                _this.contents = contents;
            });
        });
    };
    Object.defineProperty(ModelEditor.prototype, "nodes", {
        get: function () {
            return this.contents.filter(function (element) { return Type_1.Type.is(element, CEGNode_1.CEGNode) || Type_1.Type.is(element, CEGCauseNode_1.CEGCauseNode) || Type_1.Type.is(element, CEGEffectNode_1.CEGEffectNode); });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ModelEditor.prototype, "connections", {
        get: function () {
            return this.contents.filter(function (element) { return Type_1.Type.is(element, CEGConnection_1.CEGConnection); });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ModelEditor.prototype, "isValid", {
        get: function () {
            if (!this.cegEditor || !this.form) {
                return true;
            }
            return this.cegEditor.isValid && this.form.isValid;
        },
        enumerable: true,
        configurable: true
    });
    ModelEditor.prototype.save = function () {
        if (!this.isValid) {
            return;
        }
        // We need to update all nodes to save new positions.
        for (var i = 0; i < this.nodes.length; i++) {
            this.dataService.updateElement(this.nodes[i], true);
        }
        this.dataService.commit('Save');
    };
    ModelEditor.prototype.delete = function () {
        var _this = this;
        this.modal.open('Do you really want to delete all elements in ' + this.model.name + '?')
            .then(function () { return _this.removeAllElements(); })
            .catch(function () { });
    };
    ModelEditor.prototype.removeAllElements = function () {
        for (var i = this.connections.length - 1; i >= 0; i--) {
            this.dataService.deleteElement(this.connections[i].url, true);
        }
        for (var i = this.nodes.length - 1; i >= 0; i--) {
            this.dataService.deleteElement(this.nodes[i].url, true);
        }
    };
    ModelEditor.prototype.doDiscard = function () {
        var _this = this;
        var first = Promise.resolve();
        if (this.dataService.hasCommits) {
            first = this.modal.open(this.dataService.countCommits + ' unsaved changes are discarded! Continue?');
        }
        return first
            .then(function () { return _this.dataService.clearCommits(); })
            .then(function () { return _this.dataService.readElement(_this.model.url); })
            .then(function (model) { return _this.model = model; })
            .then(function () { return _this.dataService.readContents(_this.model.url); })
            .then(function (contents) { return _this.contents = contents; })
            .then(function () { return _this.cegEditor.reset(); });
    };
    ModelEditor.prototype.close = function () {
        var _this = this;
        this.doDiscard().then(function () { return _this.navigateToRequirement(); }).catch(function () { });
    };
    ModelEditor.prototype.navigateToRequirement = function () {
        this.router.navigate(['/requirements', { outlets: { 'main': [Url_1.Url.parent(this.model.url)] } }]);
    };
    __decorate([
        core_1.ViewChild(ceg_editor_component_1.CEGEditor), 
        __metadata('design:type', ceg_editor_component_1.CEGEditor)
    ], ModelEditor.prototype, "cegEditor", void 0);
    __decorate([
        core_1.ViewChild(generic_form_component_1.GenericForm), 
        __metadata('design:type', generic_form_component_1.GenericForm)
    ], ModelEditor.prototype, "form", void 0);
    ModelEditor = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'model-editor',
            templateUrl: 'model-editor.component.html'
        }), 
        __metadata('design:paramtypes', [specmate_data_service_1.SpecmateDataService, router_1.Router, router_1.ActivatedRoute, confirmation_modal_service_1.ConfirmationModal])
    ], ModelEditor);
    return ModelEditor;
}());
exports.ModelEditor = ModelEditor;
//# sourceMappingURL=model-editor.component.js.map