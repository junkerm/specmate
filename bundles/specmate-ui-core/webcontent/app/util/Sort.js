"use strict";
var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
Object.defineProperty(exports, "__esModule", { value: true });
var Requirement_1 = require("../model/Requirement");
var Comparer = (function () {
    function Comparer() {
    }
    Comparer.prototype.compare = function (element1, element2) {
        if (this.canCompare(element1, element2)) {
            return this.compareElements(element1, element2);
        }
        throw new Error('Cannot compare elements!');
    };
    Comparer.isNumeric = function (str) {
        return !isNaN(+str);
    };
    return Comparer;
}());
var FieldComparer = (function (_super) {
    __extends(FieldComparer, _super);
    function FieldComparer(sortBy) {
        var _this = _super.call(this) || this;
        _this.sortBy = sortBy;
        return _this;
    }
    FieldComparer.prototype.canCompare = function (element1, element2) {
        return element1[this.sortBy] !== undefined && element2[this.sortBy] !== undefined;
    };
    FieldComparer.prototype.compareElements = function (element1, element2) {
        if (typeof element1[this.sortBy] === 'number' && typeof element2[this.sortBy] === 'number') {
            return element1[this.sortBy] - element2[this.sortBy];
        }
        else if (typeof element1[this.sortBy] === 'string' && typeof element2[this.sortBy] === 'string') {
            if (Comparer.isNumeric(element1[this.sortBy]) && Comparer.isNumeric(element2[this.sortBy])) {
                return parseInt(element1[this.sortBy]) - parseInt(element2[this.sortBy]);
            }
            return element1[this.sortBy].localeCompare(element2[this.sortBy]);
        }
        throw new Error('Tried to compare neither strings nor numbers.');
    };
    return FieldComparer;
}(Comparer));
var ClassAwareComparer = (function (_super) {
    __extends(ClassAwareComparer, _super);
    function ClassAwareComparer(classToCompare, sortBy) {
        var _this = _super.call(this, sortBy) || this;
        _this.classToCompare = classToCompare;
        return _this;
    }
    ClassAwareComparer.prototype.canCompare = function (element1, element2) {
        return _super.prototype.canCompare.call(this, element1, element2) && element1.className === this.classToCompare.className && element2.className === this.classToCompare.className;
    };
    Object.defineProperty(ClassAwareComparer.prototype, "className", {
        get: function () {
            return this.classToCompare.className;
        },
        enumerable: true,
        configurable: true
    });
    return ClassAwareComparer;
}(FieldComparer));
var Sort = (function () {
    function Sort() {
    }
    Sort.compareElements = function (element1, element2) {
        var comparer = Sort.comparers.find(function (comparer) { return comparer.canCompare(element1, element2); });
        if (comparer) {
            return comparer.compare(element1, element2);
        }
        throw new Error('Could not find comparer!');
    };
    Sort.sortArray = function (elements) {
        if (!elements) {
            return elements;
        }
        return elements.sort(function (e1, e2) { return Sort.compareElements(e1, e2); });
    };
    Sort.sortArrayInPlace = function (array) {
        if (!array) {
            return;
        }
        var sorted = Sort.sortArray(array);
        sorted.forEach(function (element, index) {
            if (array[index] !== element) {
                array[index] = element;
            }
        });
    };
    Sort.insert = function (element, elements) {
        if (elements.indexOf(element) >= 0) {
            return;
        }
        var index = elements.findIndex(function (containedElement) { return containedElement.className === element.className && Sort.compareElements(containedElement, element) > 0; });
        if (index >= elements.length || index < 0) {
            elements.push(element);
            return;
        }
        elements.splice(index, 0, element);
    };
    Sort.comparers = [
        new ClassAwareComparer(Requirement_1.Requirement, 'extId'),
        new FieldComparer('position'),
        new FieldComparer('name'),
        new FieldComparer('url'),
        new FieldComparer('id')
    ];
    return Sort;
}());
exports.Sort = Sort;
//# sourceMappingURL=Sort.js.map