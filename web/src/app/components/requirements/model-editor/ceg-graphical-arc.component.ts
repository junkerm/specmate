import { Config } from '../../../config/config';
import { Arrays } from '../../../util/Arrays';
import { ChangeDetectorRef, Input, Component } from '@angular/core';
import { CEGGraphicalConnection } from './ceg-graphical-connection.component';

type Point = { x: number, y: number }

@Component({
    moduleId: module.id,
    selector: '[ceg-graphical-arc]',
    templateUrl: 'ceg-graphical-arc.component.svg'
})
export class CEGGraphicalArc {

    private _connections: CEGGraphicalConnection[];
    private radius: number = Config.CEG_NODE_ARC_DIST;

    private startConnectionIndex: number = -1;
    private endConnectionIndex: number = -1;

    @Input()
    private set connections(connections: CEGGraphicalConnection[]) {
        if (connections.length < 2) {
            this._connections = connections;
        }
        this._connections = connections.sort((c1: CEGGraphicalConnection, c2: CEGGraphicalConnection) => this.normalize(c2.angle) - this.normalize(c1.angle));
        this.determineConnections();
    }

    @Input()
    private type: string;

    private determineConnections(): void {
        if (!this._connections || this._connections.length === 0) {
            return;
        }
        let maxAngleDiff: number = -1;
        for (let i = 0; i < this._connections.length; i++) {
            let isLastElement: boolean = i === (this._connections.length - 1);
            let startIndex: number = i;
            let endIndex: number = isLastElement ? 0 : i + 1;
            let angleDiff: number = this.calcAngleDiff(this._connections[endIndex].angle, this._connections[startIndex].angle);
            if (angleDiff > maxAngleDiff) {
                maxAngleDiff = angleDiff;
                this.startConnectionIndex = endIndex;
                this.endConnectionIndex = startIndex;
            }
        }
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
        let angle: number = ((this.endAngle - this.startAngle) / 2.0) + this.startAngle;
        console.log(this.endAngle + " - " + this.startAngle + " = " + angle);
        let coords: Point = this.polarToCartesian(angle, this.radius - 10);
        return coords;
    }

    private get center(): Point {
        return {
            x: this.anyConnection.x2,
            y: this.anyConnection.y2
        };
    }

    private get anyConnection(): CEGGraphicalConnection {
        return this._connections[0];
    }

    private get startConnection(): CEGGraphicalConnection {
        if (this._connections === undefined || this._connections.length === 0 || this.startConnectionIndex < 0) {
            return undefined;
        }
        return this._connections[this.startConnectionIndex];
    }

    private get endConnection(): CEGGraphicalConnection {
        if (this._connections === undefined || this._connections.length === 0 || this.endConnectionIndex < 0) {
            return undefined;
        }
        return this._connections[this.endConnectionIndex];
    }

    private get draw(): boolean {
        return this._connections && this._connections.length > 1;
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
        let angle: number = this.startConnection.angle;
        return this.normalize(angle);
    }

    private get endAngle(): number {
        let angle: number = this.endConnection.angle;
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
