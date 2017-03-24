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
var core_1 = require('@angular/core');
var forms_1 = require('@angular/forms');
var router_1 = require('@angular/router');
var config_1 = require('../../../config/config');
var specmate_data_service_1 = require('../../../services/specmate-data.service');
var CEGNode_1 = require('../../../model/CEGNode');
var CEGCauseNode_1 = require('../../../model/CEGCauseNode');
var CEGEffectNode_1 = require('../../../model/CEGEffectNode');
var Url_1 = require('../../../util/Url');
var Type_1 = require('../../../util/Type');
var confirmation_modal_service_1 = require("../../core/confirmation-modal.service");
var ModelEditor = (function () {
    function ModelEditor(formBuilder, dataService, router, route, modal) {
        this.formBuilder = formBuilder;
        this.dataService = dataService;
        this.router = router;
        this.route = route;
        this.modal = modal;
        this.rows = config_1.Config.CEG_EDITOR_DESCRIPTION_ROWS;
        this.editorHeight = (isNaN(window.innerHeight) ? window['clientHeight'] : window.innerHeight) * 0.75;
        this.createForm();
    }
    ModelEditor.prototype.ngOnInit = function () {
        var _this = this;
        this.dataService.clearCommits();
        this.route.params
            .switchMap(function (params) {
            return _this.dataService.readElement(params['url']);
        })
            .subscribe(function (model) {
            _this.model = model;
            _this.dataService.readContents(_this.model.url).then(function (contents) {
                _this.contents = contents;
                _this.setFormValues();
            });
        });
    };
    ModelEditor.prototype.createForm = function () {
        this.cegForm = this.formBuilder.group({
            name: ['', forms_1.Validators.required],
            description: ''
        });
    };
    ModelEditor.prototype.updateModel = function () {
        var name = this.cegForm.controls['name'].value;
        var description = this.cegForm.controls['description'].value;
        if (!description) {
            description = '';
        }
        if (name) {
            this.model.name = name;
            this.model.description = description;
        }
    };
    ModelEditor.prototype.setFormValues = function () {
        this.cegForm.setValue({
            name: this.model.name,
            description: this.model.description
        });
    };
    ModelEditor.prototype.save = function () {
        this.updateModel();
        this.dataService.updateElement(this.model, true);
        // We need to update all nodes to save new positions.
        for (var i = 0; i < this.contents.length; i++) {
            var currentElement = this.contents[i];
            if (Type_1.Type.is(currentElement, CEGNode_1.CEGNode) || Type_1.Type.is(currentElement, CEGCauseNode_1.CEGCauseNode) || Type_1.Type.is(currentElement, CEGEffectNode_1.CEGEffectNode)) {
                this.dataService.updateElement(this.contents[i], true);
            }
        }
        this.dataService.commit('Save');
    };
    ModelEditor.prototype.delete = function () {
        var _this = this;
        this.modal.open("Do you really want to delete?")
            .then(function () { return _this.dataService.clearCommits(); })
            .then(function () { return _this.dataService.deleteElement(_this.model.url); })
            .then(function () {
            return _this.dataService.commit('Delete');
        })
            .then(function () {
            _this.router.navigate(['/requirements', { outlets: { 'main': [Url_1.Url.parent(_this.model.url)] } }]);
        }).catch(function () { });
    };
    ModelEditor.prototype.discard = function () {
        this.doDiscard().catch(function () { });
    };
    ModelEditor.prototype.doDiscard = function () {
        var _this = this;
        return this.modal.open("Unsaved changes are discarded! Continue?")
            .then(function () { return _this.dataService.clearCommits(); })
            .then(function () { return _this.dataService.readElement(_this.model.url); })
            .then(function (model) {
            _this.model = model;
            _this.setFormValues();
        })
            .then(function () {
            return _this.dataService.readContents(_this.model.url);
        })
            .then(function (contents) {
            _this.contents = contents;
        });
    };
    ModelEditor.prototype.close = function () {
        var _this = this;
        this.doDiscard().then(function () { return _this.router.navigate(['/requirements', { outlets: { 'main': [Url_1.Url.parent(_this.model.url)] } }]); }).catch(function () { });
    };
    ModelEditor = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'model-editor',
            templateUrl: 'model-editor.component.html'
        }), 
        __metadata('design:paramtypes', [forms_1.FormBuilder, specmate_data_service_1.SpecmateDataService, router_1.Router, router_1.ActivatedRoute, confirmation_modal_service_1.ConfirmationModal])
    ], ModelEditor);
    return ModelEditor;
}());
exports.ModelEditor = ModelEditor;
//# sourceMappingURL=model-editor.component.js.map