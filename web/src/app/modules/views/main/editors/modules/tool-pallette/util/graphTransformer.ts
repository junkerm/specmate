import { IContainer } from '../../../../../../../model/IContainer';
import { ElementProvider } from '../../graphical-editor/providers/properties/element-provider';
import { GraphElementFactorySelector } from '../../../../../../../factory/util/graph-element-factory-selector';
import { IConnection } from '../../../../../../../model/IConnection';
import { IModelNode } from '../../../../../../../model/IModelNode';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { SelectedElementService } from '../../../../../side/modules/selected-element/services/selected-element.service';
import { Url } from '../../../../../../../util/url';
import { IModelConnection } from '../../../../../../../model/IModelConnection';
import { Proxy } from '../../../../../../../model/support/proxy';
import { Arrays } from '../../../../../../../util/arrays';
import { Id } from '../../../../../../../util/id';

export class GraphTransformer {
    private elementProvider: ElementProvider;
    constructor(private dataService: SpecmateDataService, private selectionService: SelectedElementService, private parent: IContainer) {
        this.elementProvider = new ElementProvider(this.parent);
    }

    // Delete
    public deleteAll(elements: IContainer[], compoundId: string): Promise<void> {
        let chain = Promise.resolve();
        for (const element of elements) {
            chain = chain.then(() => this.deleteElementAndDeselect(element, compoundId));
        }
        return chain;
    }

    public deleteElementAndDeselect(element: IContainer, compoundId: string): Promise<void> {
        this.selectionService.deselect();
        this.dataService.readElement(Url.parent(element.url), true).then((model: IContainer) => this.selectionService.select(model));
        if (this.elementProvider.isNode(element)) {
            return this.deleteNode(element as IModelNode, compoundId);
        } else if (this.elementProvider.isConnection(element)) {
            return this.deleteConnection(element as IModelConnection, compoundId);
        }
        // Tried to delete element with type element.className. This type is not supported.
        return Promise.resolve();
    }

    private deleteNode(node: IModelNode, compoundId: string): Promise<void> {
        return this.dataService.readContents(this.parent.url, true).then( (content: IContainer[]) => {
            // Delete Connections
            let chain = Promise.resolve();
            content.forEach(elem => {
                if (this.elementProvider.isConnection(elem)) {
                    let currentConnection: IModelConnection = elem as IModelConnection;
                    if (currentConnection.source.url === node.url || currentConnection.target.url === node.url) {
                        chain = chain.then(() => this.deleteConnection(currentConnection, compoundId));
                    }
                }
            });
            return chain;
        }).then(() => this.dataService.deleteElement(node.url, true, compoundId));
    }

    private deleteConnection(connection: IModelConnection, compoundId: string): Promise<void> {
        return this.removeConnectionFromSource(connection, compoundId)
            .then(() => this.removeConnectionFromTarget(connection, compoundId))
            .then(() => this.dataService.deleteElement(connection.url, true, compoundId));
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

    // Create
    public createSubgraph(templates: IContainer[]): Promise<void> {
        let compundId = Id.uuid;
        let urlMap: {[old: string]: IModelNode} = {};
        // Old URL -> New Node map

        let chain: Promise<any> = Promise.resolve();

        for (const template of templates) {
            if (this.elementProvider.isNode(template)) {
                let temp = <IModelNode> template;
                let newCoord = { x: temp.x, y: temp.y + 100};
                let factory = GraphElementFactorySelector.getNodeFactory(template, newCoord, this.dataService);
                chain = chain.then(() => factory.create(this.parent, false))
                             .then((node) => {
                                urlMap[template.url] = <IModelNode>node;
                                this.transferNodeData(temp, <IModelNode>node);
                                this.dataService.updateElement(node, true, compundId);
                            });
            }
        }

        // Filter Connections that are within the subgraph
        for (const template of templates) {
            if (this.elementProvider.isConnection(template)) {
                let temp = <IConnection> template;
                chain.then( () => {
                    if ((temp.target.url in urlMap) && (temp.source.url in urlMap)) {
                        let source = urlMap[temp.source.url];
                        let target = urlMap[temp.target.url];
                        let factory = GraphElementFactorySelector.getConnectionFactory(template, source, target, this.dataService);
                        return factory.create(this.parent, false).then( (con: IModelConnection) => {
                            this.transferConnectionData(temp, con);
                            this.dataService.updateElement(con, true, compundId);
                        });
                    }
                });
            }
        }
        return chain;
    }

    private transferNodeData(template: IContainer, node: IModelNode) {
        // TODO replace this with a model independet method
        let fields = ['name', 'description', 'variable', 'condition', 'expectedOutcome'];
        for (const field of fields) {
            if (template.hasOwnProperty(field)) {
                node[field] = template[field];
            }
        }
    }

    private transferConnectionData(template: IContainer, connection: IModelConnection) {
        // TODO replace this with a model independet method
        let fields = ['name', 'description', 'negate'];
        for (const field of fields) {
            if (template.hasOwnProperty(field)) {
                connection[field] = template[field];
            }
        }
    }
}
