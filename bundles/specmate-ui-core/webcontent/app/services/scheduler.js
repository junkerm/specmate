"use strict";
var operations_1 = require("./operations");
var Scheduler = (function () {
    function Scheduler(dataService) {
        this.dataService = dataService;
        this.operations = {};
    }
    Scheduler.prototype.commit = function () {
        var _this = this;
        return this.chainCommits().then(function () {
            _this.cleanOperations();
            _this.clearCommits();
        });
    };
    Scheduler.prototype.clearCommits = function () {
        this.operations = {};
    };
    Scheduler.prototype.cleanOperations = function () {
        var clean = {};
        for (var url in this.operations) {
            if (this.operations[url] && this.operations[url] !== operations_1.EOperation.RESOLVED) {
                clean[url] = this.operations[url];
            }
        }
        this.operations = clean;
    };
    Scheduler.prototype.chainCommits = function () {
        var _this = this;
        var chain = Promise.resolve();
        var commits = this.orderCommits();
        var _loop_1 = function(i) {
            var url = commits[i];
            chain = chain.then(function () {
                return _this.dataService.getPromiseForUrl(url);
            });
        };
        for (var i = 0; i < commits.length; i++) {
            _loop_1(i);
        }
        return chain;
    };
    Scheduler.prototype.orderCommits = function () {
        var commits = [];
        this.getCreateCommits().forEach(function (url) { return commits.push(url); });
        this.getUpdateCommits().forEach(function (url) { return commits.push(url); });
        this.getDeleteCommits().forEach(function (url) { return commits.push(url); });
        return commits;
    };
    Scheduler.prototype.getCreateCommits = function () {
        return this.getCommitsOfOperation(operations_1.EOperation.CREATE);
    };
    Scheduler.prototype.getUpdateCommits = function () {
        return this.getCommitsOfOperation(operations_1.EOperation.UPDATE);
    };
    Scheduler.prototype.getDeleteCommits = function () {
        return this.getCommitsOfOperation(operations_1.EOperation.DELETE);
    };
    Scheduler.prototype.getCommitsOfOperation = function (operation) {
        var commits = [];
        for (var url in this.operations) {
            if (this.operations[url] === operation) {
                commits.push(url);
            }
        }
        return commits;
    };
    Scheduler.prototype.schedule = function (url, operation) {
        var previousOperation = this.operations[url];
        if (previousOperation === operations_1.EOperation.CREATE) {
            if (operation === operations_1.EOperation.CREATE) {
                console.error('Trying to overwrite element ' + url);
            }
            else if (operation === operations_1.EOperation.UPDATE) {
                operation = operations_1.EOperation.CREATE;
            }
            else if (operation === operations_1.EOperation.DELETE) {
                operation = operations_1.EOperation.RESOLVED;
            }
        }
        else if (previousOperation === operations_1.EOperation.UPDATE) {
            if (operation === operations_1.EOperation.CREATE) {
                console.error('Trying to overwrite element ' + url);
            }
        }
        else if (previousOperation === operations_1.EOperation.DELETE) {
            if (operation === operations_1.EOperation.CREATE) {
                operation = operations_1.EOperation.UPDATE;
            }
            else if (operation === operations_1.EOperation.UPDATE) {
                console.error('Trying to update deleted element ' + url);
            }
            else if (operation === operations_1.EOperation.DELETE) {
                console.error('Trying to delete deleted element ' + url);
            }
        }
        console.log("SCHEDULING " + operations_1.EOperation[operation] + " FOR " + url);
        this.operations[url] = operation;
    };
    Scheduler.prototype.resolve = function (url) {
        this.operations[url] = operations_1.EOperation.RESOLVED;
    };
    Scheduler.prototype.isOperation = function (url, operation) {
        return this.operations[url] === operation;
    };
    return Scheduler;
}());
exports.Scheduler = Scheduler;
//# sourceMappingURL=scheduler.js.map