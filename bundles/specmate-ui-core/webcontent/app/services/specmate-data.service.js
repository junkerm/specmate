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
/**
 * The interface to all data handling things.
 * It handles the cache and the service interface.
 *
 * In commands executed by the user via the gui, always set the virtual argument to true, and use the commit-method in a save button.
 * This makes changes being done only in the cache, not on the server. In rare cases, e.g., creating a new model, the virtual flag can be omitted, since we want to store this directly on the server.
 *
 * Whenever the user discards local changes, clearCommits() needs to be called to prevent commits from other views are done.
 */
var SpecmateDataService = (function () {
    function SpecmateDataService(http) {
        this.http = http;
        this.cache = new data_cache_1.DataCache();
        this.operations = {};
        this.serviceInterface = new service_interface_1.ServiceInterface(http);
    }
    SpecmateDataService.prototype.commit = function () {
        var _this = this;
        return this.chainCommits().then(function () {
            _this.cleanOperations();
        });
    };
    SpecmateDataService.prototype.clearCommits = function () {
        this.operations = {};
    };
    SpecmateDataService.prototype.cleanOperations = function () {
        var clean = {};
        for (var url in this.operations) {
            if (this.operations[url] && this.operations[url] !== EOperation.RESOLVED) {
                clean[url] = this.operations[url];
            }
        }
        this.operations = clean;
    };
    SpecmateDataService.prototype.chainCommits = function () {
        var _this = this;
        var chain = Promise.resolve();
        var _loop_1 = function(url) {
            chain = chain.then(function () {
                return _this.determineAction(url);
            });
        };
        for (var url in this.operations) {
            _loop_1(url);
        }
        return chain;
    };
    SpecmateDataService.prototype.determineAction = function (url) {
        var element = this.readElementVirtual(url);
        var operation = this.operations[url];
        switch (operation) {
            case EOperation.CREATE:
                return this.createElementServer(element);
            case EOperation.UPDATE:
                return this.updateElementServer(element);
            case EOperation.DELETE:
                return this.deleteElementServer(url);
        }
    };
    SpecmateDataService.prototype.schedule = function (url, operation) {
        var previousOperation = this.operations[url];
        if (previousOperation === EOperation.CREATE) {
            if (operation === EOperation.CREATE) {
                console.error('Trying to overwrite element ' + url);
            }
            else if (operation === EOperation.UPDATE) {
                operation = EOperation.CREATE;
            }
            else if (operation === EOperation.DELETE) {
                operation = EOperation.RESOLVED;
            }
        }
        else if (previousOperation === EOperation.UPDATE) {
            if (operation === EOperation.CREATE) {
                console.error('Trying to overwrite element ' + url);
            }
        }
        else if (previousOperation === EOperation.DELETE) {
            if (operation === EOperation.CREATE) {
                operation = EOperation.UPDATE;
            }
            else if (operation === EOperation.UPDATE) {
                console.error('Trying to update deleted element ' + url);
            }
            else if (operation === EOperation.DELETE) {
                console.error('Trying to delete deleted element ' + url);
            }
        }
        console.log("SCHEDULING " + EOperation[operation] + " FOR " + url);
        this.operations[url] = operation;
    };
    SpecmateDataService.prototype.resolve = function (url) {
        this.operations[url] = EOperation.RESOLVED;
    };
    SpecmateDataService.prototype.createElement = function (element, virtual) {
        if (virtual) {
            return Promise.resolve(this.createElementVirtual(element));
        }
        return this.createElementServer(element);
    };
    SpecmateDataService.prototype.readContents = function (url, virtual) {
        if (virtual) {
            return Promise.resolve(this.readContentsVirtual(url));
        }
        return this.readContentsServer(url);
    };
    SpecmateDataService.prototype.readElement = function (url, virtual) {
        if (virtual || this.operations[url] === EOperation.CREATE) {
            return Promise.resolve(this.readElementVirtual(url));
        }
        return this.readElementServer(url);
    };
    SpecmateDataService.prototype.updateElement = function (element, virtual) {
        if (virtual) {
            return Promise.resolve(this.updateElementVirtual(element));
        }
        return this.updateElementServer(element);
    };
    SpecmateDataService.prototype.deleteElement = function (url, virtual) {
        if (virtual || this.operations[url] === EOperation.CREATE) {
            return Promise.resolve(this.deleteElementVirtual(url));
        }
        return this.deleteElementServer(url);
    };
    SpecmateDataService.prototype.createElementVirtual = function (element) {
        this.schedule(element.url, EOperation.CREATE);
        return this.cache.addElement(element);
    };
    SpecmateDataService.prototype.readContentsVirtual = function (url) {
        return this.cache.readContents(url);
    };
    SpecmateDataService.prototype.readElementVirtual = function (url, forceServer) {
        return this.cache.readElement(url);
    };
    SpecmateDataService.prototype.updateElementVirtual = function (element) {
        this.schedule(element.url, EOperation.UPDATE);
        return this.cache.addElement(element);
    };
    SpecmateDataService.prototype.deleteElementVirtual = function (url) {
        this.schedule(url, EOperation.DELETE);
        return this.cache.deleteElement(url);
    };
    SpecmateDataService.prototype.createElementServer = function (element) {
        var _this = this;
        console.log("CREATE " + element.url);
        return this.serviceInterface.createElement(element).then(function () {
            _this.cache.addElement(element);
            _this.resolve(element.url);
            console.log("CREATE " + element.url + " DONE");
        });
    };
    SpecmateDataService.prototype.readContentsServer = function (url) {
        var _this = this;
        //        if (!this.cache.isCachedContents(url)) {
        return this.serviceInterface.readContents(url).then(function (contents) {
            contents.forEach(function (element) { return _this.cache.addElement(element); });
            return _this.cache.readContents(url);
        });
        //        }
        //        return Promise.resolve(this.cache.readContents(url));
    };
    SpecmateDataService.prototype.readElementServer = function (url) {
        var _this = this;
        //        if (!this.cache.isCachedContents(url)) {
        return this.serviceInterface.readElement(url).then(function (element) {
            _this.cache.addElement(element);
            return _this.cache.readElement(url);
        });
        //       }
        //       return Promise.resolve(this.cache.readElement(url));
    };
    SpecmateDataService.prototype.updateElementServer = function (element) {
        var _this = this;
        console.log("UPDATE " + element.url);
        return this.serviceInterface.updateElement(element).then(function () {
            _this.cache.addElement(element);
            _this.resolve(element.url);
            console.log("UPDATE " + element.url + " DONE");
        });
    };
    SpecmateDataService.prototype.deleteElementServer = function (url) {
        var _this = this;
        console.log("DELETE " + url);
        return this.serviceInterface.deleteElement(url).then(function () {
            _this.cache.deleteElement(url);
            _this.resolve(url);
            console.log("DELETE " + url + " DONE");
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