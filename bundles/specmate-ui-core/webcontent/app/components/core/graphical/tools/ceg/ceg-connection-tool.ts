import { CEGConnection } from "../../../../../model/CEGConnection";
import { ConnectionToolBase } from "../connection-tool-base";
import { Url } from "../../../../../util/Url";
import { IContainer } from "../../../../../model/IContainer";
import { Config } from "../../../../../config/config";
import { Proxy } from "../../../../../model/support/proxy";
import { IModelNode } from "../../../../../model/IModelNode";
import { Type } from "../../../../../util/Type";
import { CEGNode } from "../../../../../model/CEGNode";
import { CEGModel } from "../../../../../model/CEGModel";

export class CEGConnectionTool extends ConnectionToolBase<CEGConnection> {
    protected modelType: { className: string; } = CEGModel;

    name: string = 'Add Connection';
    icon: string = 'sitemap';

    protected createConnection(id: string, e1: IModelNode, e2: IModelNode): CEGConnection {
        let url: string = Url.build([this.parent.url, id]);
        let connection: CEGConnection = new CEGConnection();
        connection.name = Config.CEG_NEW_CONNECTION_NAME;
        connection.description = Config.CEG_NEW_CONNECTION_DESCRIPTION;
        connection.id = id;
        connection.url = url;
        connection.negate = false;
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
