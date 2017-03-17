"use strict";
var Url_1 = require("../util/Url");
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
    DataCache.prototype.storeElement = function (element) {
        if (this.isCached(element.url)) {
            return this.updateElement(element);
        }
        return this.createElement(element);
    };
    DataCache.prototype.createElement = function (element) {
        this._operationStore[element.url] = EOperation.CREATE;
        if (!this._elementCache[element.url]) {
            this._elementCache[element.url] = element;
        }
        else {
            this.clone(element, this._elementCache[element.url]);
        }
        if (!this._contentsCache[Url_1.Url.parent(element.url)]) {
            this._contentsCache[Url_1.Url.parent(element.url)] = new Array();
        }
        var parentContents = this._contentsCache[Url_1.Url.parent(element.url)];
        var index = parentContents.indexOf(element);
        if (index < 0) {
            parentContents.push(element);
        }
        else {
            this.clone(element, parentContents[index]);
        }
        return this._elementCache[element.url];
    };
    DataCache.prototype.updateElement = function (element) {
        this._operationStore[element.url] = EOperation.UPDATE;
        return this._elementCache[element.url];
    };
    DataCache.prototype.deleteElement = function (element) {
        this._operationStore[element.url] = EOperation.DELETE;
        this._elementCache[element.url] = undefined;
        var parentContents = this._contentsCache[Url_1.Url.parent(element.url)];
        var index = parentContents.indexOf(element);
        if (index >= 0) {
            parentContents.splice(index, 1);
        }
    };
    DataCache.prototype.getElement = function (url) {
        return this._elementCache[Url_1.Url.clean(url)];
    };
    DataCache.prototype.getContents = function (url) {
        return this._contentsCache[Url_1.Url.clean(url)];
    };
    DataCache.prototype.clone = function (source, target) {
        for (var name_1 in source) {
            if (typeof (source[name_1]) !== 'object' && typeof (source[name_1]) !== 'function') {
                target[name_1] = source[name_1];
            }
            else {
                this.clone(source[name_1], target[name_1]);
            }
        }
    };
    DataCache.prototype.isCached = function (url) {
        return this._elementCache[url] != undefined && this._elementCache[url] != null;
    };
    return DataCache;
}());
exports.DataCache = DataCache;
(function (EOperation) {
    EOperation[EOperation["CREATE"] = 0] = "CREATE";
    EOperation[EOperation["DELETE"] = 1] = "DELETE";
    EOperation[EOperation["UPDATE"] = 2] = "UPDATE";
    EOperation[EOperation["NONE"] = 3] = "NONE";
})(exports.EOperation || (exports.EOperation = {}));
var EOperation = exports.EOperation;
//# sourceMappingURL=data-cache.js.map