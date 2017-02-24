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
var core_1 = require('@angular/core');
var http_1 = require('@angular/http');
require('rxjs/add/operator/toPromise');
var CONTENTS = {
    '/': {
        name: 'Root',
        url: '/'
    },
    '/folder1': {
        name: 'Folder 1',
        url: '/folder1'
    },
    '/folder2': {
        name: 'Folder 2',
        url: '/folder2'
    },
    '/folder1/object1': {
        name: 'Object 1',
        url: '/folder1/object1'
    },
    '/folder2/object2': {
        name: 'Object 2',
        url: '/folder2/object2'
    }
};
var CHILDREN = {
    '/': [CONTENTS['/folder1'], CONTENTS['/folder2']],
    '/folder1': [CONTENTS['/folder1/object1']],
    '/folder2': [CONTENTS['/folder2/object2']]
};
var SpecmateDataService = (function () {
    function SpecmateDataService(http) {
        this.http = http;
        // localhost:8080/services/rest/:url/list|details|delete
        this.baseUrl = 'services/rest/';
        this.detailsCache = [];
    }
    SpecmateDataService.prototype.cleanUrl = function (url) {
        while (url.indexOf("//") >= 0) {
            url = url.replace("//", "/");
        }
        return url;
    };
    SpecmateDataService.prototype.getList = function (url) {
        var fullUrl = this.cleanUrl(this.baseUrl + url + '/list');
        return this.http.get(fullUrl).toPromise().then(function (response) {
            return response.json();
        });
    };
    SpecmateDataService.prototype.getDetails = function (url) {
        var _this = this;
        if (this.detailsCache[url]) {
            return Promise.resolve(this.detailsCache[url]);
        }
        var fullUrl = this.cleanUrl(this.baseUrl + url + '/details');
        return this.http.get(fullUrl).toPromise().then(function (response) {
            var details = response.json();
            _this.detailsCache[url] = details;
            return details;
        });
    };
    SpecmateDataService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [http_1.Http])
    ], SpecmateDataService);
    return SpecmateDataService;
}());
exports.SpecmateDataService = SpecmateDataService;
//# sourceMappingURL=specmate-data.service.js.map