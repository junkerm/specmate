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
var graphical_element_base_1 = require("./graphical-element-base");
var core_1 = require("@angular/core");
var line_coordinate_provider_1 = require("./coordinate-providers/line-coordinate-provider");
var GraphicalConnectionBase = (function (_super) {
    __extends(GraphicalConnectionBase, _super);
    function GraphicalConnectionBase() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    Object.defineProperty(GraphicalConnectionBase.prototype, "connection", {
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
    Object.defineProperty(GraphicalConnectionBase.prototype, "element", {
        get: function () {
            return this.connection;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "nodes", {
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
    Object.defineProperty(GraphicalConnectionBase.prototype, "sourceNode", {
        get: function () {
            return this.getNode(this.connection.source);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(GraphicalConnectionBase.prototype, "targetNode", {
        get: function () {
            return this.getNode(this.connection.target);
        },
        enumerable: true,
        configurable: true
    });
    GraphicalConnectionBase.prototype.getNode = function (proxy) {
        if (!proxy) {
            throw new Error('Tried to get element for undefined proxy!');
        }
        return this.nodes.filter(function (containedNode) { return containedNode.url === proxy.url; })[0];
    };
    GraphicalConnectionBase.prototype.setUpLineCoordsProvider = function () {
        if (this._nodes && this._connection && !this.startLineCoordsProvider && !this.endLineCoordsProvider) {
            this.startLineCoordsProvider = line_coordinate_provider_1.LineCoordinateProvider.provide(this.sourceNode, this.sourceNode, this.targetNode);
            this.endLineCoordsProvider = line_coordinate_provider_1.LineCoordinateProvider.provide(this.targetNode, this.sourceNode, this.targetNode);
        }
    };
    __decorate([
        core_1.Input(),
        __metadata("design:type", Object),
        __metadata("design:paramtypes", [Object])
    ], GraphicalConnectionBase.prototype, "connection", null);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Array),
        __metadata("design:paramtypes", [Array])
    ], GraphicalConnectionBase.prototype, "nodes", null);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Boolean)
    ], GraphicalConnectionBase.prototype, "selected", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Boolean)
    ], GraphicalConnectionBase.prototype, "valid", void 0);
    return GraphicalConnectionBase;
}(graphical_element_base_1.GraphicalElementBase));
exports.GraphicalConnectionBase = GraphicalConnectionBase;
//# sourceMappingURL=graphical-connection-base.js.map