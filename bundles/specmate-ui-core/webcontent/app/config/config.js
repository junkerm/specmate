"use strict";
var Config = (function () {
    function Config() {
    }
    Config.CEG_NODE_WIDTH = 150;
    Config.CEG_NODE_HEIGHT = 50;
    Config.CEG_NEW_NAME = "";
    Config.CEG_NEW_DESCRIPTION = "";
    Config.CEG_NEW_X = 100;
    Config.CEG_NEW_Y = 100;
    Config.CEG_EDITOR_HEIGHT = 1000;
    Config.CEG_EDITOR_DESCRIPTION_ROWS = 9;
    Config.ID_FORBIDDEN_CHARS = ['/', ' ', '\'', '"', '?', '%', '(', ')', '@', ',', '.', '[', ']', '{', '}', '--'];
    Config.ID_FORBIDDEN_REPLACEMENT = '-';
    return Config;
}());
exports.Config = Config;
//# sourceMappingURL=config.js.map