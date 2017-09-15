import {Id} from '../../../../util/Id';
import { Proxy } from '../../../../model/support/proxy';
import { CEGNode } from '../../../../model/CEGNode';
import { CEGConnection } from '../../../../model/CEGConnection';
import { Type } from '../../../../util/Type';
import { SpecmateDataService } from '../../../../services/data/specmate-data.service';
import { IContainer } from '../../../../model/IContainer';
import { Arrays } from "../../../../util/Arrays";
import { ITool } from "./i-tool";

export class DeleteTool implements ITool<IContainer> {

    name: string = 'Delete';
    icon: string = 'trash';
    color: string = 'danger';
    cursor: string = 'alias';
    done: boolean = false;

    selectedElements: IContainer[];

    constructor(private parent: IContainer, private dataService: SpecmateDataService) {
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
        return this.getConnections(element as CEGNode)
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
        if (Type.is(element, CEGNode)) {
            this.deleteNode(element as CEGNode, compoundId);
            return;
        } else if (Type.is(element, CEGConnection)) {
            return this.deleteConnection(element as CEGConnection, compoundId);
        }
        throw new Error('Tried to delete element with type ' + element.className + '. Only elements of tyoe CEGNode and CEGConnection are supported.');
    }

    private deleteNode(node: CEGNode, compoundId: string): Promise<void> {
        return this.dataService.deleteElement(node.url, true, compoundId);
    }

    private deleteConnection(connection: CEGConnection, compoundId: string): Promise<void> {
        return this.removeConnectionFromSource(connection, compoundId)
            .then(() => this.removeConnectionFromTarget(connection, compoundId))
            .then(() => this.dataService.deleteElement(connection.url, true, compoundId))
    }

    private removeConnectionFromSource(connection: CEGConnection, compoundId: string): Promise<void> {
        return this.dataService.readElement(connection.source.url, true)
            .then((source: CEGNode) => {
                let proxyToDelete: Proxy = source.outgoingConnections.find((proxy: Proxy) => proxy.url === connection.url);
                Arrays.remove(source.outgoingConnections, proxyToDelete);
                return source;
            })
            .then((source: IContainer) => this.dataService.updateElement(source, true, compoundId));
    }

    private removeConnectionFromTarget(connection: CEGConnection, compoundId: string): Promise<void> {
        return this.dataService.readElement(connection.target.url, true)
            .then((target: CEGNode) => {
                let proxyToDelete: Proxy = target.incomingConnections.find((proxy: Proxy) => proxy.url === connection.url);
                Arrays.remove(target.incomingConnections, proxyToDelete);
                return target;
            })
            .then((target: IContainer) => this.dataService.updateElement(target, true, compoundId));
    }

    private getConnections(node: IContainer): Promise<CEGConnection[]> {
        if (Type.is(node, CEGNode)) {
            return this.dataService.readContents(this.parent.url, true)
                .then((contents: IContainer[]) => {
                    return this.getConnectionsOfNode(node, contents);
                });
        }
        return Promise.resolve([]);
    }

    private getConnectionsOfNode(node: IContainer, contents: IContainer[]): CEGConnection[] {
        let connections: CEGConnection[] = [];
        for (let i = 0; i < contents.length; i++) {
            let currentElement: IContainer = contents[i];
            if (Type.is(currentElement, CEGConnection)) {
                let currentConnection: CEGConnection = currentElement as CEGConnection;
                if (currentConnection.source.url === node.url || currentConnection.target.url === node.url) {
                    connections.push(currentConnection);
                }
            }
        }
        return connections;
    }
}
