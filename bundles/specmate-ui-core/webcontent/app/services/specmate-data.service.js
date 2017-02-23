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
    function SpecmateDataService() {
    }
    SpecmateDataService.prototype.getContent = function (url) {
        return Promise.resolve(CONTENTS[url]);
    };
    SpecmateDataService.prototype.getChildren = function (url) {
        return Promise.resolve(CHILDREN[url]);
    };
    SpecmateDataService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [])
    ], SpecmateDataService);
    return SpecmateDataService;
}());
exports.SpecmateDataService = SpecmateDataService;
//# sourceMappingURL=specmate-data.service.js.map