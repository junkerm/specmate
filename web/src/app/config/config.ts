export class Config {

    public static URL_BASE = 'services/rest/';
    public static URL_CONTENTS = '/list';
    public static URL_ELEMENT = '/details';
    public static URL_DELETE = '/delete';

    // If you change this, you also need to change the Jetty Redirects (jetty-redirects.xml)
    public static VIEW_URL_PREFIX = '-/';

    // For all of these languages, we need to
    // - have a <lang>.json file in assets/i18n
    // - update it with the translation extractor in package.json (run 'npm run extract')
    public static LANGUAGES = [
        {code: 'gb', name: 'English'},
        {code: 'de', name: 'Deutsch'}
    ];
    public static DEFAULT_LANGUAGE = Config.LANGUAGES[0];
    public static LANGUAGE_CHOOSER_ENABLED = false;
    public static USE_BROWSER_LANGUAGE = false;

    public static CONFIRM_SAVE_MESSAGE = 'Your changes will be saved. Continue?';
    public static NAVIGATION_CONFIRMATION = 'You have unsaved changes. Do you really want to discard them?';

    public static CONNECTIVITY_CHECK_DELAY = 10000;

    public static LOG_START_MESSAGE = 'Specmate Started';
    public static LOG_LENGTH = 100;
    public static LOG_DEFAULT_COLOR = 'muted';
    public static LOG_DEFAULT_ICON = 'comment';
    public static LOG_INITIALLY_SHOWN = false;

    // Editor settings
    public static GRAPHICAL_EDITOR_WIDTH = 1000;
    public static GRAPHICAL_EDITOR_HEIGHT: number = (isNaN(window.innerHeight) ? window['clientHeight'] : window.innerHeight) * 0.75;
    public static GRAPHICAL_EDITOR_PADDING_HORIZONTAL = 300;
    public static GRAPHICAL_EDITOR_PADDING_VERTICAL = 300;
    public static GRAPHICAL_EDITOR_GRID_SPACE = 15;
    public static GRAPHICAL_EDITOR_ZOOM_STEP = 0.1;
    public static GRAPHICAL_EDITOR_ZOOM_MAX = 5;

    public static CEG_NODE_WIDTH = 150;
    public static CEG_NODE_HEIGHT = 60;
    public static CEG_NODE_ARC_DIST: number = 17 +
        Math.sqrt((Config.CEG_NODE_WIDTH / 2.0) * (Config.CEG_NODE_WIDTH / 2.0) +
        (Config.CEG_NODE_HEIGHT / 2.0) * (Config.CEG_NODE_HEIGHT / 2.0));

    public static CEG_NEW_MODEL_NAME = 'New Model';
    public static CEG_NEW_MODEL_DESCRIPTION = '';

    public static CEG_NEW_NODE_NAME = 'New Node';
    public static CEG_NEW_NODE_DESCRIPTION = '';
    public static CEG_NODE_NEW_TYPE = 'AND';
    public static CEG_NODE_NEW_VARIABLE = 'variable';
    public static CEG_NODE_NEW_CONDITION = 'is present';

    public static CEG_NEW_CONNECTION_NAME = 'New Connection';
    public static CEG_NEW_CONNECTION_DESCRIPTION = '';

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
    public static PROCESS_NEW_CONNECTION_DESCRIPTION = '';

    public static TESTSPEC_NAME = 'New Test Specification';
    public static TESTSPEC_DESCRIPTION = '';
    public static TESTSPEC_DESCRIPTION_TRUNC_LENGTH = 30;

    public static TESTPARAMETER_NAME = 'New Test Parameter';

    public static TESTPARAMETERASSIGNMENT_NAME = 'New Test Parameter Assignment';
    public static TESTPARAMETERASSIGNMENT_DEFAULT_CONDITION = 'UNASSIGNED';
    public static TESTPARAMETERASSIGNMENT_DEFAULT_VALUE = 'UNASSIGNED';

    public static TESTCASE_NAME = 'New Test Case';

    public static TESTPROCEDURE_NAME = 'New Test Procedure';
    public static TESTPROCEDURE_DESCRIPTION = '';

    public static TESTSTEP_NAME = 'New Test Step';
    public static TESTSTEP_ACTION = 'ACTION';
    public static TESTSTEP_EXPECTED_OUTCOME = 'OUTCOME';

    public static ERROR_UNCONNECTED_NODE = 'Unconnected node in model.';
    public static ERROR_DUPLICATE_IO_VARIABLE = 'Variable appears as cause and effect.';
    public static ERROR_DUPLICATE_NODE = 'Duplicate node.';
    public static ERROR_EMPTY_MODEL = 'Model empty.';
    public static ERROR_NOT_ONE_START_NODE = 'Not exactly one start node.';
    public static ERROR_NO_END_NODE = 'No end node.';
    public static ERROR_NODE_WITHOUT_INCOMING = 'Node without incoming connection.';
    public static ERROR_NODE_WITHOUT_OUTGOING = 'Node without outgoing connection.';
    public static ERROR_MISSING_CONDITION = 'Missing condition.';
    public static ERROR_NO_STEPS = 'No steps in model.';
}
