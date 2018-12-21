import { Component, Input } from '@angular/core';
import { Angles } from '../../util/angles';

@Component({
    moduleId: module.id.toString(),
    selector: '[connection-line]',
    templateUrl: 'connection-line.component.html',
    styleUrls: ['connection-line.component.css']
})
export class ConnectionLine {
    @Input()
    public source: {x: number, y: number};

    @Input()
    public target: {x: number, y: number};

    @Input()
    public selected: boolean;

    @Input()
    public valid: boolean;

    @Input()
    public text: string;

    @Input()
    public arrowTip: boolean;

    @Input()
    public fillArrowTip: boolean;

    public get center(): {x: number, y: number} {
        return {
            x: (this.source.x + this.target.x) / 2,
            y: (this.source.y + this.target.y) / 2
        };
    }

    public get angle(): number {
        let angle: number = this.rawAngle;
        if (angle <= 90 && angle >= -90) {
            return angle;
        }
        return angle + 180;
    }

    public get rawAngle(): number {
        return Angles.angle(this.source.x, this.source.y, this.target.x, this.target.y);
    }

    public get length(): number {
        let dx: number = this.target.x - this.source.x;
        let dy: number = this.target.y - this.source.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
