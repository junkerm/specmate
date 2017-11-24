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
var Sort_1 = require("../../../util/Sort");
var DraggableSupportingViewBase = (function (_super) {
    __extends(DraggableSupportingViewBase, _super);
    function DraggableSupportingViewBase(dataService, navigator, route, modal, editorCommonControlService, dragulaService) {
        var _this = _super.call(this, dataService, navigator, route, modal, editorCommonControlService) || this;
        _this.dragulaService = dragulaService;
        _this.isDragging = false;
        _this.dragulaService.dropModel.subscribe(function (value) { return _this.onDropModel(value.slice(1)); });
        return _this;
    }
    Object.defineProperty(DraggableSupportingViewBase.prototype, "contents", {
        get: function () {
            //Sort.sortArrayInPlace(this._contents);
            return this._contents;
        },
        set: function (contents) {
            this._contents = contents;
        },
        enumerable: true,
        configurable: true
    });
    DraggableSupportingViewBase.prototype.onDropModel = function (value) {
        var el = value[0], target = value[1], source = value[2];
        this.sanitizeContentPositions(true);
    };
    DraggableSupportingViewBase.prototype.sanitizeContentPositions = function (update) {
        this.dataService.sanitizeContentPositions(this.relevantElements, update);
        Sort_1.Sort.sortArrayInPlace(this.contents);
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