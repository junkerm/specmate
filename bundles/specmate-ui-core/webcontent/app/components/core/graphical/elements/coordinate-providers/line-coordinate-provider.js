"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var Type_1 = require("../../../../../util/Type");
var CEGNode_1 = require("../../../../../model/CEGNode");
var rectangular_line_coords_provider_1 = require("./rectangular-line-coords-provider");
var config_1 = require("../../../../../config/config");
var ProcessEnd_1 = require("../../../../../model/ProcessEnd");
var ProcessStart_1 = require("../../../../../model/ProcessStart");
var ProcessDecision_1 = require("../../../../../model/ProcessDecision");
var ProcessStep_1 = require("../../../../../model/ProcessStep");
var diamond_line_coords_provider_1 = require("./diamond-line-coords-provider");
var circular_line_coords_provider_1 = require("./circular-line-coords-provider");
var LineCoordinateProvider = (function () {
    function LineCoordinateProvider() {
    }
    LineCoordinateProvider.provide = function (type, source, target) {
        if (Type_1.Type.is(type, CEGNode_1.CEGNode)) {
            return new rectangular_line_coords_provider_1.RectangularLineCoordsProvider(source, target, { width: config_1.Config.CEG_NODE_WIDTH, height: config_1.Config.CEG_NODE_HEIGHT });
        }
        if (Type_1.Type.is(type, ProcessStep_1.ProcessStep)) {
            return new rectangular_line_coords_provider_1.RectangularLineCoordsProvider(source, target, { width: config_1.Config.CEG_NODE_WIDTH, height: config_1.Config.CEG_NODE_HEIGHT });
        }
        if (Type_1.Type.is(type, ProcessDecision_1.ProcessDecision)) {
            return new diamond_line_coords_provider_1.DiamondLineCoordsProvider(source, target, config_1.Config.PROCESS_DECISION_NODE_DIM);
        }
        if (Type_1.Type.is(type, ProcessStart_1.ProcessStart)) {
            return new circular_line_coords_provider_1.CircularLineCoordsProvider(source, target, config_1.Config.PROCESS_START_END_NODE_RADIUS);
        }
        if (Type_1.Type.is(type, ProcessEnd_1.ProcessEnd)) {
            return new circular_line_coords_provider_1.CircularLineCoordsProvider(source, target, config_1.Config.PROCESS_START_END_NODE_RADIUS);
        }
    };
    return LineCoordinateProvider;
}());
exports.LineCoordinateProvider = LineCoordinateProvider;
//# sourceMappingURL=line-coordinate-provider.js.map