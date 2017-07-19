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
    Object.defineProperty(Scheduler.prototype, "unresolvedCommands", {
        get: function () {
            return this.commands.filter(function (command) { return !command.isResolved; });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(Scheduler.prototype, "lastCommand", {
        get: function () {
            return this.unresolvedCommands[this.unresolvedCommands.length - 1];
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
        this.commands = this.commands.filter(function (command) { return command.operation === operations_1.EOperation.INIT; });
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
            return this.unresolvedCommands.length;
        },
        enumerable: true,
        configurable: true
    });
    Scheduler.prototype.chainCommits = function () {
        var _this = this;
        var chain = Promise.resolve();
        var unresolvedCommands = this.unresolvedCommands;
        var _loop_1 = function (i) {
            chain = chain.then(function () {
                return _this.dataService.getPromiseForCommand(unresolvedCommands[i]);
            });
        };
        for (var i = 0; i < unresolvedCommands.length; i++) {
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
    Scheduler.prototype.getFirstUnresolvedCommand = function (url) {
        return this.unresolvedCommands.filter(function (command) { return command.url === url; })[0];
    };
    Scheduler.prototype.getLastUnresolvedCommand = function (url) {
        if (!url) {
            return this.unresolvedCommands[this.unresolvedCommands.length - 1];
        }
        var commandsForUrl = this.unresolvedCommands.filter(function (command) { return command.url === url; });
        return commandsForUrl[commandsForUrl.length - 1];
    };
    Scheduler.prototype.initElement = function (element) {
        if (this.shouldInit) {
            var command = new command_1.Command(element.url, element, element, operations_1.EOperation.INIT);
            this.commands.push(command);
        }
    };
    Scheduler.prototype.shouldInit = function (url) {
        return !this.commands.some(function (command) { return command.operation === operations_1.EOperation.INIT && command.url === url; });
    };
    Scheduler.prototype.schedule = function (url, operation, newValue, originalValue) {
        if (!originalValue) {
            originalValue = this.getLastStoredValue(url);
        }
        var command = new command_1.Command(url, originalValue, newValue, operation);
        switch (command.operation) {
            case operations_1.EOperation.CREATE:
                this.scheduleCreateCommand(command);
                break;
            case operations_1.EOperation.UPDATE:
                this.scheduleUpdateCommand(command);
                break;
            case operations_1.EOperation.DELETE:
                this.scheduleDeleteCommand(command);
                break;
        }
    };
    Scheduler.prototype.unScheduleLastCommand = function (url) {
        var index = this.commands.indexOf(this.getLastUnresolvedCommand(url));
        if (index >= 0) {
            this.commands.splice(index, 1);
            return true;
        }
        return false;
    };
    Scheduler.prototype.unScheduleAllCommands = function (url) {
        var unscheduled = true;
        while (unscheduled) {
            unscheduled = this.unScheduleLastCommand(url);
        }
    };
    Scheduler.prototype.scheduleCreateCommand = function (command) {
        this.commands.push(command);
    };
    Scheduler.prototype.scheduleUpdateCommand = function (command) {
        if (!command.originalValue) {
            return;
        }
        if (!this.currentlyExists(command.url)) {
            return;
        }
        var lastCommand = this.getLastUnresolvedCommand();
        if (this.shouldMerge(lastCommand, command)) {
            command = lastCommand.mergeKeepOriginalValue(command);
            this.unScheduleLastCommand(command.url);
        }
        if (command.isDifference) {
            this.commands.push(command);
        }
    };
    Scheduler.prototype.scheduleDeleteCommand = function (command) {
        if (!this.currentlyExists(command.url)) {
            return;
        }
        this.commands.push(command);
    };
    Scheduler.prototype.currentlyExists = function (url) {
        var commands = this.getCommands(url);
        if (commands.length == 0) {
            throw new Error("Tried to check existence for unknown element! " + url);
        }
        var lastCommand = commands[commands.length - 1];
        return lastCommand.operation !== operations_1.EOperation.DELETE;
    };
    Scheduler.prototype.shouldMerge = function (c1, c2) {
        if (c1 && c2) {
            return c1.operation === operations_1.EOperation.UPDATE && c2.operation === operations_1.EOperation.UPDATE && c1.changedSameFields(c2) && c1.url === c2.url;
        }
        return false;
    };
    Scheduler.prototype.isVirtualElement = function (url) {
        return this.getCommands(url).some(function (command) { return command.operation === operations_1.EOperation.CREATE && !command.isResolved; });
    };
    Scheduler.prototype.resolve = function (url) {
        console.log("RESOLVE " + url);
        var firstCommand = this.getFirstUnresolvedCommand(url);
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