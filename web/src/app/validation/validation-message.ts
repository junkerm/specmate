import { TranslateService } from '@ngx-translate/core';

export class ValidationMessage {
    public static ERROR_UNCONNECTED_NODE = 'errorUnconnectedNode';
    public static ERROR_SINGLE_INDEGREE_NODE = 'errorSingleIndegreeNode';
    public static ERROR_DUPLICATE_IO_VARIABLE = 'errorDublicateIOVariable';
    public static ERROR_DUPLICATE_NODE = 'errorDublicateNode';
    public static ERROR_EMPTY_MODEL = 'errorEmptyModel';
    public static ERROR_CONTRADICTORY_CAUSES = 'errorContradictoryModel';
    public static ERROR_CIRCULAR_CAUSES = 'errorCircularCauses';
    public static ERROR_NOT_ONE_START_NODE = 'errorNotOneStartNode';
    public static ERROR_NO_END_NODE = 'errorNoEndNode';
    public static ERROR_NODE_WITHOUT_INCOMING = 'errorNodeWithoutIncomingConnection';
    public static ERROR_NODE_WITHOUT_OUTGOING = 'errorNodeWithoutOutgoingConnection';
    public static ERROR_MISSING_CONDITION = 'errorMissingCondition';
    public static ERROR_MISSING_FIELDS = 'errorMissingFields';
    public static ERROR_NO_STEPS = 'errorNoSteps';
    public static ERROR_PROCESS_END_OUTGOING_CONNECTION = 'errorProcessEndOutgoingConnection';
    public static ERROR_PROCESS_START_INCOMING_CONNECTION = 'errorProcessStartIncomingConnection';
    public static ERROR_PROCESS_NODE_MULTIPLE_OUTGOING_CONNECTIONS = 'errorProcessNodeMultipleOutgoingConnections';
    public static ERROR_PROCESS_DECISION_WITH_ONE_OR_FEWER_OUTGOING_CONNECTIONS = 'errorProcessDecisionWithOneOrFewerOutgoingConnections';
    public static ERROR_INVALID_NAME = 'errorInvalidName';
    public static ERROR_INVALID_VARIABLE = 'errorInvalidVariable';


    constructor(private messageKey: string, private interpolationParameters?: Object) {}

    public getMessageTranslated(translate: TranslateService): string {
        return translate.instant('validationResult.' + this.messageKey, this.interpolationParameters);
    }
}
