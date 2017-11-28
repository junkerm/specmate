export class Config {
    
    // VERSION is filled in by the build script.
    public static VERSION = '-SPECMATE-VERSION-';

    public static URL_BASE = 'services/rest/';
    public static URL_CONTENTS = '/list';
    public static URL_ELEMENT = '/details';
    public static URL_DELETE = '/delete';

    // If you change this, you also need to change the Jetty Redirects (jetty-redirects.xml)
    public static VIEW_URL_PREFIX = '-/';

    public static CONFIRM_SAVE_MESSAGE = 'Your changes will be saved. Continue?';
    public static NAVIGATION_CONFIRMATION = 'You have unsaved changes. Do you really want to discard them?';

    public static CONNECTIVITY_CHECK_DELAY = 10000;

    public static LOG_START_MESSAGE = 'Specmate Started';
    public static LOG_LENGTH = 100;
    public static LOG_DEFAULT_COLOR = 'muted';
    public static LOG_DEFAULT_ICON = 'comment';
    public static LOG_INITIALLY_SHOWN = false;

    // Editor settings
    public static GRAPHICAL_EDITOR_WIDTH: number = 1000;
    public static GRAPHICAL_EDITOR_HEIGHT: number = (isNaN(window.innerHeight) ? window['clientHeight'] : window.innerHeight) * 0.75;
    public static GRAPHICAL_EDITOR_PADDING_HORIZONTAL = 300;
    public static GRAPHICAL_EDITOR_PADDING_VERTICAL = 300;
    public static GRAPHICAL_EDITOR_GRID_SPACE = 15;
    public static GRAPHICAL_EDITOR_ZOOM_STEP = 0.1;
    public static GRAPHICAL_EDITOR_ZOOM_MAX = 5;

    public static CEG_NODE_WIDTH: number = 150;
    public static CEG_NODE_HEIGHT: number = 60;
    public static CEG_NODE_ARC_DIST: number = 17 + Math.sqrt((Config.CEG_NODE_WIDTH / 2.0) * (Config.CEG_NODE_WIDTH / 2.0) + (Config.CEG_NODE_HEIGHT / 2.0) * (Config.CEG_NODE_HEIGHT / 2.0));

    public static CEG_MODEL_BASE_ID = 'model';
    public static CEG_NEW_MODEL_NAME = 'New Model';
    public static CEG_NEW_MODEL_DESCRIPTION = '';

    public static CEG_NODE_BASE_ID = 'node';
    public static CEG_NEW_NODE_NAME: string = 'New Node';
    public static CEG_NEW_NODE_DESCRIPTION: string = '';
    public static CEG_NEW_NODE_X: number = 100;
    public static CEG_NEW_NODE_Y: number = 100;
    public static CEG_NODE_NEW_TYPE: string = 'AND';
    public static CEG_NODE_NEW_VARIABLE: string = 'variable';
    public static CEG_NODE_NEW_CONDITION: string = 'is present';

    public static CEG_CONNECTION_BASE_ID = 'conn';
    public static CEG_NEW_CONNECTION_NAME: string = 'New Connection';
    public static CEG_NEW_CONNECTION_DESCRIPTION: string = '';
    public static CEG_EDITOR_DESCRIPTION_ROWS: number = 9;

    public static PROCESS_DECISION_NODE_DIM = 30;
    public static PROCESS_START_END_NODE_RADIUS = 15;
    public static PROCESS_NEW_PROCESS_NAME = 'New Process';
    public static PROCESS_NEW_PROCESS_DESCRIPTION = '';
    public static PROCESS_NEW_STEP_NAME = 'New Activity';
    public static PROCESS_NEW_STEP_DESCRIPTION = '';
    public static PROCESS_NEW_DECISION_NAME = 'New Decision';
    public static PROCESS_NEW_DECISION_DESCRIPTION = '';
    public static PROCESS_NEW_START_NAME = 'Start';
    public static PROCESS_NEW_START_DESCRIPTION = '';
    public static PROCESS_NEW_END_NAME = 'END';
    public static PROCESS_NEW_END_DESCRIPTION = '';
    public static PROCESS_NEW_CONNECTION_NAME = 'New Connection';
    public static PROCESS_NEW_CONNECTION_CONDITION = '';
    public static PROCESS_NEW_CONNECTION_DESCRIPTION = '';

    public static TESTSPEC_BASE_ID = 'testspec';
    public static TESTSPEC_NAME = 'New Test Specification';
    public static TESTSPEC_DESCRIPTION = '';

    public static TESTPARAMETER_NAME = 'New Test Parameter';
    public static TESTPARAMETER_BASE_ID = 'testparam';

    public static TESTPARAMETERASSIGNMENT_NAME = 'New Test Parameter Assignment';
    public static TESTPARAMETERASSIGNMENT_BASE_ID = 'testparamassignment';
    public static TESTPARAMETERASSIGNMENT_DEFAULT_CONDITION = 'UNASSIGNED';
    public static TESTPARAMETERASSIGNMENT_DEFAULT_VALUE = 'UNASSIGNED';

    public static TESTCASE_NAME = 'New Test Case';
    public static TESTCASE_BASE_ID = 'testcase';

    public static TESTPROCEDURE_NAME = 'New Test Procedure';

    public static TESTSTEP_NAME = 'New Test Step';
    public static TESTSTEP_ACTION = 'ACTION';
    public static TESTSTEP_EXPECTED_OUTCOME = 'OUTCOME';

    // The separator to separate strings from id-numbers. Must not be included in the allowed chars.
    public static ID_SEP = '-';
    public static ID_ALLOWED_CHARS = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z', '_'];
    public static ID_FORBIDDEN_REPLACEMENT = '_';
    public static ID_MIN = 1;

    public static ERROR_UNCONNECTED_NODE = 'Unconnected node in model.';
    public static ERROR_DUPLICATE_IO_VARIABLE = 'Duplicate IO variable.';
    public static ERROR_EMPTY_MODEL = 'Model empty.';
    public static ERROR_NOT_ONE_START_NODE = 'Not exactly one start node.';
    public static ERROR_NO_END_NODE = 'No end node.';
    public static ERROR_NODE_WITHOUT_INCOMING = 'Node without incoming connection.';
    public static ERROR_NODE_WITHOUT_OUTGOING = 'Node without outgoing connection.';
    public static ERROR_MISSING_CONDITION = 'Missing condition.';
    public static ERROR_NO_STEPS = 'No steps in model.';
    
}