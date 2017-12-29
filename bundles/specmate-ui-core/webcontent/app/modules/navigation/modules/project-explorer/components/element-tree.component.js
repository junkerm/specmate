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
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var specmate_data_service_1 = require("../../../../data/modules/data-service/services/specmate-data.service");
var navigator_service_1 = require("../../navigator/services/navigator.service");
var logging_service_1 = require("../../../../views/side/modules/log-list/services/logging.service");
var url_1 = require("../../../../../util/url");
var type_1 = require("../../../../../util/type");
var Requirement_1 = require("../../../../../model/Requirement");
var CEGModel_1 = require("../../../../../model/CEGModel");
var Folder_1 = require("../../../../../model/Folder");
var TestSpecification_1 = require("../../../../../model/TestSpecification");
var Process_1 = require("../../../../../model/Process");
var ElementTree = /** @class */ (function () {
    function ElementTree(dataService, navigator, logger) {
        this.dataService = dataService;
        this.navigator = navigator;
        this.logger = logger;
        this._expanded = false;
    }
    Object.defineProperty(ElementTree.prototype, "currentElement", {
        get: function () {
            return this._currentElement;
        },
        set: function (currentElement) {
            this._currentElement = currentElement;
            if (this.isMustOpen) {
                this.initContents();
            }
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ElementTree.prototype, "element", {
        get: function () {
            return this._element;
        },
        set: function (element) {
            this._element = element;
            if (this.isMustOpen) {
                this.initContents();
            }
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ElementTree.prototype, "expanded", {
        get: function () {
            if (!this._expanded && this.isMustOpen) {
                this._expanded = true;
            }
            return this._expanded;
        },
        set: function (expanded) {
            this._expanded = expanded;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ElementTree.prototype, "canExpand", {
        get: function () {
            return (this.isCEGModelNode || this.isProcessNode || this.isRequirementNode || this.isFolderNode)
                && this.withExpand;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ElementTree.prototype, "isMustOpen", {
        get: function () {
            if (this._currentElement && this.element) {
                return url_1.Url.isParent(this.element.url, this._currentElement.url);
            }
            return false;
        },
        enumerable: true,
        configurable: true
    });
    ElementTree.prototype.ngOnInit = function () {
        var _this = this;
        this.dataService.readElement(this.baseUrl).then(function (element) {
            _this.element = element;
        });
        if (this.expanded || this.isMustOpen) {
            this.initContents();
        }
    };
    ElementTree.prototype.toggle = function () {
        this.expanded = !this._expanded;
        if (this.expanded && !this.contents) {
            this.initContents();
        }
    };
    ElementTree.prototype.initContents = function () {
        var _this = this;
        this.dataService.readContents(this.baseUrl).then(function (contents) {
            _this.contents = contents;
        });
    };
    Object.defineProperty(ElementTree.prototype, "isRequirementNode", {
        get: function () {
            return type_1.Type.is(this.element, Requirement_1.Requirement);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ElementTree.prototype, "isCEGModelNode", {
        get: function () {
            return type_1.Type.is(this.element, CEGModel_1.CEGModel);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ElementTree.prototype, "isFolderNode", {
        get: function () {
            return type_1.Type.is(this.element, Folder_1.Folder);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ElementTree.prototype, "isTestSpecificationNode", {
        get: function () {
            return type_1.Type.is(this.element, TestSpecification_1.TestSpecification);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ElementTree.prototype, "isGeneratedTestSpecificationNode", {
        get: function () {
            return this.isTestSpecificationNode && this.parent && (type_1.Type.is(this.parent, CEGModel_1.CEGModel) || type_1.Type.is(this.parent, Process_1.Process));
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ElementTree.prototype, "isProcessNode", {
        get: function () {
            return type_1.Type.is(this.element, Process_1.Process);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ElementTree.prototype, "isActive", {
        get: function () {
            if (!this.element || !this.navigator.currentElement) {
                return false;
            }
            return this.element.url === this.navigator.currentElement.url;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ElementTree.prototype, "showElement", {
        get: function () {
            return this.isCEGModelNode || this.isProcessNode || this.isRequirementNode
                || this.isTestSpecificationNode || this.isFolderNode;
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        core_1.Input(),
        __metadata("design:type", String)
    ], ElementTree.prototype, "baseUrl", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Object)
    ], ElementTree.prototype, "parent", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Object),
        __metadata("design:paramtypes", [Object])
    ], ElementTree.prototype, "currentElement", null);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Boolean)
    ], ElementTree.prototype, "withExpand", void 0);
    __decorate([
        core_1.Input(),
        __metadata("design:type", Object),
        __metadata("design:paramtypes", [Object])
    ], ElementTree.prototype, "element", null);
    ElementTree = __decorate([
        core_1.Component({
            moduleId: module.id.toString(),
            selector: 'element-tree',
            templateUrl: 'element-tree.component.html',
            styleUrls: ['element-tree.component.css']
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService, navigator_service_1.NavigatorService, logging_service_1.LoggingService])
    ], ElementTree);
    return ElementTree;
}());
exports.ElementTree = ElementTree;
//# sourceMappingURL=element-tree.component.js.map