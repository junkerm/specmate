import { Component, Input } from '@angular/core';

import { Config } from '../../../config/config';

import { CEGNode } from '../../../model/CEGNode';
import { CEGConnection } from '../../../model/CEGConnection';
import { Proxy } from '../../../model/support/proxy';
import { SpecmateDataService } from '../../../services/specmate-data.service';

@Component({
    moduleId: module.id,
    selector: '[ceg-graphical-connection]',
    templateUrl: 'ceg-graphical-connection.component.svg'
})
export class CEGGraphicalConnection {

    @Input()
    connection: CEGConnection;

    @Input()
    nodes: CEGNode[];

    constructor(private dataService: SpecmateDataService) { }

    get x1(): number {
        return this.sourceNode.x + (Config.CEG_NODE_WIDTH / 2);
    }

    get y1(): number {
        return this.sourceNode.y + (Config.CEG_NODE_HEIGHT / 2);
    }

    get x2(): number {
        return this.targetNode.x + (Config.CEG_NODE_WIDTH / 2);
    }

    get y2(): number {
        return this.targetNode.y + (Config.CEG_NODE_HEIGHT / 2);
    }

    get sourceNode(): CEGNode {
        return this.getNode(this.connection.source);
    }

    get targetNode(): CEGNode {
        return this.getNode(this.connection.target);
    }

    private getNode(proxy: Proxy): CEGNode {
        return this.nodes.filter((node: CEGNode) => node.url === proxy.url)[0];
    }

}