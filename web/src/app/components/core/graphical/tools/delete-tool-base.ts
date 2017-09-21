import {Id} from '../../../../util/Id';
import { Proxy } from '../../../../model/support/proxy';
import { Type } from '../../../../util/Type';
import { SpecmateDataService } from '../../../../services/data/specmate-data.service';
import { IContainer } from '../../../../model/IContainer';
import { Arrays } from "../../../../util/Arrays";
import { ITool } from "./i-tool";
import { IModelNode } from '../../../../model/IModelNode';
import { IModelConnection } from '../../../../model/IModelConnection';
import { TypeAwareToolBase } from './type-aware-tool-base';

export abstract class DeleteToolBase extends TypeAwareToolBase {

    name: string = 'Delete';
    icon: string = 'trash';
    color: string = 'danger';
    cursor: string = 'alias';
    done: boolean = false;

    selectedElements: IContainer[];

    constructor(private parent: IContainer, private dataService: SpecmateDataService) {
        super();
        this.selectedElements = [];
    }

    activate(): void {
        this.done = false;
    }

    deactivate(): void { }

    click(event: MouseEvent): Promise<void> {
        return Promise.resolve();
    }

    select(element: IContainer): Promise<void> {
        let compoundId: string = Id.uuid;
        return this.getConnections(element as IModelNode)
            .then((connections: IContainer[]) => {
                let chain: Promise<void> = Promise.resolve();
                for (let i = 0; i < connections.length; i++) {
                    chain = chain.then(() => {
                        return this.deleteElement(connections[i], compoundId);
                    });
                }
                return chain;
            })
            .then(() => {
                return this.deleteElement(element, compoundId);
            }).then(() => {
                this.done = true;
            });
    }

    private deleteElement(element: IContainer, compoundId: string): Promise<void> {
        if (this.isNode(element)) {
            this.deleteNode(element as IModelNode, compoundId);
            return;
        } else if (this.isConnection(element)) {
            return this.deleteConnection(element as IModelConnection, compoundId);
        }
        throw new Error('Tried to delete element with type ' + element.className + '. This type is not supported.');
    }

    private deleteNode(node: IModelNode, compoundId: string): Promise<void> {
        return this.dataService.deleteElement(node.url, true, compoundId);
    }

    private deleteConnection(connection: IModelConnection, compoundId: string): Promise<void> {
        return this.removeConnectionFromSource(connection, compoundId)
            .then(() => this.removeConnectionFromTarget(connection, compoundId))
            .then(() => this.dataService.deleteElement(connection.url, true, compoundId))
    }

    private removeConnectionFromSource(connection: IModelConnection, compoundId: string): Promise<void> {
        return this.dataService.readElement(connection.source.url, true)
            .then((source: IModelNode) => {
                let proxyToDelete: Proxy = source.outgoingConnections.find((proxy: Proxy) => proxy.url === connection.url);
                Arrays.remove(source.outgoingConnections, proxyToDelete);
                return source;
            })
            .then((source: IContainer) => this.dataService.updateElement(source, true, compoundId));
    }

    private removeConnectionFromTarget(connection: IModelConnection, compoundId: string): Promise<void> {
        return this.dataService.readElement(connection.target.url, true)
            .then((target: IModelNode) => {
                let proxyToDelete: Proxy = target.incomingConnections.find((proxy: Proxy) => proxy.url === connection.url);
                Arrays.remove(target.incomingConnections, proxyToDelete);
                return target;
            })
            .then((target: IContainer) => this.dataService.updateElement(target, true, compoundId));
    }

    private getConnections(node: IContainer): Promise<IModelConnection[]> {
        if (this.isNode(node)) {
            return this.dataService.readContents(this.parent.url, true)
                .then((contents: IContainer[]) => {
                    return this.getConnectionsOfNode(node, contents);
                });
        }
        return Promise.resolve([]);
    }

    private getConnectionsOfNode(node: IContainer, contents: IContainer[]): IModelConnection[] {
        let connections: IModelConnection[] = [];
        for (let i = 0; i < contents.length; i++) {
            let currentElement: IContainer = contents[i];
            if (this.isConnection(currentElement)) {
                let currentConnection: IModelConnection = currentElement as IModelConnection;
                if (currentConnection.source.url === node.url || currentConnection.target.url === node.url) {
                    connections.push(currentConnection);
                }
            }
        }
        return connections;
    }
}
