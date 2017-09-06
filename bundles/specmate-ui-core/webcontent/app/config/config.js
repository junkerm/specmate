"use strict"; 
Object.defineProperty(exports, "__esModule", { value: true }); 
var Config = (function () { 
    function Config() { 
    } 
    // VERSION is filled in by the build script. 
    Config.VERSION = '2182b7a'; 
    Config.URL_BASE = 'services/rest/'; 
    Config.URL_CONTENTS = '/list'; 
    Config.URL_ELEMENT = '/details'; 
    Config.URL_DELETE = '/delete'; 
    // If you change this, you also need to change the Jetty Redirects (jetty-redirects.xml) 
    Config.VIEW_URL_PREFIX = '-/'; 
    Config.CONFIRM_SAVE_MESSAGE = 'Your changes will be saved. Continue?'; 
    Config.NAVIGATION_CONFIRMATION = 'You have unsaved changes. Do you really want to discard them?'; 
    Config.CONNECTIVITY_CHECK_DELAY = 10000; 
    Config.LOG_START_MESSAGE = 'Specmate Started'; 
    Config.LOG_LENGTH = 100; 
    Config.LOG_DEFAULT_COLOR = 'muted'; 
    Config.LOG_DEFAULT_ICON = 'comment'; 
    Config.LOG_INITIALLY_SHOWN = false; 
    Config.CEG_EDITOR_GRID_SPACE = 20; 
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
    Config.CEG_NODE_NEW_CONDITION = 'is present'; 
    Config.CEG_CONNECTION_BASE_ID = 'conn'; 
    Config.CEG_NEW_CONNECTION_NAME = 'New Connection'; 
    Config.CEG_NEW_CONNECTION_DESCRIPTION = ''; 
    Config.EDITOR_HEIGHT = (isNaN(window.innerHeight) ? window['clientHeight'] : window.innerHeight) * 0.75; 
    Config.CEG_EDITOR_WIDTH = 1000; 
    Config.CEG_EDITOR_DESCRIPTION_ROWS = 9; 
    Config.TESTSPEC_BASE_ID = 'testspec'; 
    Config.TESTSPEC_NAME = 'New Test Specification'; 
    Config.TESTSPEC_DESCRIPTION = ''; 
    Config.TESTPARAMETER_NAME = 'New Test Parameter'; 
    Config.TESTPARAMETER_BASE_ID = 'testparam'; 
    Config.TESTPARAMETERASSIGNMENT_NAME = 'New Test Parameter Assignment'; 
    Config.TESTPARAMETERASSIGNMENT_BASE_ID = 'testparamassignment'; 
    Config.TESTPARAMETERASSIGNMENT_DEFAULT_CONDITION = 'UNASSIGNED'; 
    Config.TESTPARAMETERASSIGNMENT_DEFAULT_VALUE = 'UNASSIGNED'; 
    Config.TESTCASE_NAME = 'New Test Case'; 
    Config.TESTCASE_BASE_ID = 'testcase'; 
    Config.TESTPROCEDURE_NAME = 'New Test Procedure'; 
    Config.TESTSTEP_NAME = 'New Test Step'; 
    Config.TESTSTEP_ACTION = 'ACTION'; 
    Config.TESTSTEP_EXPECTED_OUTCOME = 'OUTCOME'; 
    // The separator to separate strings from id-numbers. Must not be included in the allowed chars. 
    Config.ID_SEP = '-'; 
    Config.ID_ALLOWED_CHARS = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z', '_']; 
    Config.ID_FORBIDDEN_REPLACEMENT = '_'; 
    Config.ID_MIN = 1; 
    return Config; 
}()); 
exports.Config = Config; 
//# sourceMappingURL=config.js.map 
