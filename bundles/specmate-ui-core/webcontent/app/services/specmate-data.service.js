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
var data_cache_1 = require("./data-cache");
var service_interface_1 = require("./service-interface");
var SpecmateDataService = (function () {
    function SpecmateDataService(http) {
        this.http = http;
        this.cache = new data_cache_1.DataCache();
        this.operations = {};
        this.serviceInterface = new service_interface_1.ServiceInterface(http);
    }
    SpecmateDataService.prototype.commit = function () {
        var _this = this;
        return this.performCommit().then(function () {
            var clean = {};
            for (var url in _this.operations) {
                if (_this.operations[url] !== EOperation.RESOLVED) {
                    clean[url] = _this.operations[url];
                }
            }
            _this.operations = clean;
        });
    };
    SpecmateDataService.prototype.performCommit = function () {
        return Promise.all(this.getAllCommitActions()).then(function () { }, function (message) { return console.log("Promise rejected: " + message); });
    };
    SpecmateDataService.prototype.getAllCommitActions = function () {
        var actions = [];
        for (var url in this.operations) {
            var operation = this.operations[url];
            var element = this.readElementVirtual(url);
            console.log("COMMIT: " + EOperation[operation] + " " + url);
            switch (operation) {
                case EOperation.CREATE:
                    actions.push(this.createElementServer(element));
                    break;
                case EOperation.UPDATE:
                    actions.push(this.updateElementServer(element));
                    break;
                case EOperation.DELETE:
                    actions.push(this.deleteElementServer(url));
                    break;
            }
        }
        return actions;
    };
    SpecmateDataService.prototype.task = function (url, operation) {
        this.operations[url] = operation;
    };
    SpecmateDataService.prototype.resolve = function (url) {
        this.operations[url] = EOperation.RESOLVED;
    };
    SpecmateDataService.prototype.createElement = function (element, virtual) {
        if (virtual) {
            return Promise.resolve(this.createElementVirtual(element));
        }
        this.createElementServer(element);
    };
    SpecmateDataService.prototype.readContents = function (url, virtual) {
        if (virtual) {
            return Promise.resolve(this.readContentsVirtual(url));
        }
        this.readContentsServer(url);
    };
    SpecmateDataService.prototype.readElement = function (url, virtual) {
        if (virtual) {
            return Promise.resolve(this.readElementVirtual(url));
        }
        this.readElementServer(url);
    };
    SpecmateDataService.prototype.updateElement = function (element, virtual) {
        if (virtual) {
            return Promise.resolve(this.updateElementVirtual(element));
        }
        this.updateElementServer(element);
    };
    SpecmateDataService.prototype.deleteElement = function (url, virtual) {
        if (virtual) {
            return Promise.resolve(this.deleteElementVirtual(url));
        }
        this.deleteElementServer(url);
    };
    SpecmateDataService.prototype.createElementVirtual = function (element) {
        this.task(element.url, EOperation.CREATE);
        return this.cache.addElement(element);
    };
    SpecmateDataService.prototype.readContentsVirtual = function (url) {
        return this.cache.readContents(url);
    };
    SpecmateDataService.prototype.readElementVirtual = function (url, forceServer) {
        return this.cache.readElement(url);
    };
    SpecmateDataService.prototype.updateElementVirtual = function (element) {
        this.task(element.url, EOperation.UPDATE);
        this.operations[element.url] = EOperation.UPDATE;
        return this.cache.addElement(element);
    };
    SpecmateDataService.prototype.deleteElementVirtual = function (url) {
        this.task(url, EOperation.DELETE);
        return this.cache.deleteElement(url);
    };
    SpecmateDataService.prototype.createElementServer = function (element) {
        var _this = this;
        return this.serviceInterface.createElement(element).then(function () {
            _this.cache.addElement(element);
            _this.resolve(element.url);
        });
    };
    SpecmateDataService.prototype.readContentsServer = function (url) {
        var _this = this;
        if (!this.cache.isCachedContents(url)) {
            return this.serviceInterface.readContents(url).then(function (contents) {
                contents.forEach(function (element) { return _this.cache.addElement(element); });
                return _this.cache.readContents(url);
            });
        }
        return Promise.resolve(this.cache.readContents(url));
    };
    SpecmateDataService.prototype.readElementServer = function (url) {
        var _this = this;
        if (!this.cache.isCachedContents(url)) {
            return this.serviceInterface.readElement(url).then(function (element) {
                _this.cache.addElement(element);
                return _this.cache.readElement(url);
            });
        }
        return Promise.resolve(this.cache.readElement(url));
    };
    SpecmateDataService.prototype.updateElementServer = function (element) {
        var _this = this;
        return this.serviceInterface.updateElement(element).then(function () {
            _this.cache.addElement(element);
            _this.resolve(element.url);
        });
    };
    SpecmateDataService.prototype.deleteElementServer = function (url) {
        var _this = this;
        return this.serviceInterface.deleteElement(url).then(function () {
            _this.cache.deleteElement(url);
            _this.resolve(url);
        });
    };
    SpecmateDataService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [http_1.Http])
    ], SpecmateDataService);
    return SpecmateDataService;
}());
exports.SpecmateDataService = SpecmateDataService;
(function (EOperation) {
    EOperation[EOperation["CREATE"] = 0] = "CREATE";
    EOperation[EOperation["DELETE"] = 1] = "DELETE";
    EOperation[EOperation["UPDATE"] = 2] = "UPDATE";
    EOperation[EOperation["RESOLVED"] = 3] = "RESOLVED";
})(exports.EOperation || (exports.EOperation = {}));
var EOperation = exports.EOperation;
//# sourceMappingURL=specmate-data.service.js.map