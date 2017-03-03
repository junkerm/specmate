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
var common_1 = require('@angular/common');
var forms_1 = require('@angular/forms');
var router_1 = require('@angular/router');
var specmate_data_service_1 = require('../../services/specmate-data.service');
var CEGModel_1 = require('../../model/CEGModel');
var Requirement_1 = require('../../model/Requirement');
var Type_1 = require('../../util/Type');
var Url_1 = require('../../util/Url');
var Id_1 = require('../../util/Id');
var RequirementsCEGEditor = (function () {
    function RequirementsCEGEditor(formBuilder, dataService, route, location) {
        this.formBuilder = formBuilder;
        this.dataService = dataService;
        this.route = route;
        this.location = location;
        this.rows = 5;
        this.createForm();
    }
    RequirementsCEGEditor.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params
            .switchMap(function (params) { return _this.dataService.getDetails(params['url']); })
            .subscribe(function (container) {
            _this.container = container;
            if (Type_1.Type.is(container, CEGModel_1.CEGModel)) {
                _this.model = container;
                _this.isNew = false;
            }
            else if (Type_1.Type.is(container, Requirement_1.Requirement)) {
                _this.model = new CEGModel_1.CEGModel();
                _this.isNew = true;
                _this.updateModel("New");
            }
            _this.setFormValues();
        });
    };
    RequirementsCEGEditor.prototype.createForm = function () {
        var _this = this;
        this.cegForm = this.formBuilder.group({
            name: ['', forms_1.Validators.required],
            description: ''
        });
        this.cegForm.valueChanges.subscribe(function (formModel) {
            _this.updateModel(formModel);
        });
    };
    RequirementsCEGEditor.prototype.updateModel = function (formModel) {
        this.model.name = formModel.name;
        this.model.description = formModel.description;
        if (this.isNew) {
            this.model.id = Id_1.Id.fromName(this.model.name);
            this.model.url = Url_1.Url.build([this.container.url, this.model.id]);
        }
    };
    RequirementsCEGEditor.prototype.setFormValues = function () {
        this.cegForm.setValue({
            name: this.model.name,
            description: this.model.description
        });
    };
    RequirementsCEGEditor.prototype.discard = function () {
        //TODO: Really discard new data and go back. Implement a reset button? Reactive Forms in Angular should help.
        console.log("We do not have reset the values of the model! TODO!");
        this.location.back();
    };
    RequirementsCEGEditor = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'ceg-editor',
            templateUrl: 'requirements-ceg-editor.component.html'
        }), 
        __metadata('design:paramtypes', [forms_1.FormBuilder, specmate_data_service_1.SpecmateDataService, router_1.ActivatedRoute, common_1.Location])
    ], RequirementsCEGEditor);
    return RequirementsCEGEditor;
}());
exports.RequirementsCEGEditor = RequirementsCEGEditor;
//# sourceMappingURL=requirements-ceg-editor.component.js.map