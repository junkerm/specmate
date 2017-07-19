"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
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
    Object.defineProperty(Scheduler.prototype, "realCommands", {
        get: function () {
            return this.commands.filter(function (command) { return command.operation !== operations_1.EOperation.INIT; });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(Scheduler.prototype, "lastCommand", {
        get: function () {
            return this.realCommands[this.realCommands.length - 1];
        },
        enumerable: true,
        configurable: true
    });
    Scheduler.prototype.popCommand = function () {
        var index = this.commands.indexOf(this.lastCommand);
        var command = this.commands[index];
        this.commands.splice(index, 1);
        return command;
    };
    Scheduler.prototype.getInitialValue = function (url) {
        console.log(this.commands);
        console.log(this.realCommands);
        var initCommand = this.commands.filter(function (command) { return command.operation === operations_1.EOperation.INIT && command.originalValue.url === url; })[0];
        if (initCommand) {
            return initCommand.originalValue;
        }
        return undefined;
    };
    Scheduler.prototype.undo = function () {
        var lastCommand = this.popCommand();
        if (!lastCommand) {
            console.log("OUT OF HISTORY");
            return;
        }
        var originalValue = lastCommand.originalValue;
        // First, we check whether this element was initialized (this happens, if it was read from the server)
        if (!originalValue) {
            originalValue = this.getInitialValue(lastCommand.url);
        }
        switch (lastCommand.operation) {
            case operations_1.EOperation.CREATE:
                this.dataService.undoCreate(lastCommand.newValue.url);
                break;
            case operations_1.EOperation.UPDATE:
                this.dataService.undoUpdate(originalValue);
                break;
            case operations_1.EOperation.DELETE:
                this.dataService.undoDelete(originalValue);
                break;
            default:
                break;
        }
    };
    Scheduler.prototype.clearCommits = function () {
        this.commands = this.commands.filter(function (command) { return command.operation !== operations_1.EOperation.INIT; });
    };
    Object.defineProperty(Scheduler.prototype, "hasCommits", {
        get: function () {
            return this.countOpenCommits > 0;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(Scheduler.prototype, "countOpenCommits", {
        get: function () {
            return this.commands.filter(function (command) { return command.operation !== operations_1.EOperation.INIT && command.operation !== operations_1.EOperation.RESOLVED; }).length;
        },
        enumerable: true,
        configurable: true
    });
    Scheduler.prototype.chainCommits = function () {
        var _this = this;
        var chain = Promise.resolve();
        var _loop_1 = function (i) {
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
        this.commands.push(command);
    };
    Scheduler.prototype.schedule = function (url, operation, newValue, originalValue) {
        if (!originalValue) {
            originalValue = this.getLastStoredValue(url);
        }
        var command = new command_1.Command(url, originalValue, newValue, operation);
        this.commands.push(command);
    };
    Scheduler.prototype.isVirtualElement = function (url) {
        return this.getCommands(url).some(function (command) { return command.operation === operations_1.EOperation.CREATE; });
    };
    Scheduler.prototype.resolve = function (url) {
        var firstCommand = this.getFirstCommand(url);
        if (firstCommand) {
            firstCommand.resolve();
            return;
        }
        throw new Error('Tried to resolve ' + url + ', but no command was found.');
    };
    return Scheduler;
}());
exports.Scheduler = Scheduler;
//# sourceMappingURL=scheduler.js.map