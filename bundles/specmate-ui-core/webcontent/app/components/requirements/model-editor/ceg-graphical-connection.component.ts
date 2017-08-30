import { Component, Input } from '@angular/core';

import { Config } from '../../../config/config';

import { CEGNode } from '../../../model/CEGNode';
import { CEGConnection } from '../../../model/CEGConnection';
import { Proxy } from '../../../model/support/proxy';
import { SpecmateDataService } from '../../../services/data/specmate-data.service';
import { CEGCauseNode } from '../../../model/CEGCauseNode';
import { CEGEffectNode } from '../../../model/CEGEffectNode';
import { Converters } from "../../core/forms/conversion/converters";

@Component({
    moduleId: module.id,
    selector: '[ceg-graphical-connection]',
    templateUrl: 'ceg-graphical-connection.component.svg',
    styleUrls: ['ceg-graphical-connection.component.css']
})
export class CEGGraphicalConnection {

    @Input()
    connection: CEGConnection;

    @Input()
    causeNodes: CEGCauseNode[];

    @Input()
    effectNodes: CEGEffectNode[];

    @Input()
    nodes: CEGNode[];

    @Input()
    selected: boolean;

    @Input()
    valid: boolean;

    constructor(private dataService: SpecmateDataService) { }

    public get x1(): number {
        return this.sourceNode ? Number.parseFloat((this.sourceNode.x + (Config.CEG_NODE_WIDTH / 2)) + '') : 0;
    }

    public get y1(): number {
        return this.sourceNode ? Number.parseFloat((this.sourceNode.y + (Config.CEG_NODE_HEIGHT / 2)) + '') : 0;
    }

    public get x2(): number {
        return this.targetNode ? Number.parseFloat((this.targetNode.x + (Config.CEG_NODE_WIDTH / 2)) + '') : 0;
    }

    public get y2(): number {
        return this.targetNode ? Number.parseFloat((this.targetNode.y + (Config.CEG_NODE_HEIGHT / 2)) + '') : 0;
    }

    private get centerX(): number {
        return (this.x1 + this.x2) / 2.0;
    }

    private get centerY(): number {
        return (this.y1 + this.y2) / 2.0;
    }

    private get alpha1(): number {
        return this.calcAngle(-Config.CEG_NODE_WIDTH, -Config.CEG_NODE_HEIGHT);;
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
            return this.x2 - Config.CEG_NODE_WIDTH / 2;
        } else if(this.isRight) {
            return this.x2 + Config.CEG_NODE_WIDTH / 2;
        } else if(this.isTop) {
            return this.x2 - ((Config.CEG_NODE_HEIGHT / 2) / Math.tan(this.angle / 180 * Math.PI));
        } else if(this.isBelow) {
            return this.x2 + ((Config.CEG_NODE_HEIGHT / 2) / Math.tan(this.angle / 180 * Math.PI));
        }
    }

    public get arrowY(): number {
        if(this.isLeft) {
            return this.y2 - ((Config.CEG_NODE_WIDTH / 2) * Math.tan(this.angle / 180 * Math.PI));
        } else if(this.isRight) {
            return this.y2 + ((Config.CEG_NODE_WIDTH / 2) * Math.tan(this.angle / 180 * Math.PI));
        } else if(this.isTop) {
            return this.y2 - Config.CEG_NODE_HEIGHT / 2;
        } else if(this.isBelow) {
            return this.y2 + Config.CEG_NODE_HEIGHT / 2;
        }
    }

    public get angle(): number {
        return this.calcAngle(this.x2 - this.x1, this.y2 - this.y1);
    }

    private calcAngle(dx: number, dy: number): number {
        return Math.atan2(dy, dx) * 180.0 / Math.PI;
    }

    private get sourceNode(): CEGNode {
        return this.getNode(this.connection.source);
    }

    private get targetNode(): CEGNode {
        return this.getNode(this.connection.target);
    }

    private get isNegated(): boolean {
        // Recently, the negate property is sometimes sent as string from the server. We workaround this easily here.
        return (this.connection.negate + '').toLowerCase() === 'true';
    }

    private getNode(proxy: Proxy): CEGNode {
        if (!proxy) {
            throw new Error('Tried to get element for undefined proxy!');
        }
        let node: CEGNode = this.effectNodes.filter((containedNode: CEGNode) => containedNode.url === proxy.url)[0];
        if (node) {
            return node;
        }
        node = this.causeNodes.filter((containedNode: CEGNode) => containedNode.url === proxy.url)[0];
        if (node) {
            return node;
        }
        return this.nodes.filter((containedNode: CEGNode) => containedNode.url === proxy.url)[0];
    }
}
