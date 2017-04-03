"use strict";
var operations_1 = require("./operations");
var command_1 = require("./command");
var Scheduler = (function () {
    function Scheduler(dataService) {
        this.dataService = dataService;
        this.commands = [];
    }
    Scheduler.prototype.commit = function () {
        var _this = this;
        return this.chainCommits().then(function () {
            _this.clearCommits();
        });
    };
    Scheduler.prototype.clearCommits = function () {
        this.commands = [];
    };
    Scheduler.prototype.chainCommits = function () {
        var _this = this;
        var chain = Promise.resolve();
        var _loop_1 = function(i) {
            chain = chain.then(function () {
                return _this.dataService.getPromiseForCommand(_this.commands[i]);
            });
        };
        for (var i = 0; i < this.commands.length; i++) {
            _loop_1(i);
        }
        return chain;
    };
    Scheduler.prototype.getCommands = function (url) {
        return this.commands.filter(function (command) { return command.url === url; });
    };
    Scheduler.prototype.getLastStoredValue = function (url) {
        var commands = this.getCommands(url);
        if (commands && commands.length > 0) {
            return commands[commands.length - 1].newValue;
        }
        return undefined;
    };
    Scheduler.prototype.getFirstCommand = function (url) {
        return this.getCommands(url).filter(function (command) { return command.operation !== operations_1.EOperation.INIT && command.operation !== operations_1.EOperation.RESOLVED; })[0];
    };
    Scheduler.prototype.initElement = function (element) {
        var command = new command_1.Command(element.url, element, element, operations_1.EOperation.INIT);
    };
    Scheduler.prototype.schedule = function (url, operation, newValue, originalValue) {
        if (!originalValue) {
            originalValue = this.getLastStoredValue(url);
        }
        var command = new command_1.Command(url, originalValue, newValue, operation);
        this.commands.push(command);
        console.log("SCHEDULING " + operations_1.EOperation[operation] + " FOR " + url);
        console.log(command);
    };
    Scheduler.prototype.isVirtualElement = function (url) {
        return this.getCommands(url).some(function (command) { return command.operation === operations_1.EOperation.CREATE; });
    };
    Scheduler.prototype.resolve = function (url) {
        console.log(this.commands);
        var firstCommand = this.getFirstCommand(url);
        if (firstCommand) {
            firstCommand.resolve();
            return;
        }
        console.log(this.commands);
        throw new Error('Tried to resolve ' + url + ', but no command was found.');
    };
    return Scheduler;
}());
exports.Scheduler = Scheduler;
//# sourceMappingURL=scheduler.js.map