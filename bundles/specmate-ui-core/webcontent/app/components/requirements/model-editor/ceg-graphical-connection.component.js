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
var router_1 = require('@angular/router');
var config_1 = require('../../../config/config');
var CEGConnection_1 = require('../../../model/CEGConnection');
var specmate_data_service_1 = require('../../../services/specmate-data.service');
var CEGGraphicalConnection = (function () {
    function CEGGraphicalConnection(dataService, router, route) {
        this.dataService = dataService;
        this.router = router;
        this.route = route;
    }
    Object.defineProperty(CEGGraphicalConnection.prototype, "x1", {
        get: function () {
            return this.sourceNode ? this.sourceNode.x + (config_1.Config.CEG_NODE_WIDTH / 2) : 0;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalConnection.prototype, "y1", {
        get: function () {
            return this.sourceNode ? this.sourceNode.y + (config_1.Config.CEG_NODE_HEIGHT / 2) : 0;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalConnection.prototype, "x2", {
        get: function () {
            return this.targetNode ? this.targetNode.x + (config_1.Config.CEG_NODE_WIDTH / 2) : 0;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalConnection.prototype, "y2", {
        get: function () {
            return this.targetNode ? this.targetNode.y + (config_1.Config.CEG_NODE_HEIGHT / 2) : 0;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalConnection.prototype, "sourceNode", {
        get: function () {
            return this.getNode(this.connection.source);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalConnection.prototype, "targetNode", {
        get: function () {
            return this.getNode(this.connection.target);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalConnection.prototype, "isNegated", {
        get: function () {
            console.log('isNegated: ' + this.connection.negate);
            return this.connection.negate;
        },
        enumerable: true,
        configurable: true
    });
    CEGGraphicalConnection.prototype.getNode = function (proxy) {
        if (!proxy) {
            throw new Error('Tried to get element for undefined proxy!');
        }
        var node = this.effectNodes.filter(function (containedNode) { return containedNode.url === proxy.url; })[0];
        if (node) {
            return node;
        }
        node = this.causeNodes.filter(function (containedNode) { return containedNode.url === proxy.url; })[0];
        if (node) {
            return node;
        }
        return this.nodes.filter(function (containedNode) { return containedNode.url === proxy.url; })[0];
    };
    __decorate([
        core_1.Input(), 
        __metadata('design:type', CEGConnection_1.CEGConnection)
    ], CEGGraphicalConnection.prototype, "connection", void 0);
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Array)
    ], CEGGraphicalConnection.prototype, "causeNodes", void 0);
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Array)
    ], CEGGraphicalConnection.prototype, "effectNodes", void 0);
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Array)
    ], CEGGraphicalConnection.prototype, "nodes", void 0);
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Boolean)
    ], CEGGraphicalConnection.prototype, "selected", void 0);
    CEGGraphicalConnection = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: '[ceg-graphical-connection]',
            templateUrl: 'ceg-graphical-connection.component.svg',
            styleUrls: ['ceg-graphical-connection.component.css']
        }), 
        __metadata('design:paramtypes', [specmate_data_service_1.SpecmateDataService, router_1.Router, router_1.ActivatedRoute])
    ], CEGGraphicalConnection);
    return CEGGraphicalConnection;
}());
exports.CEGGraphicalConnection = CEGGraphicalConnection;
//# sourceMappingURL=ceg-graphical-connection.component.js.map