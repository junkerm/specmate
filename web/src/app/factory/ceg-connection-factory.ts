import { Config } from '../config/config';
import { CEGConnection } from '../model/CEGConnection';
import { IContainer } from '../model/IContainer';
import { Proxy } from '../model/support/proxy';
import { Id } from '../util/id';
import { Url } from '../util/url';
import { ConnectionElementFactoryBase } from './connection-element-factory-base';
import { ElementFactoryBase } from './element-factory-base';

export class CEGConnectionFactory extends ConnectionElementFactoryBase<CEGConnection> {
    public create(parent: IContainer, commit: boolean, compoundId?: string): Promise<CEGConnection> {
        compoundId = compoundId || Id.uuid;

        let id: string = Id.uuid;
        let url: string = Url.build([parent.url, id]);
        let connection: CEGConnection = new CEGConnection();
        connection.name = Config.CEG_NEW_CONNECTION_NAME + ' ' + ElementFactoryBase.getDateStr();
        connection.description = Config.CEG_NEW_CONNECTION_DESCRIPTION;
        connection.id = id;
        connection.url = url;
        connection.negate = false;
        connection.source = new Proxy();
        connection.source.url = this.source.url;
        connection.target = new Proxy();
        connection.target.url = this.target.url;
        connection.tracesFrom = [];
        connection.tracesTo = [];
        let proxy: Proxy = new Proxy();
        proxy.url = connection.url;
        if (!this.source.outgoingConnections) {
            this.source.outgoingConnections = [];
        }
        if (!this.target.incomingConnections) {
            this.target.incomingConnections = [];
        }
        this.source.outgoingConnections.push(proxy);
        this.target.incomingConnections.push(proxy);

        return this.dataService.createElement(connection, true, compoundId)
            .then(() => this.dataService.updateElement(this.source, true, compoundId))
            .then(() => this.dataService.updateElement(this.target, true, compoundId))
            .then(() => commit ? this.dataService.commit('create') : Promise.resolve())
            .then(() => connection);
    }

}
