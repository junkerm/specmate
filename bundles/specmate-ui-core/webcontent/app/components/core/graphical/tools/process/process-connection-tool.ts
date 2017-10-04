import { ConnectionToolBase } from "../connection-tool-base";
import { Url } from "../../../../../util/Url";
import { IContainer } from "../../../../../model/IContainer";
import { Config } from "../../../../../config/config";
import { Proxy } from "../../../../../model/support/proxy";
import { IModelNode } from "../../../../../model/IModelNode";
import { Type } from "../../../../../util/Type";
import { ProcessConnection } from "../../../../../model/ProcessConnection";
import { ProcessStart } from "../../../../../model/ProcessStart";
import { ProcessDecision } from "../../../../../model/ProcessDecision";
import { ProcessStep } from "../../../../../model/ProcessStep";
import { ProcessEnd } from "../../../../../model/ProcessEnd";
import { Process } from "../../../../../model/Process";

export class ProcessConnectionTool extends ConnectionToolBase<ProcessConnection> {
    protected modelType: { className: string; } = Process;
    
    name: string = 'Add Connection';
    icon: string = 'sitemap';

    protected createConnection(id: string, e1: IModelNode, e2: IModelNode): ProcessConnection {
        let url: string = Url.build([this.parent.url, id]);
        let connection: ProcessConnection = new ProcessConnection();
        connection.name = Config.PROCESS_NEW_CONNECTION_NAME;
        connection.description = Config.PROCESS_NEW_CONNECTION_DESCRIPTION;
        connection.condition = Config.PROCESS_NEW_CONNECTION_DESCRIPTION;
        connection.id = id;
        connection.url = url;
        connection.source = new Proxy();
        connection.source.url = e1.url;
        connection.target = new Proxy();
        connection.target.url = e2.url;
        let proxy: Proxy = new Proxy();
        proxy.url = connection.url;
        if (!e1.outgoingConnections) {
            e1.outgoingConnections = [];
        }
        if (!e2.incomingConnections) {
            e2.incomingConnections = [];
        }
        e1.outgoingConnections.push(proxy);
        e2.incomingConnections.push(proxy);
        return connection;
    }
}
