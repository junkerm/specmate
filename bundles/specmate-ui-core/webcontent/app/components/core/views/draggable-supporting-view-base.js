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
Object.defineProperty(exports, "__esModule", { value: true });
var specmate_view_base_1 = require("./specmate-view-base");
var DraggableSupportingViewBase = (function (_super) {
    __extends(DraggableSupportingViewBase, _super);
    function DraggableSupportingViewBase(dataService, navigator, route, modal, editorCommonControlService) {
        var _this = _super.call(this, dataService, navigator, route, modal, editorCommonControlService) || this;
        _this.isDragging = false;
        return _this;
    }
    DraggableSupportingViewBase.prototype.onDragStart = function (e) {
        this.isDragging = true;
    };
    DraggableSupportingViewBase.prototype.onDropSuccess = function (e) {
        this.sanitizeContentPositions(true);
        this.isDragging = false;
    };
    DraggableSupportingViewBase.prototype.sanitizeContentPositions = function (update) {
        this.dataService.sanitizeContentPositions(this.relevantElements, update);
    };
    DraggableSupportingViewBase.prototype.onElementResolved = function (element) {
        this.element = element;
        this.readContents();
    };
    /** Reads to the contents of the test specification  */
    DraggableSupportingViewBase.prototype.readContents = function () {
        var _this = this;
        if (this.element) {
            this.dataService.readContents(this.element.url).then(function (contents) {
                _this.contents = contents;
                _this.sanitizeContentPositions(true);
                _this.dataService.commit('Save (Sanitized positions)');
            });
        }
    };
    return DraggableSupportingViewBase;
}(specmate_view_base_1.SpecmateViewBase));
exports.DraggableSupportingViewBase = DraggableSupportingViewBase;
//# sourceMappingURL=draggable-supporting-view-base.js.map