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
var operations_1 = require("./operations");
var scheduler_1 = require("./scheduler");
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
        this.busy = false;
        this.currentTaskName = '';
        this.cache = new data_cache_1.DataCache();
        this.serviceInterface = new service_interface_1.ServiceInterface(http);
        this.scheduler = new scheduler_1.Scheduler(this);
    }
    SpecmateDataService.prototype.createElement = function (element, virtual) {
        if (virtual) {
            return Promise.resolve(this.createElementVirtual(element));
        }
        return this.createElementServer(element);
    };
    SpecmateDataService.prototype.readContents = function (url, virtual) {
        var _this = this;
        this.busy = true;
        if (virtual) {
            return Promise.resolve(this.readContentsVirtual(url)).then(function (contents) { return _this.readContentsComplete(contents); });
        }
        return this.readContentsServer(url).then(function (contents) { return _this.readContentsComplete(contents); });
    };
    SpecmateDataService.prototype.readContentsComplete = function (contents) {
        this.busy = false;
        return contents;
    };
    SpecmateDataService.prototype.readElement = function (url, virtual) {
        var _this = this;
        if (virtual || this.scheduler.isVirtualElement(url)) {
            return Promise.resolve(this.readElementVirtual(url)).then(function (element) { return _this.readElementComplete(element); });
        }
        return this.readElementServer(url).then(function (element) { return _this.readElementComplete(element); });
    };
    SpecmateDataService.prototype.readElementComplete = function (element) {
        this.busy = false;
        this.scheduler.initElement(element);
        return element;
    };
    SpecmateDataService.prototype.updateElement = function (element, virtual) {
        if (virtual) {
            return Promise.resolve(this.updateElementVirtual(element));
        }
        return this.updateElementServer(element);
    };
    SpecmateDataService.prototype.deleteElement = function (url, virtual) {
        if (virtual || this.scheduler.isVirtualElement(url)) {
            return Promise.resolve(this.deleteElementVirtual(url));
        }
        return this.deleteElementServer(url);
    };
    SpecmateDataService.prototype.getPromiseForCommand = function (command) {
        var element = command.newValue;
        if (command.operation === operations_1.EOperation.CREATE) {
            return this.createElementServer(command.newValue);
        }
        if (command.operation === operations_1.EOperation.UPDATE) {
            return this.updateElementServer(command.newValue);
        }
        if (command.operation === operations_1.EOperation.DELETE) {
            return this.deleteElementServer(command.originalValue.url);
        }
        throw new Error('No suitable command found!');
    };
    SpecmateDataService.prototype.clearCommits = function () {
        this.scheduler.clearCommits();
    };
    Object.defineProperty(SpecmateDataService.prototype, "hasCommits", {
        get: function () {
            return this.scheduler.hasCommits;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(SpecmateDataService.prototype, "countCommits", {
        get: function () {
            return this.scheduler.countOpenCommits;
        },
        enumerable: true,
        configurable: true
    });
    SpecmateDataService.prototype.commit = function (taskName) {
        var _this = this;
        this.busy = true;
        this.currentTaskName = taskName;
        return this.scheduler.commit().then(function () { _this.busy = false; });
    };
    SpecmateDataService.prototype.createElementVirtual = function (element) {
        this.scheduler.schedule(element.url, operations_1.EOperation.CREATE, element, undefined);
        return this.cache.addElement(element);
    };
    SpecmateDataService.prototype.readContentsVirtual = function (url) {
        return this.cache.readContents(url);
    };
    SpecmateDataService.prototype.readElementVirtual = function (url, forceServer) {
        return this.cache.readElement(url);
    };
    SpecmateDataService.prototype.updateElementVirtual = function (element) {
        this.scheduler.schedule(element.url, operations_1.EOperation.UPDATE, element);
        return this.cache.addElement(element);
    };
    SpecmateDataService.prototype.deleteElementVirtual = function (url) {
        this.scheduler.schedule(url, operations_1.EOperation.DELETE, undefined, this.readElementVirtual(url));
        return this.cache.deleteElement(url);
    };
    SpecmateDataService.prototype.createElementServer = function (element) {
        var _this = this;
        console.log("CREATE " + element.url);
        return this.serviceInterface.createElement(element).then(function () {
            //this.cache.addElement(element);
            _this.scheduler.resolve(element.url);
            console.log("CREATE " + element.url + " DONE");
        });
    };
    SpecmateDataService.prototype.readContentsServer = function (url) {
        var _this = this;
        return this.serviceInterface.readContents(url).then(function (contents) {
            _this.cache.updateContents(contents, url);
            return _this.cache.readContents(url);
        });
    };
    SpecmateDataService.prototype.readElementServer = function (url) {
        var _this = this;
        return this.serviceInterface.readElement(url).then(function (element) {
            _this.cache.addElement(element);
            return _this.cache.readElement(url);
        });
    };
    SpecmateDataService.prototype.updateElementServer = function (element) {
        var _this = this;
        console.log("UPDATE " + element.url);
        return this.serviceInterface.updateElement(element).then(function () {
            //this.cache.addElement(element);
            _this.scheduler.resolve(element.url);
            console.log("UPDATE " + element.url + " DONE");
        });
    };
    SpecmateDataService.prototype.deleteElementServer = function (url) {
        var _this = this;
        console.log("DELETE " + url);
        return this.serviceInterface.deleteElement(url).then(function () {
            //this.cache.deleteElement(url);
            _this.scheduler.resolve(url);
            console.log("DELETE " + url + " DONE");
        });
    };
    SpecmateDataService.prototype.performOperations = function (url, operation, payload) {
        var _this = this;
        this.busy = true;
        return this.serviceInterface.performOperation(url, operation, payload).then(function () { _this.busy = false; });
    };
    SpecmateDataService.prototype.performQuery = function (url, operation, parameters) {
        var _this = this;
        this.busy = true;
        return this.serviceInterface.performQuery(url, operation, parameters).then(function (result) { _this.busy = false; return result; });
    };
    SpecmateDataService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [http_1.Http])
    ], SpecmateDataService);
    return SpecmateDataService;
}());
exports.SpecmateDataService = SpecmateDataService;
//# sourceMappingURL=specmate-data.service.js.map