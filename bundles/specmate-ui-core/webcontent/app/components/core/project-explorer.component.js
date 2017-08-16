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
var Url_1 = require("../../util/Url");
var router_1 = require("@angular/router");
var core_1 = require("@angular/core");
var specmate_data_service_1 = require("../../services/specmate-data.service");
var ProjectExplorer = (function () {
    function ProjectExplorer(dataService, router, route) {
        var _this = this;
        this.dataService = dataService;
        this.router = router;
        this.route = route;
        this.baseUrl = '/';
        this.currentElementUrl = '';
        this.router.events.filter(function (event) { return event instanceof router_1.NavigationEnd; }).subscribe(function (event) { return _this.onNavigation(); });
    }
    ProjectExplorer.prototype.onNavigation = function () {
        this.currentElementUrl = Url_1.Url.fromParams(this.route.snapshot.firstChild.params);
    };
    ProjectExplorer.prototype.ngOnInit = function () {
        var _this = this;
        this.dataService.readContents(this.baseUrl).then(function (children) { return _this.rootElements = children; });
    };
    ProjectExplorer = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'project-explorer',
            templateUrl: 'project-explorer.component.html',
            styleUrls: ['project-explorer.component.css']
        }),
        __metadata("design:paramtypes", [specmate_data_service_1.SpecmateDataService, router_1.Router, router_1.ActivatedRoute])
    ], ProjectExplorer);
    return ProjectExplorer;
}());
exports.ProjectExplorer = ProjectExplorer;
//# sourceMappingURL=project-explorer.component.js.map