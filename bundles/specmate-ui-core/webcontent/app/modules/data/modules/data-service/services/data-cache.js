"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var sort_1 = require("../../../../../util/sort");
var objects_1 = require("../../../../../util/objects");
var url_1 = require("../../../../../util/url");
var arrays_1 = require("../../../../../util/arrays");
var DataCache = (function () {
    function DataCache() {
        this.elementStore = {};
        this.contentsStore = {};
    }
    DataCache.prototype.isCachedElement = function (url) {
        return this.elementStore[url] !== undefined;
    };
    DataCache.prototype.isCachedContents = function (url) {
        return this.contentsStore[url] !== undefined;
    };
    DataCache.prototype.addElement = function (element) {
        if (this.isCachedElement(element.url)) {
            this.updateElement(element);
            return;
        }
        this.createElement(element);
    };
    DataCache.prototype.updateContents = function (contents, url) {
        var _this = this;
        if (!contents) {
            return;
        }
        sort_1.Sort.sortArray(contents).forEach(function (element) { return _this.addElement(element); });
        if (!this.isCachedContents(url)) {
            this.contentsStore[url] = [];
        }
        var storedContents = this.readContents(url);
        var elementsToDelete = storedContents.filter(function (storedElement) {
            return contents.find(function (element) { return element.url === storedElement.url; }) === undefined;
        });
        elementsToDelete.forEach(function (element) { return _this.deleteElement(element.url); });
    };
    DataCache.prototype.readElement = function (url) {
        return this.elementStore[url];
    };
    DataCache.prototype.readContents = function (url) {
        if (!this.contentsStore[url]) {
            return undefined;
        }
        return this.contentsStore[url];
    };
    DataCache.prototype.deleteElement = function (url) {
        // always remove from parent and then remove the element itself. Otherwise, removal from parent does not work, since this relies on the element being in the element cache.
        this.removeFromParentContents(url);
        delete this.elementStore[url];
        var childrenUrls = this.getChildrenUrls(url);
        for (var i = 0; i < childrenUrls.length; i++) {
            this.removeFromParentContents(childrenUrls[i]);
            delete this.elementStore[childrenUrls[i]];
        }
    };
    DataCache.prototype.updateElement = function (element) {
        objects_1.Objects.clone(element, this.elementStore[element.url]);
    };
    DataCache.prototype.createElement = function (element) {
        this.elementStore[element.url] = element;
        this.addToParentContents(element);
        return Promise.resolve();
    };
    DataCache.prototype.getParentContents = function (url) {
        var parentUrl = url_1.Url.parent(url);
        sort_1.Sort.sortArrayInPlace(this.contentsStore[parentUrl]);
        return this.contentsStore[parentUrl];
    };
    DataCache.prototype.getChildrenUrls = function (url) {
        var childrenUrls = [];
        for (var storedUrl in this.contentsStore) {
            if (storedUrl.startsWith(url + url_1.Url.SEP)) {
                childrenUrls.push(storedUrl);
            }
        }
        return childrenUrls;
    };
    DataCache.prototype.addToParentContents = function (element) {
        var parentUrl = url_1.Url.parent(element.url);
        if (!this.isCachedContents(parentUrl)) {
            this.contentsStore[parentUrl] = [];
        }
        var parentContents = this.getParentContents(element.url);
        var index = parentContents.indexOf(element);
        if (parentContents.indexOf(element) < 0) {
            sort_1.Sort.insert(element, parentContents);
        }
        else {
            console.error('Tried to add an existing element to parent! ' + element.url);
        }
    };
    DataCache.prototype.removeFromParentContents = function (url) {
        var parentContents = this.getParentContents(url);
        if (parentContents) {
            var element = this.elementStore[url];
            arrays_1.Arrays.remove(parentContents, element);
        }
    };
    return DataCache;
}());
exports.DataCache = DataCache;
//# sourceMappingURL=data-cache.js.map