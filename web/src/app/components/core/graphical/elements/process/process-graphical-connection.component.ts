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
import { ProcessConnection } from "../../../../../model/ProcessConnection";
import { GraphicalConnectionBase } from "../graphical-connection-base";
import { RectangularLineCoordsProvider } from "../coordinate-providers/rectangular-line-coords-provider";
import { ProcessStep } from "../../../../../model/ProcessStep";
import { ProcessDecision } from "../../../../../model/ProcessDecision";
import { LineCoordsProviderBase } from "../coordinate-providers/line-coords-provider-base";
import { DiamondLineCoordsProvider } from "../coordinate-providers/diamond-line-coords-provider";
import { Type } from "../../../../../util/Type";
import { IModelNode } from "../../../../../model/IModelNode";

@Component({
    moduleId: module.id,
    selector: '[process-graphical-connection]',
    templateUrl: 'process-graphical-connection.component.svg',
    styleUrls: ['process-graphical-connection.component.css']
})
export class ProcessGraphicalConnection extends GraphicalConnectionBase<ProcessConnection> {
    public nodeType: { className: string; } = ProcessConnection;

    protected nodeWidth: number = Config.CEG_NODE_WIDTH;
    protected nodeHeight: number = Config.CEG_NODE_HEIGHT;

    protected startLineCoordsProvider: LineCoordsProviderBase;
    protected endLineCoordsProvider: LineCoordsProviderBase;

    private _nodes: (ProcessStep | ProcessDecision)[];
    private _connection: ProcessConnection;

    @Input()
    public set connection(connection: ProcessConnection) {
        this._connection = connection;
        this.setUpLineCoordsProvider();
    }

    public get connection(): ProcessConnection {
        return this._connection;
    }

    public get element(): ProcessConnection {
        return this.connection;
    }

    @Input()
    public set nodes(nodes: (ProcessStep | ProcessDecision)[]) {
        this._nodes = nodes;
        this.setUpLineCoordsProvider();
    }

    public get nodes(): (ProcessStep | ProcessDecision)[] {
        return this._nodes;
    }

    @Input()
    selected: boolean;

    @Input()
    valid: boolean;

    public setUpLineCoordsProvider(): void {
        if(this._nodes && this._connection) {
            this.startLineCoordsProvider = this.getLineCoordsProvider(this.sourceNode);
            this.endLineCoordsProvider = this.getLineCoordsProvider(this.targetNode);
        }
    }

    private getLineCoordsProvider(node: IModelNode): LineCoordsProviderBase {
        if(Type.is(node, ProcessDecision)) {
            return new DiamondLineCoordsProvider(this.sourceNode, this.targetNode, Config.PROCESS_DECISION_NODE_DIM);
        } else if (Type.is(node, ProcessStep)) {
            return new RectangularLineCoordsProvider(this.sourceNode, this.targetNode, {width: this.nodeWidth, height: this.nodeHeight});
        }
    }
}
