import { Input } from '@angular/core';
import { IModelConnection } from '../../../../../../../model/IModelConnection';
import { IModelNode } from '../../../../../../../model/IModelNode';
import { Proxy } from '../../../../../../../model/support/proxy';
import { LineCoordinateProvider } from '../providers/coordinates/line-coordinate-provider';
import { LineCoordsProviderBase } from '../providers/coordinates/line-coords-provider-base';
import { Area, Line, Square } from '../util/area';
import { GraphicalElementBase } from './graphical-element-base';

export abstract class GraphicalConnectionBase<T extends IModelConnection> extends GraphicalElementBase<T> {

    private _nodes: IModelNode[];
    private _connection: T;

    protected startLineCoordsProvider: LineCoordsProviderBase;
    protected endLineCoordsProvider: LineCoordsProviderBase;

    @Input()
    public set connection(connection: T) {
        this._connection = connection;
        this.setUpLineCoordsProvider();
    }

    public get connection(): T {
        return this._connection;
    }

    public get element(): T {
        return this.connection;
    }

    @Input()
    public set nodes(nodes: IModelNode[]) {
        this._nodes = nodes;
        this.setUpLineCoordsProvider();
    }

    public get nodes(): IModelNode[] {
        return this._nodes;
    }

    @Input()
    public selected: boolean;

    protected get sourceNode(): IModelNode {
        return this.getNode(this.connection.source);
    }

    protected get targetNode(): IModelNode {
        return this.getNode(this.connection.target);
    }

    private getNode(proxy: Proxy): IModelNode {
        if (!proxy) {
            throw new Error('Tried to get element for undefined proxy!');
        }
        return this.nodes.filter((containedNode: IModelNode) => containedNode.url === proxy.url)[0];
    }

    public setUpLineCoordsProvider(): void {
        if (this._nodes && this._connection && !this.startLineCoordsProvider && !this.endLineCoordsProvider) {
            this.startLineCoordsProvider = LineCoordinateProvider.provide(this.sourceNode, this.sourceNode, this.targetNode);
            this.endLineCoordsProvider = LineCoordinateProvider.provide(this.targetNode, this.sourceNode, this.targetNode);
        }
    }

    public isInSelectionArea(area: Square): boolean {
        let line = new Line( this.startLineCoordsProvider.lineStart.x, this.startLineCoordsProvider.lineStart.y,
                             this.endLineCoordsProvider.lineEnd.x, this.endLineCoordsProvider.lineEnd.y);
        return Area.checkLineInArea(area, line);
    }
}
