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
var http_1 = require("@angular/http");
var data_cache_1 = require("./data-cache");
var service_interface_1 = require("./service-interface");
var e_operation_1 = require("../commands/e-operation");
var scheduler_1 = require("../commands/scheduler");
var logging_service_1 = require("../logging/logging.service");
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
    function SpecmateDataService(http, logger) {
        this.http = http;
        this.logger = logger;
        this.busy = false;
        this.currentTaskName = '';
        this.cache = new data_cache_1.DataCache();
        this.serviceInterface = new service_interface_1.ServiceInterface(http);
        this.scheduler = new scheduler_1.Scheduler(this);
    }
    SpecmateDataService.prototype.checkConnection = function () {
        return this.serviceInterface.checkConnection();
    };
    SpecmateDataService.prototype.createElement = function (element, virtual, compoundId) {
        if (virtual) {
            return Promise.resolve(this.createElementVirtual(element, compoundId));
        }
        return this.createElementServer(element);
    };
    SpecmateDataService.prototype.readContents = function (url, virtual) {
        var _this = this;
        this.busy = true;
        if (virtual || this.scheduler.isVirtualElement(url)) {
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
    SpecmateDataService.prototype.updateElement = function (element, virtual, compoundId) {
        if (virtual) {
            return Promise.resolve(this.updateElementVirtual(element, compoundId));
        }
        return this.updateElementServer(element);
    };
    SpecmateDataService.prototype.deleteElement = function (url, virtual, compoundId) {
        if (virtual || this.scheduler.isVirtualElement(url)) {
            return Promise.resolve(this.deleteElementVirtual(url, compoundId));
        }
        return this.deleteElementServer(url);
    };
    SpecmateDataService.prototype.getPromiseForCommand = function (command) {
        var element = command.newValue;
        switch (command.operation) {
            case e_operation_1.EOperation.CREATE:
                return this.createElementServer(command.newValue);
            case e_operation_1.EOperation.UPDATE:
                return this.updateElementServer(command.newValue);
            case e_operation_1.EOperation.DELETE:
                return this.deleteElementServer(command.originalValue.url);
        }
        throw new Error('No suitable command found! Probably, we tried to re-execute an already resolved command.');
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
    SpecmateDataService.prototype.undo = function () {
        this.scheduler.undo();
    };
    SpecmateDataService.prototype.undoCreate = function (url) {
        this.cache.deleteElement(url);
    };
    SpecmateDataService.prototype.undoUpdate = function (originalValue) {
        this.cache.addElement(originalValue);
    };
    SpecmateDataService.prototype.undoDelete = function (originalValue) {
        this.cache.addElement(originalValue);
    };
    SpecmateDataService.prototype.createElementVirtual = function (element, compoundId) {
        this.scheduler.schedule(element.url, e_operation_1.EOperation.CREATE, element, undefined, compoundId);
        return this.cache.addElement(element);
    };
    SpecmateDataService.prototype.readContentsVirtual = function (url) {
        return this.cache.readContents(url);
    };
    SpecmateDataService.prototype.readElementVirtual = function (url, forceServer) {
        return this.cache.readElement(url);
    };
    SpecmateDataService.prototype.updateElementVirtual = function (element, compoundId) {
        this.scheduler.schedule(element.url, e_operation_1.EOperation.UPDATE, element, undefined, compoundId);
        this.cache.addElement(element);
    };
    SpecmateDataService.prototype.deleteElementVirtual = function (url, compoundId) {
        this.scheduler.schedule(url, e_operation_1.EOperation.DELETE, undefined, this.readElementVirtual(url), compoundId);
        this.cache.deleteElement(url);
    };
    SpecmateDataService.prototype.createElementServer = function (element) {
        var _this = this;
        this.logStart('Create', element);
        return this.serviceInterface.createElement(element).then(function () {
            _this.scheduler.resolve(element.url);
            _this.logFinished('Create', element);
        }).catch(function () { return _this.handleError('Element could not be saved.', element); });
    };
    SpecmateDataService.prototype.readContentsServer = function (url) {
        var _this = this;
        return this.serviceInterface.readContents(url).then(function (contents) {
            _this.cache.updateContents(contents, url);
            contents.forEach(function (element) { return _this.scheduler.initElement(element); });
            return _this.cache.readContents(url);
        }).catch(function () {
            _this.handleError('Contents could not be read. ' + url);
            return undefined;
        });
    };
    SpecmateDataService.prototype.readElementServer = function (url) {
        var _this = this;
        return this.serviceInterface.readElement(url).then(function (element) {
            _this.cache.addElement(element);
            return _this.cache.readElement(url);
        }).catch(function () {
            _this.handleError('Element could not be read. ' + url);
            return undefined;
        });
    };
    SpecmateDataService.prototype.updateElementServer = function (element) {
        var _this = this;
        this.logStart('Update', element);
        return this.serviceInterface.updateElement(element).then(function () {
            _this.scheduler.resolve(element.url);
            _this.logFinished('Update', element);
        }).catch(function () { return _this.handleError('Element could not be updated. ', element); });
    };
    SpecmateDataService.prototype.deleteElementServer = function (url) {
        var _this = this;
        this.logStart('Delete ' + url);
        return this.serviceInterface.deleteElement(url).then(function () {
            _this.scheduler.resolve(url);
            _this.logFinished('Delete' + url);
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
        this.logStart('Query Url: ' + url + ' Operation: ' + operation);
        return this.serviceInterface.performQuery(url, operation, parameters).then(function (result) {
            _this.busy = false;
            _this.logFinished('Query Url: ' + url + ' Operation: ' + operation);
            return result;
        }).catch(function () { return _this.handleError('Operation could not be performed.  Url: ' + url + ' Operation: ' + operation); });
    };
    SpecmateDataService.prototype.logStart = function (message, element) {
        this.logger.info('Trying: ' + message, element);
    };
    SpecmateDataService.prototype.logFinished = function (message, element) {
        this.logger.info('Success: ' + message, element);
    };
    SpecmateDataService.prototype.handleError = function (message, element) {
        this.logger.error('Error in data service: ' + message, element);
    };
    SpecmateDataService = __decorate([
        core_1.Injectable(),
        __metadata("design:paramtypes", [http_1.Http, logging_service_1.LoggingService])
    ], SpecmateDataService);
    return SpecmateDataService;
}());
exports.SpecmateDataService = SpecmateDataService;
//# sourceMappingURL=specmate-data.service.js.map