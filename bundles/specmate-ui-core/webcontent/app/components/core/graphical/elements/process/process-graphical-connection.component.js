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
var specmate_data_service_1 = require("../../../../../services/data/specmate-data.service");
var config_1 = require("../../../../../config/config");
var angles_1 = require("../../util/angles");
var coords_1 = require("../../util/coords");
var graphical_element_base_1 = require("../graphical-element-base");
var ProcessConnection_1 = require("../../../../../model/ProcessConnection");
var ProcessGraphicalConnection = (function (_super) {
    __extends(ProcessGraphicalConnection, _super);
    function ProcessGraphicalConnection(dataService) {
        var _this = _super.call(this) || this;
        _this.dataService = dataService;
        _this.nodeType = ProcessConnection_1.ProcessConnection;
        _this.nodeWidth = config_1.Config.CEG_NODE_WIDTH;
        _this.nodeHeight = config_1.Config.CEG_NODE_HEIGHT;
        return _this;
    }
    Object.defineProperty(ProcessGraphicalConnection.prototype, "element", {
        get: function () {
            return this.connection;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessGraphicalConnection.prototype, "x1", {
        get: function () {
            return this.c1.x;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessGraphicalConnection.prototype, "y1", {
        get: function () {
            return this.c1.y;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessGraphicalConnection.prototype, "x2", {
        get: function () {
            return this.c2.x;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessGraphicalConnection.prototype, "y2", {
        get: function () {
            return this.c2.y;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessGraphicalConnection.prototype, "c1", {
        get: function () {
            return this.getC(this.sourceNode);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessGraphicalConnection.prototype, "c2", {
        get: function () {
            return this.getC(this.targetNode);
        },
        enumerable: true,
        configurable: true
    });
    ProcessGraphicalConnection.prototype.getC = function (node) {
        return coords_1.Coords.getCenter(node.x, node.y, this.nodeWidth, this.nodeHeight);
    };
    Object.defineProperty(ProcessGraphicalConnection.prototype, "centerX", {
        get: function () {
            return (this.x1 + this.x2) / 2.0;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessGraphicalConnection.prototype, "centerY", {
        get: function () {
            return (this.y1 + this.y2) / 2.0;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessGraphicalConnection.prototype, "alpha1", {
        get: function () {
            return angles_1.Angles.calcAngle(-this.nodeWidth, -this.nodeHeight);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessGraphicalConnection.prototype, "isLeft", {
        get: function () {
            return this.angle >= -(180 + this.alpha1) && this.angle <= (180 + this.alpha1);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessGraphicalConnection.prototype, "isRight", {
        get: function () {
            return (this.angle >= -this.alpha1 && this.angle <= 180) || (this.angle >= -180 && this.angle <= this.alpha1);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessGraphicalConnection.prototype, "isTop", {
        get: function () {
            return this.angle >= 180 + this.alpha1 && this.angle <= -this.alpha1;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessGraphicalConnection.prototype, "isBelow", {
        get: function () {
            return this.angle >= this.alpha1 && this.angle <= -(180 + this.alpha1);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessGraphicalConnection.prototype, "arrowX", {
        get: function () {
            if (this.isLeft) {
                return this.x2 - this.nodeWidth / 2;
            }
            else if (this.isRight) {
                return this.x2 + this.nodeWidth / 2;
            }
            else if (this.isTop) {
                return this.x2 - ((this.nodeHeight / 2) / Math.tan(this.angle / 180 * Math.PI));
            }
            else if (this.isBelow) {
                return this.x2 + ((this.nodeHeight / 2) / Math.tan(this.angle / 180 * Math.PI));
            }
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessGraphicalConnection.prototype, "arrowY", {
        get: function () {
            if (this.isLeft) {
                return this.y2 - ((this.nodeWidth / 2) * Math.tan(this.angle / 180 * Math.PI));
            }
            else if (this.isRight) {
                return this.y2 + ((this.nodeWidth / 2) * Math.tan(this.angle / 180 * Math.PI));
            }
            else if (this.isTop) {
                return this.y2 - this.nodeHeight / 2;
            }
            else if (this.isBelow) {
                return this.y2 + this.nodeHeight / 2;
            }
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessGraphicalConnection.prototype, "angle", {
        get: function () {
            return angles_1.Angles.angle(this);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessGraphicalConnection.prototype, "sourceNode", {
        get: function () {
            return this.getNode(this.connection.source);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessGraphicalConnection.prototype, "targetNode", {
        get: function () {
            return this.getNode(this.connection.target);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ProcessGraphicalConnection.prototype, "isNegated", {
        get: function () {
            // Recently, the negate property is sometimes sent as string from the server. We workaround this easily here.
            return (this.connection.negate + '').toLowerCase() === 'true';
        },
        enumerable: true,
        configurable: true
    });
    ProcessGraphicalConnection.prototype.getNode = function (proxy) {
        if (!proxy) {
            throw new Error('Tried to get element for undefined proxy!');
        }
        return this.nodes.filter(function (containedNode) { return containedNode.url === proxy.url; })[0];
    };
    __decorate([
        core_1.Input(),
        __metadata("design:type", CEGConnection_1.CEGConnection)
    ], ProcessGraphicalConnection.prototype, "connection", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Array)
    ], ProcessGraphicalConnection.prototype, "nodes", void 0);
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
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService])
    ], ProcessGraphicalConnection);
    return ProcessGraphicalConnection;
}(graphical_element_base_1.GraphicalElementBase));
exports.ProcessGraphicalConnection = ProcessGraphicalConnection;
//# sourceMappingURL=process-graphical-connection.component.js.map