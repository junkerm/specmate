import { Component, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { Config } from '../../../config/config';

import { CEGNode } from '../../../model/CEGNode';
import { CEGConnection } from '../../../model/CEGConnection';
import { Proxy } from '../../../model/support/proxy';
import { SpecmateDataService } from '../../../services/specmate-data.service';
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

    constructor(private dataService: SpecmateDataService, private router: Router, private route: ActivatedRoute) { }

    private get x1(): number {
        return this.sourceNode ? Number.parseFloat((this.sourceNode.x + (Config.CEG_NODE_WIDTH / 2)) + '') : 0;
    }

    private get y1(): number {
        return this.sourceNode ? Number.parseFloat((this.sourceNode.y + (Config.CEG_NODE_HEIGHT / 2)) + '') : 0;
    }

    private get x2(): number {
        return this.targetNode ? Number.parseFloat((this.targetNode.x + (Config.CEG_NODE_WIDTH / 2)) + '') : 0;
    }

    private get y2(): number {
        return this.targetNode ? Number.parseFloat((this.targetNode.y + (Config.CEG_NODE_HEIGHT / 2)) + '') : 0;
    }

    private get centerX(): number {
        return (this.x1 + this.x2) / 2.0;
    }

    private get centerY(): number {
        return (this.y1 + this.y2) / 2.0;
    }

    private get angle(): number {
        return Math.atan2(this.y2 - this.y1, this.x2 - this.x1) * 180.0 / Math.PI;
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
