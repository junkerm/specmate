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
var Requirement_1 = require("../../model/Requirement");
var TestSpecification_1 = require("../../model/TestSpecification");
var specmate_data_service_1 = require("../data/specmate-data.service");
var navigator_service_1 = require("../navigation/navigator.service");
var Sort_1 = require("../../util/Sort");
var Url_1 = require("../../util/Url");
var Type_1 = require("../../util/Type");
var CEGModel_1 = require("../../model/CEGModel");
var Process_1 = require("../../model/Process");
var TestProcedure_1 = require("../../model/TestProcedure");
var AdditionalInformationService = (function () {
    function AdditionalInformationService(dataService, navigator) {
        var _this = this;
        this.dataService = dataService;
        this.navigator = navigator;
        this.informationLoaded = new core_1.EventEmitter();
        navigator.hasNavigated.subscribe(function (element) {
            _this.clear();
            _this.element = element;
            _this.loadParents()
                .then(function () { return _this.loadTestSpecifications(); })
                .then(function () { return _this.informationLoaded.emit(); });
        });
    }
    AdditionalInformationService.prototype.loadTestSpecifications = function () {
        var _this = this;
        if (!this.canHaveTestSpecifications || !this.requirement) {
            return Promise.resolve();
        }
        return this.dataService.performQuery(this.requirement.url, 'listRecursive', { class: TestSpecification_1.TestSpecification.className })
            .then(function (testSpecifications) { return _this._testSpecifications = Sort_1.Sort.sortArray(testSpecifications); })
            .then(function () { return Promise.resolve(); });
    };
    AdditionalInformationService.prototype.loadParents = function () {
        var _this = this;
        var parentUrls = [];
        var url = this.element.url;
        while (!Url_1.Url.isRoot(url)) {
            url = Url_1.Url.parent(url);
            parentUrls.push(url);
        }
        var readParentsTask = Promise.resolve(0);
        this.parents = [];
        var _loop_1 = function (i) {
            var currentUrl = parentUrls[i];
            readParentsTask = readParentsTask.then(function () {
                return _this.dataService.readElement(currentUrl)
                    .then(function (element) { return _this.parents.push(element); });
            });
        };
        for (var i = 0; i < parentUrls.length; i++) {
            _loop_1(i);
        }
        return readParentsTask.then(function () { return Promise.resolve(); });
    };
    AdditionalInformationService.prototype.clear = function () {
        this._model = undefined;
        this._requirement = undefined;
        this._contents = undefined;
    };
    Object.defineProperty(AdditionalInformationService.prototype, "hasAdditionalInformation", {
        get: function () {
            return this.requirement !== undefined || this.model !== undefined || this.testSpecification !== undefined;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(AdditionalInformationService.prototype, "model", {
        get: function () {
            if (!this.parents) {
                return undefined;
            }
            return this.parents.find(function (element) { return Type_1.Type.is(element, CEGModel_1.CEGModel) || Type_1.Type.is(element, Process_1.Process); });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(AdditionalInformationService.prototype, "requirement", {
        get: function () {
            if (!this.parents) {
                return undefined;
            }
            return this.parents.find(function (element) { return Type_1.Type.is(element, Requirement_1.Requirement); });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(AdditionalInformationService.prototype, "testSpecification", {
        get: function () {
            if (!this.parents) {
                return undefined;
            }
            return this.parents.find(function (element) { return Type_1.Type.is(element, TestSpecification_1.TestSpecification); });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(AdditionalInformationService.prototype, "testSpecifications", {
        get: function () {
            return this._testSpecifications;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(AdditionalInformationService.prototype, "canHaveTestSpecifications", {
        get: function () {
            return Type_1.Type.is(this.element, Requirement_1.Requirement) || Type_1.Type.is(this.element, CEGModel_1.CEGModel) || Type_1.Type.is(this.element, Process_1.Process);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(AdditionalInformationService.prototype, "canGenerateTestSpecifications", {
        get: function () {
            return this.element && (Type_1.Type.is(this.element, CEGModel_1.CEGModel) || Type_1.Type.is(this.element, Process_1.Process));
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(AdditionalInformationService.prototype, "canAddTestSpecifications", {
        get: function () {
            return Type_1.Type.is(this.element, Requirement_1.Requirement);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(AdditionalInformationService.prototype, "canExportToALM", {
        get: function () {
            return Type_1.Type.is(this.element, TestProcedure_1.TestProcedure);
        },
        enumerable: true,
        configurable: true
    });
    AdditionalInformationService = __decorate([
        core_1.Injectable(),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService, navigator_service_1.NavigatorService])
    ], AdditionalInformationService);
    return AdditionalInformationService;
}());
exports.AdditionalInformationService = AdditionalInformationService;
//# sourceMappingURL=additional-information.service.js.map