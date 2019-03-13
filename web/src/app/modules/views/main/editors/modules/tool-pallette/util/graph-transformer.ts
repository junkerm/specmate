import { GraphElementFactorySelector, Coords } from '../../../../../../../factory/util/graph-element-factory-selector';
import { IConnection } from '../../../../../../../model/IConnection';
import { IContainer } from '../../../../../../../model/IContainer';
import { IModelConnection } from '../../../../../../../model/IModelConnection';
import { IModelNode } from '../../../../../../../model/IModelNode';
import { ISpecmatePositionableModelObject } from '../../../../../../../model/ISpecmatePositionableModelObject';
import { FieldMetaItem, MetaInfo } from '../../../../../../../model/meta/field-meta';
import { Proxy } from '../../../../../../../model/support/proxy';
import { Arrays } from '../../../../../../../util/arrays';
import { Url } from '../../../../../../../util/url';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { SelectedElementService } from '../../../../../side/modules/selected-element/services/selected-element.service';
import { ElementProvider } from '../../graphical-editor/providers/properties/element-provider';
import { Objects } from '../../../../../../../util/objects';

export class GraphTransformer {
    private elementProvider: ElementProvider;
    constructor(private dataService: SpecmateDataService, private selectionService: SelectedElementService, private parent: IContainer) {
        this.elementProvider = new ElementProvider(this.parent);
    }

    // Delete
    public async deleteAll(elements: IContainer[], compoundId: string): Promise<void> {
        await this.selectionService.deselectElements(elements);
        // We have to delete connections first to avoid updating already deleted nodes.
        for (const element of elements) {
            if (this.elementProvider.isConnection(element)) {
                await this.deleteElement(element, compoundId);
            }
        }
        for (const element of elements) {
            if (this.elementProvider.isNode(element)) {
                await this.deleteElement(element, compoundId);
            }
        }
        if (this.selectionService !== undefined && this.selectionService !== null) {
            this.selectionService.deselectElements(elements);
        }
    }

    private async deleteElement(element: IContainer, compoundId: string): Promise<void> {
        const model = await this.dataService.readElement(Url.parent(element.url), true);
        if (this.selectionService !== undefined && this.selectionService !== null) {
            this.selectionService.select(model);
        }

        if (this.elementProvider.isNode(element)) {
            await this.deleteNode(element as IModelNode, compoundId);
        } else if (this.elementProvider.isConnection(element)) {
            await this.deleteConnection(element as IModelConnection, compoundId);
        }
        // Tried to delete element with type element.className. This type is not supported.
    }

    public async deleteElementAndDeselect(element: IContainer, compoundId: string): Promise<void> {
        if (this.selectionService !== undefined && this.selectionService !== null) {
            this.selectionService.deselectElement(element);
        }
        await this.deleteElement(element, compoundId);
    }

    private async deleteNode(node: IModelNode, compoundId: string): Promise<void> {
        const contents: IContainer[] = await this.dataService.readContents(this.parent.url, true);
        const connections = contents
            .filter((element: IContainer) => this.elementProvider.isConnection(element))
            .map((element: IContainer) => element as IModelConnection)
            .filter((connection: IModelConnection) => connection.source.url === node.url || connection.target.url === node.url);
        for (let i = 0 ; i < connections.length; i++) {
            await this.deleteConnection(connections[i], compoundId);
        }
        await this.dataService.deleteElement(node.url, true, compoundId);
    }

    private async deleteConnection(connection: IModelConnection, compoundId: string): Promise<void> {
        await this.removeConnectionFromSource(connection, compoundId);
        await this.removeConnectionFromTarget(connection, compoundId);
        await this.dataService.deleteElement(connection.url, true, compoundId);
    }

    private async removeConnectionFromSource(connection: IModelConnection, compoundId: string): Promise<void> {
        const source = await this.dataService.readElement(connection.source.url, true) as IModelNode;
        if (!source.outgoingConnections) {
            return;
        }
        const proxy = source.outgoingConnections.find(proxy => proxy.url === connection.url);
        Arrays.remove(source.outgoingConnections, proxy);
        await this.dataService.updateElement(source, true, compoundId);
    }

    private async removeConnectionFromTarget(connection: IModelConnection, compoundId: string): Promise<void> {
        const target = await this.dataService.readElement(connection.target.url, true) as IModelNode;
        if (!target.incomingConnections) {
            return;
        }
        const proxy = target.incomingConnections.find(proxy => proxy.url === connection.url);
        Arrays.remove(target.incomingConnections, proxy);
        await this.dataService.updateElement(target, true, compoundId);
    }

    /**
     * Clones a given list of elements (Nodes, Vertecies) and returns a promise for the copies
     * If changeGraph is true, we use factory methods and commit the changes to the model
     * This is used when you want to create new nodes/vertecies.
     *
     * If changeGraph is false, we only clone the objects without changing the model
     * This is used when you only want to clone the data for later work.
     */
    public async cloneSubgraph(templates: IContainer[], compoundId: string, changeGraph: boolean, offset = 100): Promise<IContainer[]> {
        let urlMap: {[old: string]: IModelNode} = {};
        let out: IContainer[] = [];
        // Old URL -> New Node map
        for (const template of templates) {
            if (this.elementProvider.isNode(template)) {
                let temp = <IModelNode> template;
                let newCoord = { x: temp.x, y: temp.y + offset};
                let node = <IModelNode>await this.cloneNode(temp, newCoord, compoundId, changeGraph);
                urlMap[template.url] = node;
                node.incomingConnections = [];
                node.outgoingConnections = [];
                if (changeGraph) {
                    this.transferData(temp, node);
                    await this.updateElement(node, compoundId);
                }
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
                    let con = await this.cloneEdge(template, source, target, compoundId, changeGraph);
                    let conProxy = new Proxy();
                    conProxy.url = con.url;
                    if (changeGraph) {
                        this.transferData(temp, con);
                        await this.updateElement(source, compoundId);
                        await this.updateElement(target, compoundId);
                        await this.updateElement(con, compoundId);
                    }
                    out.push(con);
                }
            }
        }
        return Promise.resolve(out);
    }

    private async cloneNode(template: IModelNode, coords: Coords, compoundId: string, createNode: boolean):
        Promise<ISpecmatePositionableModelObject> {
        if (!createNode) {
            return <ISpecmatePositionableModelObject>Objects.clone(template);
        }
        let factory = GraphElementFactorySelector.getNodeFactory(template, coords, this.dataService);
        return await factory.create(this.parent, false, compoundId);
    }

    private async cloneEdge(template: IContainer, newSource: IModelNode, newTarget: IModelNode, compoundId: string, createEdge: boolean):
        Promise<IModelConnection> {
        if (!createEdge) {
            return <IModelConnection>Objects.clone(template);
        }
        let factory = GraphElementFactorySelector.getConnectionFactory(template, newSource, newTarget, this.dataService);
        return await factory.create(this.parent, false, compoundId);
    }

    private async updateElement(element: IContainer, compoundId: string): Promise<void> {
        await this.dataService.updateElement(element, true, compoundId);
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
