import { Component, Input } from '@angular/core';
import { CEGConnection } from "../../../../../model/CEGConnection";
import { CEGNode } from "../../../../../model/CEGNode";
import { SpecmateDataService } from "../../../../../services/data/specmate-data.service";
import { Config } from "../../../../../config/config";
import { Proxy } from "../../../../../model/support/proxy";
import { Angles } from "../../util/angles";
import { Coords } from "../../util/coords";
import { GraphicalElementBase } from "../graphical-element-base";
import { IContainer } from "../../../../../model/IContainer";
import { GraphicalConnectionBase } from "../graphical-connection-base";
import { RectangularLineCoordsProvider } from "../coordinate-providers/rectangular-line-coords-provider";
import { LineCoordsProviderBase } from "../coordinate-providers/line-coords-provider-base";

@Component({
    moduleId: module.id,
    selector: '[ceg-graphical-connection]',
    templateUrl: 'ceg-graphical-connection.component.svg',
    styleUrls: ['ceg-graphical-connection.component.css']
})
export class CEGGraphicalConnection extends GraphicalConnectionBase<CEGConnection> {
    public nodeType: { className: string; } = CEGConnection;
    
    protected nodeWidth: number = Config.CEG_NODE_WIDTH;
    protected nodeHeight: number = Config.CEG_NODE_HEIGHT;

    protected startLineCoordsProvider: LineCoordsProviderBase;
    protected endLineCoordsProvider: LineCoordsProviderBase;

    private _nodes: CEGNode[];
    private _connection: CEGConnection;


    @Input()
    public set connection(connection: CEGConnection) {
        this._connection = connection;
        this.setupLineProvider();
    };
    public get connection(): CEGConnection {
        return this._connection;
    };

    public get element(): CEGConnection {
        return this.connection;
    }

    @Input()
    public set nodes(nodes: CEGNode[]) {
        this._nodes = nodes;
        this.setupLineProvider();
    }

    public get nodes(): CEGNode[] {
        return this._nodes;
    }

    @Input()
    selected: boolean;

    @Input()
    valid: boolean;
    
    private get isNegated(): boolean {
        return (this.connection.negate + '').toLowerCase() === 'true';
    }

    private setupLineProvider(): void {
        if(this.connection && this.nodes) {
            this.startLineCoordsProvider = new RectangularLineCoordsProvider(this.sourceNode, this.targetNode, {width: this.nodeWidth, height: this.nodeHeight});
            this.endLineCoordsProvider = new RectangularLineCoordsProvider(this.sourceNode, this.targetNode, {width: this.nodeWidth, height: this.nodeHeight});
        }
    }
}
