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
import { MetaInfo, FieldMetaItem } from '../../../../../../../model/meta/field-meta';

export class GraphTransformer {
    private elementProvider: ElementProvider;
    constructor(private dataService: SpecmateDataService, private selectionService: SelectedElementService, private parent: IContainer) {
        this.elementProvider = new ElementProvider(this.parent);
    }

    // Delete
    public deleteAll(elements: IContainer[], compoundId: string): Promise<void> {
        let chain = Promise.resolve();
        for (const element of elements) {
            chain = chain.then(() => this.deleteElement(element, compoundId));
        }
        chain.then(() => this.selectionService.deselectElements(elements));
        return chain;
    }

    private deleteElement(element: IContainer, compoundId: string): Promise<void> {
        this.dataService.readElement(Url.parent(element.url), true).then((model: IContainer) => this.selectionService.select(model));
        if (this.elementProvider.isNode(element)) {
            return this.deleteNode(element as IModelNode, compoundId);
        } else if (this.elementProvider.isConnection(element)) {
            return this.deleteConnection(element as IModelConnection, compoundId);
        }
        // Tried to delete element with type element.className. This type is not supported.
        return Promise.resolve();
    }

    public deleteElementAndDeselect(element: IContainer, compoundId: string): Promise<void> {
        this.selectionService.deselectElement(element);
        return this.deleteElement(element, compoundId);
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

    // Create a copy from the given templates in the current model.
    public async createSubgraph(templates: IContainer[], compundId: string): Promise<IContainer[]> {
        let urlMap: {[old: string]: IModelNode} = {};
        let out: IContainer[] = [];
        // Old URL -> New Node map
        for (const template of templates) {
            if (this.elementProvider.isNode(template)) {
                let temp = <IModelNode> template;
                let newCoord = { x: temp.x, y: temp.y + 100};
                let factory = GraphElementFactorySelector.getNodeFactory(template, newCoord, this.dataService);
                let node = await factory.create(this.parent, false, compundId);
                urlMap[template.url] = <IModelNode>node;
                this.transferData(temp, <IModelNode>node);
                await this.dataService.updateElement(node, true, compundId);
                out.push(node);
            }
        }

        // Filter Connections that are within the subgraph
        for (const template of templates) {
            if (this.elementProvider.isConnection(template)) {
                let temp = <IConnection> template;
                if ((temp.target.url in urlMap) && (temp.source.url in urlMap)) {
                    let source = urlMap[temp.source.url];
                    let target = urlMap[temp.target.url];
                    let factory = GraphElementFactorySelector.getConnectionFactory(template, source, target, this.dataService);
                    let con = await factory.create(this.parent, false, compundId);
                    this.transferData(temp, con);
                    await this.dataService.updateElement(con, true, compundId);
                    out.push(con);
                }
            }
        }
        return Promise.resolve(out);
    }

    private transferData(from: IContainer, to: IContainer) {
        let fields: string[] = MetaInfo[from.className].map( (item: FieldMetaItem) => item.name);
        for (const field of fields) {
            if (from.hasOwnProperty(field)) {
                to[field] = from[field];
            }
        }
    }
}
