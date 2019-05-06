import { Config } from '../../../../../../../config/config';
import { Angles } from './angles';
import { Point } from './area';

export class Coords {
    public static getCenter(x: number, y: number, width: number, height: number): {x: number, y: number} {
        let cx: number = Number.parseFloat((x + width / 2) + '');
        let cy: number = Number.parseFloat((y + height / 2) + '');
        return {
            x: cx,
            y: cy
        };
    }

    public static polarToCartesian(angleInDegrees: number, radius: number, center?: Point): Point {
        let angleInRadians = Angles.degToRad(angleInDegrees);
        if (!center) {
            center = new Point(0, 0);
        }

        return {
            x: center.x + (radius * Math.cos(angleInRadians)),
            y: center.y + (radius * Math.sin(angleInRadians))
        };
    }

    public static roundToGrid(coord: number): number {
        let rest: number = coord % Config.GRAPHICAL_EDITOR_GRID_SPACE;
        if (rest === 0) {
            return coord;
        }
        return coord - rest;
    }
}
