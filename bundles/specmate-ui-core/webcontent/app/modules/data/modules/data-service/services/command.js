"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var e_operation_1 = require("./e-operation");
var objects_1 = require("../../../../../util/objects");
var Command = /** @class */ (function () {
    function Command(url, originalValue, newValue, operation, compoundId) {
        this.url = url;
        this.operation = operation;
        this.compoundId = compoundId;
        this._originalValue = objects_1.Objects.clone(originalValue);
        this._newValue = objects_1.Objects.clone(newValue);
        if (operation === e_operation_1.EOperation.INIT) {
            this.resolve();
        }
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
        this._resolved = true;
        if (this.operation === e_operation_1.EOperation.CREATE) {
            this.operation = e_operation_1.EOperation.INIT;
            this._originalValue = objects_1.Objects.clone(this._newValue);
        }
    };
    Object.defineProperty(Command.prototype, "isResolved", {
        get: function () {
            return this._resolved;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(Command.prototype, "changedFields", {
        get: function () {
            if (!this._changedFields) {
                this._changedFields = objects_1.Objects.changedFields(this._originalValue, this._newValue).sort();
            }
            return this._changedFields;
        },
        enumerable: true,
        configurable: true
    });
    Command.prototype.changedSameFields = function (other) {
        if (this.changedFields.length !== other.changedFields.length) {
            return false;
        }
        for (var i = 0; i < this.changedFields.length; i++) {
            if (this.changedFields[i] !== other.changedFields[i]) {
                return false;
            }
        }
        return true;
    };
    Object.defineProperty(Command.prototype, "isDifference", {
        get: function () {
            return this.changedFields.length > 0;
        },
        enumerable: true,
        configurable: true
    });
    Command.prototype.mergeKeepOriginalValue = function (next) {
        if (this.isMergeable(next)) {
            throw new Error('Tried to merge commands with conflicting operations.');
        }
        return new Command(this.url, this._originalValue, next._newValue, this.operation, next.compoundId);
    };
    Command.prototype.isMergeable = function (other) {
        return this.operation !== e_operation_1.EOperation.UPDATE || this.operation !== other.operation;
    };
    return Command;
}());
exports.Command = Command;
//# sourceMappingURL=command.js.map