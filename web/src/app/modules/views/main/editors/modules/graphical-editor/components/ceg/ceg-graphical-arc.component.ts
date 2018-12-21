import { Component, Input } from '@angular/core';
import { GraphicalElementBase } from '../../elements/graphical-element-base';
import { CEGNode } from '../../../../../../../../model/CEGNode';
import { CEGConnection } from '../../../../../../../../model/CEGConnection';
import { Config } from '../../../../../../../../config/config';
import { SelectedElementService } from '../../../../../../side/modules/selected-element/services/selected-element.service';
import { ValidationService } from '../../../../../../../forms/modules/validation/services/validation.service';
import { Angles } from '../../util/angles';
import { Square } from '../../util/area';
import { MultiselectionService } from '../../../tool-pallette/services/multiselection.service';
import { Coords } from '../../util/coords';

class Point {
    constructor(public x: number, public y: number) {}
}

class AngledConnection {
    constructor(public connection: CEGConnection, public angle: number ) {}
}

@Component({
    moduleId: module.id.toString(),
    selector: '[ceg-graphical-arc]',
    templateUrl: 'ceg-graphical-arc.component.html'
})
export class CEGGraphicalArc extends GraphicalElementBase<CEGNode> {
    public nodeType: { className: string; } = CEGNode;

    private _connections: AngledConnection[];
    private radius: number = Config.CEG_NODE_ARC_DIST;

    private startConnectionIndex = -1;
    private endConnectionIndex = -1;

    private startPoints: {[key: string]: Point} = {};
    private endPoints: {[key: string]: Point} = {};

    @Input()
    private node: CEGNode;

    @Input()
    private nodes: CEGNode[];

    @Input()
    private type: string;

    constructor(selectedElementService: SelectedElementService,
                validationService: ValidationService,
                multiselectionService: MultiselectionService) {
        super(selectedElementService, validationService, multiselectionService);
    }

    @Input()
    private set connections(connections: CEGConnection[]) {
        if (!connections) {
            return;
        }
        if (!this.node) {
            return;
        }
        this._connections = connections.filter((connection: CEGConnection) => connection.target.url === this.node.url)
                            .map(c => new AngledConnection(c, Angles.normalize(this.getConnectionAngle(c))))
                            .sort((c1: AngledConnection, c2: AngledConnection) => (c2.angle - c1.angle));
        this.determineArcConnections();

    }

    private get connections(): CEGConnection[] {
        if (!this.node) {
            return [];
        }
        return this._connections.map(c => c.connection);
    }

    public get element(): CEGNode {
        return this.node;
    }

    private getConnectionAngle(connection: CEGConnection): number {
        let startPoint: Point = this.getStartPoint(connection);
        let endPoint: Point = this.getEndPoint(connection);
        if (!startPoint || !endPoint) {
            return 0;
        }

        return Angles.angle(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
    }

    private determineArcConnections(): void {
        // Compute the start and end connection of the arc.
        if (!this.connections || this.connections.length === 0) {
            return;
        }
        let maxAngleDiff = -1;
        for (let i = 0; i < this._connections.length; i++) {
            let isLastElement: boolean = i === (this._connections.length - 1);
            let startIndex: number = i;
            let endIndex: number = isLastElement ? 0 : i + 1;
            let angleDiff: number = Angles.calcAngleDiff(this._connections[endIndex].angle, this._connections[startIndex].angle);
            if (angleDiff > maxAngleDiff) {
                maxAngleDiff = angleDiff;
                this.startConnectionIndex = endIndex;
                this.endConnectionIndex = startIndex;
            }
        }
    }

    private getStartPoint(connection: CEGConnection): Point {
        if (!this.nodes || !connection) {
            return new Point(0, 0);
        }
        return this.nodes.find((node: CEGNode) => node.url === connection.source.url);
    }

    private getEndPoint(connection: CEGConnection): Point {
        if (!this.nodes || !connection) {
            return new Point(0, 0);
        }
        return this.nodes.find((node: CEGNode) => node.url === connection.target.url);
    }

    private get markerPosition(): Point {
        let diff: number = Angles.calcAngleDiff(this.endAngle, this.startAngle);
        let angle: number = this.startAngle - (diff / 2.0);
        return Coords.polarToCartesian(angle + 180, this.radius - 10, this.center);
    }

    private get center(): Point {
        if (!this.connections) {
            return new Point(0, 0);
        }
        let endPoint: Point = this.getEndPoint(this.connections[0]);
        return new Point(endPoint.x, endPoint.y);
    }

    private get startConnection(): AngledConnection {
        if (this.connections === undefined || this.connections.length === 0 || this.startConnectionIndex < 0) {
            return undefined;
        }
        return this._connections[this.startConnectionIndex];
    }

    private get endConnection(): AngledConnection {
        if (this.connections === undefined || this.connections.length === 0 || this.endConnectionIndex < 0) {
            return undefined;
        }
        return this._connections[this.endConnectionIndex];
    }

    public get isVisible(): boolean {
        return this.node && this.node.incomingConnections && this.node.incomingConnections.length > 1;
    }

    private get startAngle(): number {
        return this.startConnection.angle;
    }

    private get endAngle(): number {
        return this.endConnection.angle;
    }

    private get arcStart(): Point {
        return Coords.polarToCartesian(this.startAngle + 180, this.radius, this.center);
    }

    private get arcEnd(): Point {
        return Coords.polarToCartesian(this.endAngle + 180, this.radius, this.center);
    }

    private get arcD(): string {
        let diff: number = Angles.calcAngleDiff(this.startAngle, this.endAngle);
        let largeArcFlag: number = Math.abs(diff) <= 180 ? 1 : 0;

        return [
            'M', this.arcStart.x, this.arcStart.y,
            'A', this.radius, this.radius, 0, largeArcFlag, 0, this.arcEnd.x, this.arcEnd.y
        ].join(' ');
    }

    public isInSelectionArea(area: Square): boolean {
        // The graphical arc can not be selected.
        return false;
    }
}
