import { GraphicalElementBase } from "./graphical-element-base";
import { IContainer } from "../../../../model/IContainer";
import { Angles } from "../util/angles";
import { Coords } from "../util/coords";
import { ISpecmatePositionableModelObject } from "../../../../model/ISpecmatePositionableModelObject";
import { Input } from "@angular/core";
import { Config } from "../../../../config/config";
import { IModelNode } from "../../../../model/IModelNode";
import { Proxy } from "../../../../model/support/proxy";
import { IModelConnection } from "../../../../model/IModelConnection";
import { LineCoordsProviderBase } from "./coordinate-providers/line-coords-provider-base";
import { LineCoordinateProvider } from "./coordinate-providers/line-coordinate-provider";

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
    selected: boolean;

    @Input()
    valid: boolean;

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

    public get angle(): number {
        if(!this.startLineCoordsProvider) {
            return 0;
        }
        return this.startLineCoordsProvider.angle;
    }

    public get centerX(): number {
        return (this.lineStartX + this.lineEndX) / 2;
    }

    public get centerY(): number {
        return (this.lineStartY + this.lineEndY) / 2;
    }

    public get lineStartX(): number {
        if(!this.startLineCoordsProvider) {
            return 0;
        }
        return this.startLineCoordsProvider.lineStart.x;
    }

    public get lineStartY(): number {
        if(!this.startLineCoordsProvider) {
            return 0;
        }
        return this.startLineCoordsProvider.lineStart.y;
    }

    public get lineEndX(): number {
        if(!this.endLineCoordsProvider) {
            return 0;
        }
        return this.endLineCoordsProvider.lineEnd.x;
    }

    public get lineEndY(): number {
        if(!this.endLineCoordsProvider) {
            return 0;
        }
        return this.endLineCoordsProvider.lineEnd.y;
    }

    public setUpLineCoordsProvider(): void {
        if(this._nodes && this._connection) {
            this.startLineCoordsProvider = LineCoordinateProvider.provide(this.sourceNode, this.sourceNode, this.targetNode);
            this.endLineCoordsProvider = LineCoordinateProvider.provide(this.targetNode, this.sourceNode, this.targetNode);
        }
    }
}