import { IModelConnection } from '../../../../../../../model/IModelConnection';

export class Angles {
    public static angle<T extends IModelConnection>(lineStartX: number, lineStartY: number, lineEndX: number, lineEndY: number): number {
        return Angles.calcAngle(lineEndX - lineStartX, lineEndY - lineStartY);
    }

    public static calcAngle(dx: number, dy: number): number {
        return Angles.radToDeg(Math.atan2(dy, dx));
    }

    public static radToDeg(radAngle: number) {
        return radAngle * 180.0 / Math.PI;
    }

    public static degToRad(degAngle: number) {
        return degAngle * Math.PI / 180.0;
    }

    public static calcAngleDiff(angle1: number, angle2: number): number {
        angle1 = Angles.normalize(angle1);
        angle2 = Angles.normalize(angle2);
        if (angle2 < angle1) {
            return Angles.normalize((360 - angle1) + angle2);
        }
        return Angles.normalize(angle2 - angle1);
    }

    public static normalize(angle: number): number {
        if (angle < 0) {
            angle = 360 + (angle % 360);
        }
        return angle % 360;
    }
}
