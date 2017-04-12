import { Input, Component } from '@angular/core';
import { CEGGraphicalConnection } from "./ceg-graphical-connection.component";

type Point = { x: number, y: number }

@Component({
    moduleId: module.id,
    selector: '[ceg-graphical-arc]',
    templateUrl: 'ceg-graphical-arc.component.svg'
})
export class CEGGraphicalArc {

    private _connections: CEGGraphicalConnection[];

    @Input()
    private set connections(connections: CEGGraphicalConnection[]) {
        this._connections = connections.sort((c1: CEGGraphicalConnection, c2: CEGGraphicalConnection) => this.normalize(c2.angle) - this.normalize(c1.angle));
    }

    private radius: number = 100;

    private get center(): { x: number, y: number } {
        return {
            x: this.anyConnection.x2,
            y: this.anyConnection.y2
        };
    }

    private get anyConnection(): CEGGraphicalConnection {
        return this._connections[0];
    }

    private get firstConnection(): CEGGraphicalConnection {
        if (this._connections === undefined || this._connections.length === 0) {
            return undefined;
        }
        return this._connections[0];
    }

    private get lastConnection(): CEGGraphicalConnection {
        if (this._connections === undefined || this._connections.length === 0) {
            return undefined;
        }
        return this._connections[this._connections.length - 1];
    }

    private get draw(): boolean {
        return this._connections && this._connections.length > 1;
    }

    private polarToCartesian(angleInDegrees: number): Point {
        let angleInRadians = angleInDegrees * Math.PI / 180.0;

        return {
            x: this.center.x + (this.radius * Math.cos(angleInRadians)),
            y: this.center.y + (this.radius * Math.sin(angleInRadians))
        };
    }

    private normalize(angle: number): number {
        if(angle < 0) {
            angle = 360 + angle;
        }
        return angle;
    }

    private get startAngle(): number {
        let angle: number = this.firstConnection.angle;
        return this.normalize(angle);
    }

    private get endAngle(): number {
        let angle: number = this.lastConnection.angle;
        return this.normalize(angle);
    }

    private get arcStart(): Point {
        return this.polarToCartesian(this.startAngle + 180);
    }

    private get arcEnd(): Point {
        return this.polarToCartesian(this.endAngle + 180);
    }

    private get arcD(): string {

        console.log(this.startAngle + "-" + this.endAngle + " = " + (this.startAngle - this.endAngle));
        let diff: number = this.endAngle - this.startAngle;

        let largeArcFlag = Math.abs(this.endAngle - this.startAngle) <= 180 ? "0" : "1";

        let d = [
            "M", this.arcStart.x, this.arcStart.y,
            "A", this.radius, this.radius, 0, largeArcFlag, 0, this.arcEnd.x, this.arcEnd.y
        ].join(" ");

        return d;
    }
}
