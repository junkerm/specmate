"use strict"; 
var Config = (function () { 
    function Config() { 
    } 
    // VERSION is filled in by the build script. 
    Config.VERSION = '4fa17f6'; 
    Config.URL_BASE = 'services/rest/'; 
    Config.URL_CONTENTS = '/list'; 
    Config.URL_ELEMENT = '/details'; 
    Config.URL_DELETE = '/delete'; 
    Config.CEG_NODE_WIDTH = 150; 
    Config.CEG_NODE_HEIGHT = 57; 
    Config.CEG_NODE_ARC_DIST = 17 + Math.sqrt((Config.CEG_NODE_WIDTH / 2.0) * (Config.CEG_NODE_WIDTH / 2.0) + (Config.CEG_NODE_HEIGHT / 2.0) * (Config.CEG_NODE_HEIGHT / 2.0)); 
    Config.CEG_MODEL_BASE_ID = 'model'; 
    Config.CEG_NEW_MODEL_NAME = 'New Model'; 
    Config.CEG_NEW_MODEL_DESCRIPTION = ''; 
    Config.CEG_NODE_BASE_ID = 'node'; 
    Config.CEG_NEW_NODE_NAME = 'New Node'; 
    Config.CEG_NEW_NODE_DESCRIPTION = ''; 
    Config.CEG_NEW_NODE_X = 100; 
    Config.CEG_NEW_NODE_Y = 100; 
    Config.CEG_NODE_NEW_TYPE = 'AND'; 
    Config.CEG_NODE_NEW_VARIABLE = 'variable'; 
    Config.CEG_NODE_NEW_OPERATOR = '='; 
    Config.CEG_NODE_NEW_VALUE = '0'; 
    Config.CEG_CONNECTION_BASE_ID = 'conn'; 
    Config.CEG_NEW_CONNECTION_NAME = 'New Connection'; 
    Config.CEG_NEW_CONNECTION_DESCRIPTION = ''; 
    Config.CEG_EDITOR_HEIGHT = (isNaN(window.innerHeight) ? window['clientHeight'] : window.innerHeight) * 0.75; 
    Config.CEG_EDITOR_WIDTH = 1000; 
    Config.CEG_EDITOR_DESCRIPTION_ROWS = 9; 
    Config.TESTSPEC_BASE_ID = 'testspec'; 
    Config.TESTSPEC_NAME = 'New Test Specification'; 
    Config.TESTSPEC_DESCRIPTION = ''; 
    // The separator to separate strings from id-numbers. Must not be included in the allowed chars. 
    Config.ID_SEP = '-'; 
    Config.ID_ALLOWED_CHARS = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z', '_']; 
    Config.ID_FORBIDDEN_REPLACEMENT = '_'; 
    Config.ID_MIN = 1; 
    return Config; 
}()); 
exports.Config = Config; 
//# sourceMappingURL=config.js.map 
