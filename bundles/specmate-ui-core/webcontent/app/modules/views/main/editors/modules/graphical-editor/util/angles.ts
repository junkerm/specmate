import { IModelConnection } from '../../../../../../../model/IModelConnection';

export class Angles {
    public static angle<T extends IModelConnection>(lineStartX: number, lineStartY: number, lineEndX: number, lineEndY: number): number {
        return Angles.calcAngle(lineEndX - lineStartX, lineEndY - lineStartY);
    }

    public static calcAngle(dx: number, dy: number): number {
        return Math.atan2(dy, dx) * 180.0 / Math.PI;
    }
}
