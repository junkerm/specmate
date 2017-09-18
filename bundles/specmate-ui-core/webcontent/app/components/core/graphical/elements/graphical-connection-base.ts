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

export abstract class GraphicalConnectionBase<T extends IModelConnection> extends GraphicalElementBase<T> {

    protected abstract get nodeWidth(): number;
    protected abstract get nodeHeight(): number;

    public abstract connection: T;

    public get element(): T {
        return this.connection;
    }

    public abstract nodes: IModelNode[];
    public abstract selected: boolean;
    public abstract valid: boolean;

    public get lineStartX(): number {
        return this.sourceNodeCenter.x;
    }

    public get lineStartY(): number {
        return this.sourceNodeCenter.y;
    }

    public get lineEndX(): number {
        return this.targetNodeCenter.x;
    }

    public get lineEndY(): number {
        return this.targetNodeCenter.y;
    }

    private get sourceNodeCenter(): {x: number, y: number} {
        return this.getNodeCenter(this.sourceNode);
    }

    private get targetNodeCenter(): {x: number, y: number} {
        return this.getNodeCenter(this.targetNode);
    }

    private getNodeCenter(node: {x: number, y: number}): {x: number, y: number} {
        return Coords.getCenter(node.x, node.y, this.nodeWidth, this.nodeHeight);
    }

    private get lineCenterX(): number {
        return (this.lineStartX + this.lineEndX) / 2.0;
    }

    private get lineCenterY(): number {
        return (this.lineStartY + this.lineEndY) / 2.0;
    }

    private get alpha1(): number {
        return Angles.calcAngle(-this.nodeWidth, -this.nodeHeight);
    }

    private get isLeft(): boolean {
        return this.angle >= -(180 + this.alpha1) && this.angle <= (180 + this.alpha1)
    }

    private get isRight(): boolean {
        return (this.angle >= -this.alpha1 && this.angle <= 180) || (this.angle >= -180 && this.angle <= this.alpha1)
    }

    private get isTop(): boolean {
        return this.angle >= 180 + this.alpha1 && this.angle <= -this.alpha1;
    }

    private get isBelow(): boolean {
        return this.angle >= this.alpha1 && this.angle <= -(180 + this.alpha1);
    }

    public get arrowX(): number {
        if(this.isLeft) {
            return this.lineEndX - this.nodeWidth / 2;
        } else if(this.isRight) {
            return this.lineEndX + this.nodeWidth / 2;
        } else if(this.isTop) {
            return this.lineEndX - ((this.nodeHeight / 2) / Math.tan(this.angle / 180 * Math.PI));
        } else if(this.isBelow) {
            return this.lineEndX + ((this.nodeHeight / 2) / Math.tan(this.angle / 180 * Math.PI));
        }
    }

    public get arrowY(): number {
        if(this.isLeft) {
            return this.lineEndY - ((this.nodeWidth / 2) * Math.tan(this.angle / 180 * Math.PI));
        } else if(this.isRight) {
            return this.lineEndY + ((this.nodeWidth / 2) * Math.tan(this.angle / 180 * Math.PI));
        } else if(this.isTop) {
            return this.lineEndY - this.nodeHeight / 2;
        } else if(this.isBelow) {
            return this.lineEndY + this.nodeHeight / 2;
        }
    }

    private get angle(): number {
        return Angles.angle(this);
    }

    private get sourceNode(): IModelNode {
        return this.getNode(this.connection.source);
    }

    private get targetNode(): IModelNode {
        return this.getNode(this.connection.target);
    }

    private getNode(proxy: Proxy): IModelNode {
        if (!proxy) {
            throw new Error('Tried to get element for undefined proxy!');
        }
        return this.nodes.filter((containedNode: IModelNode) => containedNode.url === proxy.url)[0];
    }
}