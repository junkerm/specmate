"use strict";
var Arrays_1 = require('../util/Arrays');
var Url_1 = require("../util/Url");
var Objects_1 = require("../util/Objects");
var DataCache = (function () {
    function DataCache() {
        this._operationStore = {};
        this._elementCache = {};
        this._contentsCache = {};
    }
    DataCache.prototype.clearOperationStore = function () {
        this._operationStore = {};
    };
    Object.defineProperty(DataCache.prototype, "operationStore", {
        get: function () {
            return this._operationStore;
        },
        enumerable: true,
        configurable: true
    });
    DataCache.prototype.storeElement = function (element, cold) {
        if (this.isElementCached(element.url)) {
            return this.updateElement(element, cold);
        }
        return this.createElement(element, cold);
    };
    DataCache.prototype.createElement = function (element, cold) {
        if (!cold) {
            this.addOperation(element.url, EOperation.CREATE);
        }
        this._elementCache[element.url] = element;
        if (!this.isContentsCached(Url_1.Url.parent(element.url))) {
            this._contentsCache[Url_1.Url.parent(element.url)] = [];
        }
        if (!this.isContentsCached(element.url)) {
            this._contentsCache[element.url] = [];
        }
        var parentContents = this._contentsCache[Url_1.Url.parent(element.url)];
        var index = parentContents.indexOf(element);
        if (index < 0) {
            parentContents.push(element);
        }
        else {
            Objects_1.Objects.clone(element, parentContents[index]);
        }
        return this._elementCache[element.url];
    };
    DataCache.prototype.updateElement = function (element, cold) {
        var cachedElement = this._elementCache[element.url];
        if (!Objects_1.Objects.equals(element, cachedElement)) {
            if (!cold) {
                this.addOperation(element.url, EOperation.UPDATE);
            }
            Objects_1.Objects.clone(element, cachedElement);
        }
        return this._elementCache[element.url];
    };
    DataCache.prototype.deleteElement = function (element) {
        if (this.isNewElement(element.url)) {
            this.clearElement(element);
        }
        this.addOperation(element.url, EOperation.DELETE);
        this._elementCache[element.url] = undefined;
        Arrays_1.Arrays.remove(this._contentsCache[Url_1.Url.parent(element.url)], element);
    };
    DataCache.prototype.getElement = function (url) {
        return this._elementCache[Url_1.Url.clean(url)];
    };
    DataCache.prototype.getContents = function (url) {
        return this._contentsCache[Url_1.Url.clean(url)];
    };
    DataCache.prototype.isElementCached = function (url) {
        return this._elementCache[url] != undefined && this._elementCache[url] != null;
    };
    DataCache.prototype.isContentsCached = function (url) {
        return this._contentsCache[url] != undefined && this._contentsCache[url] != null;
    };
    DataCache.prototype.addOperation = function (url, operation) {
        var currentOperation = this.operationStore[url];
        if (currentOperation === EOperation.CREATE && operation === EOperation.UPDATE) {
            return;
        }
        this._operationStore[url] = operation;
    };
    DataCache.prototype.resolveOperation = function (url, operation) {
        this.operationStore[url] = EOperation.RESOLVED;
    };
    DataCache.prototype.removeResolvedOperations = function () {
        var cleaned = {};
        for (var url in this._operationStore) {
            if (this._operationStore[url] !== EOperation.RESOLVED) {
                cleaned[url] = this._operationStore[url];
            }
        }
        this._operationStore = cleaned;
    };
    DataCache.prototype.isNewElement = function (url) {
        return this._operationStore[url] === EOperation.CREATE;
    };
    DataCache.prototype.clearElement = function (element) {
        this._operationStore[element.url] = EOperation.RESOLVED;
        this._elementCache[element.url] = undefined;
        this._contentsCache[element.url] = undefined;
        Arrays_1.Arrays.remove(this._contentsCache[Url_1.Url.parent(element.url)], element);
    };
    return DataCache;
}());
exports.DataCache = DataCache;
(function (EOperation) {
    EOperation[EOperation["CREATE"] = 0] = "CREATE";
    EOperation[EOperation["DELETE"] = 1] = "DELETE";
    EOperation[EOperation["UPDATE"] = 2] = "UPDATE";
    EOperation[EOperation["RESOLVED"] = 3] = "RESOLVED";
})(exports.EOperation || (exports.EOperation = {}));
var EOperation = exports.EOperation;
//# sourceMappingURL=data-cache.js.map