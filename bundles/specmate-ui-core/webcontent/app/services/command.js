"use strict";
var Objects_1 = require("../util/Objects");
var operations_1 = require("./operations");
var Command = (function () {
    function Command(url, originalValue, newValue, operation) {
        this.url = url;
        this.operation = operation;
        this._originalValue = Objects_1.Objects.clone(originalValue);
        this._newValue = Objects_1.Objects.clone(newValue);
    }
    Object.defineProperty(Command.prototype, "originalValue", {
        get: function () {
            return this._originalValue;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(Command.prototype, "newValue", {
        get: function () {
            return this._newValue;
        },
        enumerable: true,
        configurable: true
    });
    Command.prototype.resolve = function () {
        this.operation = operations_1.EOperation.RESOLVED;
    };
    return Command;
}());
exports.Command = Command;
//# sourceMappingURL=command.js.map