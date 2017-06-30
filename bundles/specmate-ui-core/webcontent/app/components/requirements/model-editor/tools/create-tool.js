"use strict";
var Id_1 = require("../../../../util/Id");
var CreateTool = (function () {
    function CreateTool(parent, dataService) {
        this.parent = parent;
        this.dataService = dataService;
        this.selectedElements = [];
    }
    CreateTool.prototype.activate = function () {
        this.done = false;
        this.selectedElements = [];
    };
    CreateTool.prototype.deactivate = function () {
        this.selectedElements = [];
    };
    CreateTool.prototype.getNewId = function (idBase) {
        return this.dataService.readContents(this.parent.url, true).then(function (contents) { return Id_1.Id.generate(contents, idBase); });
    };
    CreateTool.prototype.createAndSelect = function (element) {
        var _this = this;
        return this.dataService.createElement(element, true).then(function () {
            _this.selectedElements = [element];
            _this.done = true;
        });
    };
    return CreateTool;
}());
exports.CreateTool = CreateTool;
//# sourceMappingURL=create-tool.js.map