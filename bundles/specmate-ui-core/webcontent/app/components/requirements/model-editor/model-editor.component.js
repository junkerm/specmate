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
var Url_1 = require('../../../util/Url');
require('rxjs/add/operator/switchMap');
require('rxjs/add/operator/reduce');
var generic_form_component_1 = require("../../core/forms/generic-form.component");
var editor_common_control_service_1 = require('../../../services/editor-common-control.service');
var ModelEditor = (function () {
    function ModelEditor(dataService, router, route, editorCommonControlService) {
        this.dataService = dataService;
        this.router = router;
        this.route = route;
        this.editorCommonControlService = editorCommonControlService;
    }
    ModelEditor.prototype.ngOnInit = function () {
        var _this = this;
        this.editorCommonControlService.showCommonControls = true;
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
    ModelEditor.prototype.ngDoCheck = function (args) {
        this.editorCommonControlService.isCurrentEditorValid = this.isValid;
    };
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
        __metadata('design:paramtypes', [specmate_data_service_1.SpecmateDataService, router_1.Router, router_1.ActivatedRoute, editor_common_control_service_1.EditorCommonControlService])
    ], ModelEditor);
    return ModelEditor;
}());
exports.ModelEditor = ModelEditor;
//# sourceMappingURL=model-editor.component.js.map