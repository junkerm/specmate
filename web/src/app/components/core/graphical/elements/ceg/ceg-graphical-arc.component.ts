import { ChangeDetectorRef, Input, Component } from '@angular/core';
import { Config } from "../../../../../config/config";
import { CEGConnection } from "../../../../../model/CEGConnection";
import { Angles } from "../../util/angles";
import { CEGNode } from "../../../../../model/CEGNode";
import { Coords } from "../../util/coords";
import { Type } from "../../../../../util/Type";
import { GraphicalElementBase } from "../graphical-element-base";
import { SelectedElementService } from '../../../../../services/editor/selected-element.service';
import { ValidationService } from '../../../../../services/validation/validation.service';

type Point = { x: number, y: number }

@Component({
    moduleId: module.id,
    selector: '[ceg-graphical-arc]',
    templateUrl: 'ceg-graphical-arc.component.svg'
})
export class CEGGraphicalArc extends GraphicalElementBase<CEGNode> {

    public nodeType: { className: string; } = CEGNode;

    private _connections: CEGConnection[];
    private radius: number = Config.CEG_NODE_ARC_DIST;

    private startConnectionIndex: number = -1;
    private endConnectionIndex: number = -1;

    private startPoints: {[key : string] : Point} = {};
    private endPoints: {[key : string] : Point} = {};

    constructor(selectedElementService: SelectedElementService, validationService: ValidationService) {
        super(selectedElementService, validationService);
    }

    @Input()
    private set connections(connections: CEGConnection[]) {
        if(!connections) {
            return;
        }
        if(!this.node) {
            return;
        }
        this._connections = connections.filter((connection: CEGConnection) => connection.target.url === this.node.url)
        .sort((c1: CEGConnection, c2: CEGConnection) => this.normalize(this.getAngle(c2)) - this.normalize(this.getAngle(c1)));;
    }

    private get connections(): CEGConnection[] {
        if(!this.node) {
            return [];
        }
        return this._connections;
    }

    @Input()
    private node: CEGNode;

    public get element(): CEGNode {
        return this.node;
    }

    @Input()
    private nodes: CEGNode[];

    @Input()
    private type: string;

    private getAngle(connection: CEGConnection): number {
        let startPoint: Point = this.getStartPoint(connection);
        let endPoint: Point = this.getEndPoint(connection);
        return Angles.angle(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
    }

    private determineConnections(): void {
        if (!this.connections || this.connections.length === 0) {
            return;
        }
        let maxAngleDiff: number = -1;
        for (let i = 0; i < this.connections.length; i++) {
            let isLastElement: boolean = i === (this.connections.length - 1);
            let startIndex: number = i;
            let endIndex: number = isLastElement ? 0 : i + 1;
            let angleDiff: number = this.calcAngleDiff(this.getAngle(this.connections[endIndex]), this.getAngle(this.connections[startIndex]));
            if (angleDiff > maxAngleDiff) {
                maxAngleDiff = angleDiff;
                this.startConnectionIndex = endIndex;
                this.endConnectionIndex = startIndex;
            }
        }
    }

    private getStartPoint(connection: CEGConnection): Point {
        if(!this.nodes || !connection) {
            return {x: 0, y: 0};
        }
        if(!this.startPoints[connection.url]) {
            this.startPoints[connection.url] = this.nodes.find((node: CEGNode) => node.url === connection.source.url);
        }
        return this.startPoints[connection.url];
    }

    private getEndPoint(connection: CEGConnection): Point {
        if(!this.nodes || !connection) {
            return {x: 0, y: 0};
        }
        let point = this.endPoints[connection.url];
        if(point) {
            return point;
        }
        point = this.nodes.find((node: CEGNode) => node.url === connection.target.url);
        this.endPoints[connection.url] = point;
        return point;
    }

    private calcAngleDiff(angle1: number, angle2: number): number {
        angle1 = this.normalize(angle1);
        angle2 = this.normalize(angle2);
        if (angle2 < angle1) {
            return this.normalize((360 - angle1) + angle2);
        }
        return this.normalize(angle2 - angle1);
    }

    private get marker(): Point {
        let diff: number = this.calcAngleDiff(this.endAngle, this.startAngle);
        let angle: number = this.startAngle - (diff / 2.0);
        return this.polarToCartesian(angle + 180, this.radius - 10);
    }

    private get center(): Point {
        if(!this.connections) {
            return {x: 0, y: 0};
        }
        let endPoint: Point = this.getEndPoint(this.connections[0]);
        return {
            x: endPoint.x,
            y: endPoint.y
        };
    }

    private get startConnection(): CEGConnection {
        this.determineConnections();
        if (this.connections === undefined || this.connections.length === 0 || this.startConnectionIndex < 0) {
            return undefined;
        }
        return this.connections[this.startConnectionIndex];
    }

    private get endConnection(): CEGConnection {
        this.determineConnections();
        if (this.connections === undefined || this.connections.length === 0 || this.endConnectionIndex < 0) {
            return undefined;
        }
        return this.connections[this.endConnectionIndex];
    }

    public get isVisible(): boolean {
        return this.node && this.node.incomingConnections && this.node.incomingConnections.length > 1;
    }

    private polarToCartesian(angleInDegrees: number, radius?: number): Point {
        if (!radius) {
            radius = this.radius;
        }
        let angleInRadians = angleInDegrees * Math.PI / 180.0;

        return {
            x: this.center.x + (radius * Math.cos(angleInRadians)),
            y: this.center.y + (radius * Math.sin(angleInRadians))
        };
    }

    private normalize(angle: number): number {
        if (angle < 0) {
            angle = 360 + angle;
        }
        return angle % 360;
    }

    private get startAngle(): number {
        let angle: number = this.getAngle(this.startConnection);
        return this.normalize(angle);
    }

    private get endAngle(): number {
        let angle: number = this.getAngle(this.endConnection);
        return this.normalize(angle);
    }

    private get arcStart(): Point {
        return this.polarToCartesian(this.startAngle + 180);
    }

    private get arcEnd(): Point {
        return this.polarToCartesian(this.endAngle + 180);
    }

    private get arcD(): string {

        let diff: number = this.calcAngleDiff(this.startAngle, this.endAngle);
        let largeArcFlag: number = Math.abs(diff) <= 180 ? 1 : 0;

        return [
            'M', this.arcStart.x, this.arcStart.y,
            'A', this.radius, this.radius, 0, largeArcFlag, 0, this.arcEnd.x, this.arcEnd.y
        ].join(' ');
    }
}
