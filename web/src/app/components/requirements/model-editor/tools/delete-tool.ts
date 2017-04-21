import { Proxy } from '../../../../model/support/proxy';
import { ITool } from './i-tool';
import { CEGNode } from '../../../../model/CEGNode';
import { CEGConnection } from '../../../../model/CEGConnection';
import { Type } from '../../../../util/Type';
import { SpecmateDataService } from '../../../../services/specmate-data.service';
import { IContainer } from '../../../../model/IContainer';
import { CEGEffectNode } from '../../../../model/CEGEffectNode';
import { CEGCauseNode } from '../../../../model/CEGCauseNode';
import { Arrays } from "../../../../util/Arrays";

export class DeleteTool implements ITool<IContainer> {

    name: string = 'Delete';
    icon: string = 'trash';
    color: string = 'danger';
    cursor: string = 'alias';

    selectedElements: IContainer[];

    constructor(private parent: IContainer, private dataService: SpecmateDataService) {
        this.selectedElements = [];
    }

    activate(): void { }
    deactivate(): void { }
    click(event: MouseEvent): void { }

    select(element: IContainer): void {
        this.getConnections(element as CEGNode)
            .then((connections: IContainer[]) => {
                let chain: Promise<void> = Promise.resolve();
                for (let i = 0; i < connections.length; i++) {
                    chain = chain.then(() => {
                        return this.deleteElement(connections[i]);
                    });
                }
                return chain;
            })
            .then(() => {
                return this.deleteElement(element);
            });
    }

    private deleteElement(element: IContainer): Promise<void> {
        if (Type.is(element, CEGNode) || Type.is(element, CEGCauseNode) || Type.is(element, CEGEffectNode)) {
            this.deleteNode(element as CEGNode);
            return;
        } else if (Type.is(element, CEGConnection)) {
            return this.deleteConnection(element as CEGConnection);
        }
        throw new Error('Tried to delete element with type ' + element.className + '. Only elements of tyoe CEGNode and CEGConnection are supported.');
    }

    private deleteNode(node: CEGNode): Promise<void> {
        return this.dataService.deleteElement(node.url, true);
    }

    private deleteConnection(connection: CEGConnection): Promise<void> {
        return this.removeConnectionFromSource(connection)
            .then(() => this.removeConnectionFromTarget(connection))
            .then(() => this.dataService.deleteElement(connection.url, true))
    }

    private removeConnectionFromSource(connection: CEGConnection): Promise<void> {
        return this.dataService.readElement(connection.source.url, true)
            .then((source: CEGNode) => {
                let proxyToDelete: Proxy = source.outgoingConnections.find((proxy: Proxy) => proxy.url === connection.url);
                Arrays.remove(source.outgoingConnections, proxyToDelete);
                return source;
            })
            .then((source: IContainer) => this.dataService.updateElement(source, true));
    }

    private removeConnectionFromTarget(connection: CEGConnection): Promise<void> {
        return this.dataService.readElement(connection.target.url, true)
            .then((target: CEGNode) => {
                let proxyToDelete: Proxy = target.incomingConnections.find((proxy: Proxy) => proxy.url === connection.url);
                Arrays.remove(target.incomingConnections, proxyToDelete);
                return target;
            })
            .then((target: IContainer) => this.dataService.updateElement(target, true));
    }

    private getConnections(node: IContainer): Promise<CEGConnection[]> {
        if (Type.is(node, CEGNode) || Type.is(node, CEGCauseNode) || Type.is(node, CEGEffectNode)) {
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
