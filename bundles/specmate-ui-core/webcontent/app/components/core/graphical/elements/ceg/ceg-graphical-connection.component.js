"use strict";
var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
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
var CEGConnection_1 = require("../../../../../model/CEGConnection");
var config_1 = require("../../../../../config/config");
var graphical_connection_base_1 = require("../graphical-connection-base");
var rectangular_line_coords_provider_1 = require("../coordinate-providers/rectangular-line-coords-provider");
var CEGGraphicalConnection = (function (_super) {
    __extends(CEGGraphicalConnection, _super);
    function CEGGraphicalConnection() {
        var _this = _super !== null && _super.apply(this, arguments) || this;
        _this.nodeType = CEGConnection_1.CEGConnection;
        _this.nodeWidth = config_1.Config.CEG_NODE_WIDTH;
        _this.nodeHeight = config_1.Config.CEG_NODE_HEIGHT;
        return _this;
    }
    Object.defineProperty(CEGGraphicalConnection.prototype, "connection", {
        get: function () {
            return this._connection;
        },
        set: function (connection) {
            this._connection = connection;
            this.setupLineProvider();
        },
        enumerable: true,
        configurable: true
    });
    ;
    ;
    Object.defineProperty(CEGGraphicalConnection.prototype, "element", {
        get: function () {
            return this.connection;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalConnection.prototype, "nodes", {
        get: function () {
            return this._nodes;
        },
        set: function (nodes) {
            this._nodes = nodes;
            this.setupLineProvider();
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(CEGGraphicalConnection.prototype, "isNegated", {
        get: function () {
            return (this.connection.negate + '').toLowerCase() === 'true';
        },
        enumerable: true,
        configurable: true
    });
    CEGGraphicalConnection.prototype.setupLineProvider = function () {
        if (this.connection && this.nodes) {
            this.startLineCoordsProvider = new rectangular_line_coords_provider_1.RectangularLineCoordsProvider(this.sourceNode, this.targetNode, { width: this.nodeWidth, height: this.nodeHeight });
            this.endLineCoordsProvider = new rectangular_line_coords_provider_1.RectangularLineCoordsProvider(this.sourceNode, this.targetNode, { width: this.nodeWidth, height: this.nodeHeight });
        }
    };
    __decorate([
        core_1.Input(),
        __metadata("design:type", CEGConnection_1.CEGConnection),
        __metadata("design:paramtypes", [CEGConnection_1.CEGConnection])
    ], CEGGraphicalConnection.prototype, "connection", null);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Array),
        __metadata("design:paramtypes", [Array])
    ], CEGGraphicalConnection.prototype, "nodes", null);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Boolean)
    ], CEGGraphicalConnection.prototype, "selected", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Boolean)
    ], CEGGraphicalConnection.prototype, "valid", void 0);
    CEGGraphicalConnection = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: '[ceg-graphical-connection]',
            templateUrl: 'ceg-graphical-connection.component.svg',
            styleUrls: ['ceg-graphical-connection.component.css']
        })
    ], CEGGraphicalConnection);
    return CEGGraphicalConnection;
}(graphical_connection_base_1.GraphicalConnectionBase));
exports.CEGGraphicalConnection = CEGGraphicalConnection;
//# sourceMappingURL=ceg-graphical-connection.component.js.map