"use strict";
var Id_1 = require("../../../../util/Id");
var CreateTool = (function () {
    function CreateTool(parent, dataService) {
        this.parent = parent;
        this.dataService = dataService;
        this.selectedElements = [];
    }
    CreateTool.prototype.activate = function () {
        this.selectedElements = [];
    };
    CreateTool.prototype.deactivate = function () {
        this.selectedElements = [];
    };
    CreateTool.prototype.getNewId = function (idBase) {
        return this.dataService.readContents(this.parent.url, true).then(function (contents) { return Id_1.Id.generate(contents, idBase); });
    };
    CreateTool.prototype.createAndSelect = function (element) {
        this.dataService.createElement(element, true);
        this.selectedElements = [element];
    };
    return CreateTool;
}());
exports.CreateTool = CreateTool;
//# sourceMappingURL=create-tool.js.map