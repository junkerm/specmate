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
var config_1 = require("../../../../../config/config");
var ProcessConnection_1 = require("../../../../../model/ProcessConnection");
var graphical_connection_base_1 = require("../graphical-connection-base");
var rectangular_line_coords_provider_1 = require("../coordinate-providers/rectangular-line-coords-provider");
var ProcessStep_1 = require("../../../../../model/ProcessStep");
var ProcessDecision_1 = require("../../../../../model/ProcessDecision");
var diamond_line_coords_provider_1 = require("../coordinate-providers/diamond-line-coords-provider");
var Type_1 = require("../../../../../util/Type");
var ProcessStart_1 = require("../../../../../model/ProcessStart");
var ProcessEnd_1 = require("../../../../../model/ProcessEnd");
var circular_line_coords_provider_1 = require("../coordinate-providers/circular-line-coords-provider");
var ProcessGraphicalConnection = (function (_super) {
    __extends(ProcessGraphicalConnection, _super);
    function ProcessGraphicalConnection() {
        var _this = _super !== null && _super.apply(this, arguments) || this;
        _this.nodeType = ProcessConnection_1.ProcessConnection;
        _this.nodeWidth = config_1.Config.CEG_NODE_WIDTH;
        _this.nodeHeight = config_1.Config.CEG_NODE_HEIGHT;
        return _this;
    }
    Object.defineProperty(ProcessGraphicalConnection.prototype, "connection", {
        get: function () {
            return this._connection;
        },
        set: function (connection) {
            this._connection = connection;
            this.setUpLineCoordsProvider();
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessGraphicalConnection.prototype, "element", {
        get: function () {
            return this.connection;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessGraphicalConnection.prototype, "nodes", {
        get: function () {
            return this._nodes;
        },
        set: function (nodes) {
            this._nodes = nodes;
            this.setUpLineCoordsProvider();
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessGraphicalConnection.prototype, "rotateAngle", {
        get: function () {
            if (this.angle <= 90 && this.angle >= -90) {
                return this.angle;
            }
            return this.angle + 180;
        },
        enumerable: true,
        configurable: true
    });
    ProcessGraphicalConnection.prototype.setUpLineCoordsProvider = function () {
        if (this._nodes && this._connection) {
            this.startLineCoordsProvider = this.getLineCoordsProvider(this.sourceNode);
            this.endLineCoordsProvider = this.getLineCoordsProvider(this.targetNode);
        }
    };
    ProcessGraphicalConnection.prototype.getLineCoordsProvider = function (node) {
        if (Type_1.Type.is(node, ProcessDecision_1.ProcessDecision)) {
            return new diamond_line_coords_provider_1.DiamondLineCoordsProvider(this.sourceNode, this.targetNode, config_1.Config.PROCESS_DECISION_NODE_DIM);
        }
        else if (Type_1.Type.is(node, ProcessStep_1.ProcessStep)) {
            return new rectangular_line_coords_provider_1.RectangularLineCoordsProvider(this.sourceNode, this.targetNode, { width: this.nodeWidth, height: this.nodeHeight });
        }
        else if (Type_1.Type.is(node, ProcessStart_1.ProcessStart) || Type_1.Type.is(node, ProcessEnd_1.ProcessEnd)) {
            return new circular_line_coords_provider_1.CircularLineCoordsProvider(this.sourceNode, this.targetNode, config_1.Config.PROCESS_START_END_NODE_RADIUS);
        }
    };
    __decorate([
        core_1.Input(),
        __metadata("design:type", ProcessConnection_1.ProcessConnection),
        __metadata("design:paramtypes", [ProcessConnection_1.ProcessConnection])
    ], ProcessGraphicalConnection.prototype, "connection", null);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Array),
        __metadata("design:paramtypes", [Array])
    ], ProcessGraphicalConnection.prototype, "nodes", null);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Boolean)
    ], ProcessGraphicalConnection.prototype, "selected", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Boolean)
    ], ProcessGraphicalConnection.prototype, "valid", void 0);
    ProcessGraphicalConnection = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: '[process-graphical-connection]',
            templateUrl: 'process-graphical-connection.component.svg',
            styleUrls: ['process-graphical-connection.component.css']
        })
    ], ProcessGraphicalConnection);
    return ProcessGraphicalConnection;
}(graphical_connection_base_1.GraphicalConnectionBase));
exports.ProcessGraphicalConnection = ProcessGraphicalConnection;
//# sourceMappingURL=process-graphical-connection.component.js.map