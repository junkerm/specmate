"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var angular2_uuid_1 = require("angular2-uuid");
var Id = (function () {
    function Id() {
    }
    Object.defineProperty(Id, "uuid", {
        /** Generates a new id (we hope we do not get duplicates. We do not check for that.) */
        get: function () {
            return angular2_uuid_1.UUID.UUID();
        },
        enumerable: true,
        configurable: true
    });
    return Id;
}());
exports.Id = Id;
//# sourceMappingURL=Id.js.map