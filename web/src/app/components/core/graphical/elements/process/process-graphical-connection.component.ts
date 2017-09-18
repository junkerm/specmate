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

    @Input()
    connection: CEGConnection;

    public get element(): CEGConnection {
        return this.connection;
    }

    @Input()
    nodes: CEGNode[];

    @Input()
    selected: boolean;

    @Input()
    valid: boolean;

    constructor(private dataService: SpecmateDataService) {
        super();
    }
}
