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
var Subject_1 = require("rxjs/Subject");
require("rxjs/add/operator/catch");
require("rxjs/add/operator/debounceTime");
require("rxjs/add/operator/distinctUntilChanged");
var specmate_data_service_1 = require("../../../../data/modules/data-service/services/specmate-data.service");
var navigator_service_1 = require("../../navigator/services/navigator.service");
var ProjectExplorer = /** @class */ (function () {
    function ProjectExplorer(dataService, navigator) {
        this.dataService = dataService;
        this.navigator = navigator;
        this.baseUrl = '/';
        this.searchQueries = new Subject_1.Subject();
    }
    Object.defineProperty(ProjectExplorer.prototype, "currentElement", {
        get: function () {
            return this.navigator.currentElement;
        },
        enumerable: true,
        configurable: true
    });
    ProjectExplorer.prototype.ngOnInit = function () {
        var _this = this;
        this.dataService.readContents(this.baseUrl).then(function (children) { return _this.rootElements = children; });
        this.searchQueries
            .debounceTime(300)
            .distinctUntilChanged()
            .subscribe(function (query) {
            _this.dataService.search(query).then(function (results) { return _this.searchResults = results; });
        });
    };
    ProjectExplorer.prototype.search = function (query) {
        this.searchQueries.next(query);
    };
    ProjectExplorer = __decorate([
        core_1.Component({
            moduleId: module.id.toString(),
            selector: 'project-explorer',
            templateUrl: 'project-explorer.component.html',
            styleUrls: ['project-explorer.component.css']
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService, navigator_service_1.NavigatorService])
    ], ProjectExplorer);
    return ProjectExplorer;
}());
exports.ProjectExplorer = ProjectExplorer;
//# sourceMappingURL=project-explorer.component.js.map