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
var config_1 = require('../../../config/config');
var specmate_data_service_1 = require('../../../services/specmate-data.service');
var CEGModel_1 = require('../../../model/CEGModel');
var CEGNode_1 = require('../../../model/CEGNode');
var CEGConnection_1 = require('../../../model/CEGConnection');
var Requirement_1 = require('../../../model/Requirement');
var Type_1 = require('../../../util/Type');
var Url_1 = require('../../../util/Url');
var Id_1 = require('../../../util/Id');
var Tools;
(function (Tools) {
    Tools[Tools["NODE"] = 0] = "NODE";
    Tools[Tools["CONNECTION"] = 1] = "CONNECTION";
    Tools[Tools["DELETE"] = 2] = "DELETE";
    Tools[Tools["MOVE"] = 3] = "MOVE";
})(Tools || (Tools = {}));
var CEGEditor = (function () {
    function CEGEditor(formBuilder, dataService, router, route, location, changeDetectorRef) {
        this.formBuilder = formBuilder;
        this.dataService = dataService;
        this.router = router;
        this.route = route;
        this.location = location;
        this.changeDetectorRef = changeDetectorRef;
        this.rows = config_1.Config.CEG_EDITOR_DESCRIPTION_ROWS;
        this.editorHeight = (isNaN(window.innerHeight) ? window['clientHeight'] : window.innerHeight) * 0.75;
        this.nodeType = CEGNode_1.CEGNode;
        this.connectionType = CEGConnection_1.CEGConnection;
        this.activeTool = Tools.MOVE;
        this.selectedConnectionNodes = [];
        this.createForm();
    }
    CEGEditor.prototype.activateNodeAdder = function () {
        this.activeTool = Tools.NODE;
    };
    CEGEditor.prototype.activateConnectionAdder = function () {
        this.selectedConnectionNodes = [];
        this.selectedNode = undefined;
        this.activeTool = Tools.CONNECTION;
    };
    CEGEditor.prototype.activateDeleter = function () {
        this.activeTool = Tools.DELETE;
    };
    CEGEditor.prototype.activateMover = function () {
        this.activeTool = Tools.MOVE;
    };
    Object.defineProperty(CEGEditor.prototype, "isActiveNodeAdder", {
        get: function () {
            return this.isActive(Tools.NODE);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGEditor.prototype, "isActiveConnectionAdder", {
        get: function () {
            return this.isActive(Tools.CONNECTION);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGEditor.prototype, "isActiveDeleter", {
        get: function () {
            return this.isActive(Tools.DELETE);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGEditor.prototype, "isActiveMover", {
        get: function () {
            return this.isActive(Tools.MOVE);
        },
        enumerable: true,
        configurable: true
    });
    CEGEditor.prototype.isActive = function (tool) {
        return this.activeTool === tool;
    };
    CEGEditor.prototype.isSelected = function (node) {
        return node === this.selectedNode || this.selectedConnectionNodes.indexOf(node) >= 0;
    };
    CEGEditor.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params
            .switchMap(function (params) { return _this.dataService.getDetails(params['url']); })
            .subscribe(function (container) {
            _this.container = container;
            if (Type_1.Type.is(container, CEGModel_1.CEGModel)) {
                _this.model = container;
                _this.isNew = false;
                _this.setFormValues();
            }
            else if (Type_1.Type.is(container, Requirement_1.Requirement)) {
                _this.model = new CEGModel_1.CEGModel();
                _this.model.name = config_1.Config.CEG_NEW_NAME;
                _this.model.description = config_1.Config.CEG_NEW_DESCRIPTION;
                _this.model['contents'] = [];
                _this.isNew = true;
                _this.setFormValues();
                _this.updateModel(_this.cegForm);
            }
        });
    };
    CEGEditor.prototype.createForm = function () {
        var _this = this;
        this.cegForm = this.formBuilder.group({
            name: ['', forms_1.Validators.required],
            description: ''
        });
        this.cegForm.valueChanges.subscribe(function (formModel) {
            _this.updateModel(formModel);
        });
    };
    CEGEditor.prototype.updateModel = function (formModel) {
        this.model.name = formModel.name;
        this.model.description = formModel.description;
        if (this.isNew && this.model.name) {
            this.model.id = Id_1.Id.fromName(this.model.name);
            this.model.url = Url_1.Url.build([this.container.url, this.model.id]);
        }
    };
    CEGEditor.prototype.setFormValues = function () {
        this.cegForm.setValue({
            name: this.model.name,
            description: this.model.description
        });
    };
    CEGEditor.prototype.save = function () {
        if (this.isNew) {
            this.container['contents'].push(this.model);
        }
        this.dataService.addElement(this.model);
        this.router.navigate(['/requirements', { outlets: { 'main': [this.model.url, 'ceg'] } }]);
        //this.router.navigate([this.model.url, 'ceg'], { relativeTo: this.route });
    };
    CEGEditor.prototype.discard = function () {
        //TODO: Really discard new data and go back. Implement a reset button? Reactive Forms in Angular should help.
        console.log("We do not have reset the values of the model! TODO!");
        this.location.back();
    };
    CEGEditor.prototype.addNode = function (x, y) {
        var node = new CEGNode_1.CEGNode();
        node.name = config_1.Config.CEG_NEW_NAME;
        node.description = config_1.Config.CEG_NEW_DESCRIPTION;
        node.x = x;
        node.y = y;
        this.model['contents'].push(node);
        this.changeDetectorRef.detectChanges();
        this.dataService.addElement(node);
        //this.select(node);
    };
    CEGEditor.prototype.addConnection = function (n1, n2) {
        var connection = new CEGConnection_1.CEGConnection();
        connection.name = "New Connection";
        connection.id = "newconn";
        connection.description = "";
        connection.source = { url: n1.url };
        connection.target = { url: n2.url };
        this.model['contents'].push(connection);
        this.dataService.addElement(connection);
    };
    CEGEditor.prototype.addNodeToSelectedConnectionNodes = function (node) {
        if (this.selectedConnectionNodes.length == 2) {
            this.selectedConnectionNodes = [];
        }
        this.selectedConnectionNodes.push(node);
    };
    CEGEditor.prototype.delete = function (element) {
        if (Type_1.Type.is(element, CEGNode_1.CEGNode)) {
            var connections = this.getConnections(element);
            for (var i = 0; i < connections.length; i++) {
                var connectionIndex = this.model['contents'].indexOf(connections[i]);
                if (connectionIndex >= 0) {
                    this.model['contents'].splice(connectionIndex, 1);
                }
            }
        }
        var nodeIndex = this.model['contents'].indexOf(element);
        if (nodeIndex >= 0) {
            this.model['contents'].splice(nodeIndex, 1);
        }
        this.dataService.removeElement(element);
    };
    CEGEditor.prototype.getConnections = function (node) {
        var connections = [];
        for (var i = 0; i < this.model['contents'].length; i++) {
            var currentElement = this.model['contents'][i];
            if (Type_1.Type.is(currentElement, CEGConnection_1.CEGConnection)) {
                var currentConnection = currentElement;
                if (currentConnection.source.url === node.url || currentConnection.target.url === node.url) {
                    connections.push(currentConnection);
                }
            }
        }
        return connections;
    };
    CEGEditor.prototype.select = function (element) {
        this.selectedNode = element;
        this.router.navigate([{ outlets: { 'ceg-node-details': [element.url, 'ceg-node-details'] } }], { relativeTo: this.route });
        if (this.isActiveConnectionAdder && Type_1.Type.is(element, CEGNode_1.CEGNode)) {
            this.addNodeToSelectedConnectionNodes(element);
        }
        else if (this.isActiveDeleter) {
            this.delete(element);
        }
    };
    CEGEditor.prototype.click = function (evt) {
        if (this.isActiveNodeAdder) {
            this.addNode(evt.offsetX, evt.offsetY);
        }
        else if (this.isActiveConnectionAdder) {
            if (this.selectedConnectionNodes.length == 2) {
                this.addConnection(this.selectedConnectionNodes[0], this.selectedConnectionNodes[1]);
                this.selectedConnectionNodes = [];
            }
        }
    };
    CEGEditor = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'ceg-editor',
            templateUrl: 'ceg-editor.component.html'
        }), 
        __metadata('design:paramtypes', [forms_1.FormBuilder, specmate_data_service_1.SpecmateDataService, router_1.Router, router_1.ActivatedRoute, common_1.Location, core_1.ChangeDetectorRef])
    ], CEGEditor);
    return CEGEditor;
}());
exports.CEGEditor = CEGEditor;
//# sourceMappingURL=ceg-editor.component.js.map